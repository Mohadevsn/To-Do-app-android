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
import java.util.Arrays;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final String tableName = "tasks";
    private final String idCol = "id";
    private final String taskNameCol = "name";
    private final String descriptionCol = "description";
    private final String statusCol = "status";
    ArrayList<Task> taskArrayList = new ArrayList<>();


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
        Log.e("dbhandler", "readTask: taskArrayList"+ taskArrayList, null);
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

    public ArrayList<Task> filter(ArrayList<String> status){
        SQLiteDatabase db = this.getReadableDatabase();
        //String query = "SELECT * from "+ tableName+" WHERE "+ statusCol +" = 'to do';";
        if(status.isEmpty()){
            return readTask();
        }
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM "+tableName+ " WHERE ");
        for (int i = 0; i < status.size(); i++){
            queryBuilder.append(statusCol+" =? ");
            if(i < status.size()-1){
                queryBuilder.append(" OR ");
            }
        }

        Log.e("dbHandler", "filter: queryBuilder " + queryBuilder, null);
        Log.e("dbHandler", "filter: status size " + status.size(), null);
        String[] statusArray = status.toArray(new String[0]);
        Log.e("dbHandler", "filter: StatusArray: " + Arrays.toString(statusArray), null);
        Cursor taskCursor = db.rawQuery(queryBuilder.toString(), statusArray);

        // Log cursor count to see if there are any results
        Log.e("dbHandler", "filter: Cursor count " + taskCursor.getCount(), null);

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

        Log.e("dbHandler", "filter: TaskArrayList : "+ taskArrayList, null);
        return taskArrayList;
    }



    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        onCreate(db);
    }

}
