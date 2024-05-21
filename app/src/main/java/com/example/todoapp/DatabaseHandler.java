package com.example.todoapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    private String databaseName = "toDodb";
    private String tableName = "tasks";
    private String idCol = "id";
    private String taskNameCol = "name";
    private String descriptionCol = "description";
    private String statusCol = "status";


    public DatabaseHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + tableName + "("
                + idCol + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + taskNameCol + " TEXT, "
                + descriptionCol + " TEXT, "
                + statusCol + " TEXT "
                + ")";
        db.execSQL(query);
    }


    public void addTask(String taskName, String description, String status){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(taskNameCol, taskName);
        contentValues.put(descriptionCol, description);
        contentValues.put(statusCol, status);

        db.insert(tableName, null,contentValues);
        db.close();
    }

    public ArrayList<Task> readTask(){
        SQLiteDatabase db = this.getReadableDatabase();

        @SuppressLint("Recycle")
        Cursor taskCursor = db.rawQuery("SELECT * from " + tableName , null);
        ArrayList<Task> taskArrayList = new ArrayList<>();

        if(taskCursor.moveToFirst()){
            do {
                taskArrayList.add(new Task(
                        taskCursor.getString(1),
                        taskCursor.getString(2),
                        taskCursor.getString(3)));
            }while (taskCursor.moveToNext());
        }
        taskCursor.close();
        return taskArrayList;
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        onCreate(db);
    }


}
