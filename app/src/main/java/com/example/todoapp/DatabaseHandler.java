package com.example.todoapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final String tableName = "tasks";
    private final String idCol = "id";
    private final String taskNameCol = "name";
    private final String descriptionCol = "description";
    private final String statusCol = "status";


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
        int id;
        ContentValues contentValues = new ContentValues();

        contentValues.put(taskNameCol, taskName);
        contentValues.put(descriptionCol, description);
        contentValues.put(statusCol, status);

        id= (int) db.insert(tableName, null, contentValues);
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
                        taskCursor.getInt(0),
                        taskCursor.getString(1),
                        taskCursor.getString(2),
                        taskCursor.getString(3)));
            }while (taskCursor.moveToNext());
        }
        taskCursor.close();
        return taskArrayList;
    }

    public void updateTask(String id,String taskName, String description, String status){
        SQLiteDatabase db = this.getWritableDatabase();
        int numberRows = 0;
        ContentValues contentValues = new ContentValues();
        contentValues.put(taskNameCol, taskName);
        contentValues.put(descriptionCol, description);
        contentValues.put(statusCol, status);
        Log.e("update", "Status : "+ status, null);

        try{
            numberRows = db.update(tableName, contentValues, idCol + "=?", new String [] {id});
        }catch (SQLException e ){
            Log.e("Sql Exception", "updateTask: "+ e.getMessage(), null);
        }
        Log.e("Update", "Rows affected: " +numberRows, null);
        db.close();
    }

    public ArrayList<Task> filterTaskTodo(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * from "+ tableName+" WHERE "+ statusCol +" = 'to do';";

        @SuppressLint("Recycle")
        Cursor taskCursor = db.rawQuery(query, null);
        ArrayList<Task> taskArrayList = new ArrayList<>();

        if(taskCursor.moveToFirst()){
            do {
                taskArrayList.add(new Task(
                        taskCursor.getInt(0),
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
