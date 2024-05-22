package com.example.todoapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    TaskAdapter taskAdapter;
    FloatingActionButton goAdd;
    DatabaseHandler databaseHandler;
    ArrayList<Task> taskArrayList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        goAdd = findViewById(R.id.goAdd);

        databaseHandler = new DatabaseHandler(this, "toDodb", null, 1);
        taskArrayList = databaseHandler.readTask();

        Log.d("MainActivity", "Tasks loaded: " + taskArrayList.size()); // Check if tasks are loaded

        listView = findViewById(R.id.task_list);
        taskAdapter = new TaskAdapter(this, R.layout.row, taskArrayList);
        listView.setAdapter(taskAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("MainActivity", "Item clicked at position: " + position); // Log the click
                Task clickedTask = taskArrayList.get(position);
                Log.d("MainActivity", "Clicked Task: " + clickedTask.getTaskName()); // Log task details

                Intent i = new Intent(MainActivity.this, ModifyTask.class);
                i.putExtra("task", clickedTask);
                startActivity(i);
            }
        });

        goAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), AddTask.class);
                startActivity(i);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
