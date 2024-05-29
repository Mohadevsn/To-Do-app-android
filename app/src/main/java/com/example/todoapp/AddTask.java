package com.example.todoapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AddTask extends AppCompatActivity {

    Button addButton;
    FloatingActionButton cancelButton;

    EditText name, description;
    Spinner spinner;

    String nameStr, descriptionStr,Newstatus;
    Spinner_item statusStr;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_task);

        // Initialize the EditText and Spinner views
        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
        spinner = findViewById(R.id.statusSpinner);

        addButton = findViewById(R.id.addButton);
        cancelButton = findViewById(R.id.cancel);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameStr = name.getText().toString();
                descriptionStr = description.getText().toString();
                statusStr = (Spinner_item) spinner.getSelectedItem();
                Newstatus = statusStr.getText();

                if(nameStr.isEmpty() ){
                    Toast.makeText(AddTask.this, "Une tache sans nom n'est pas valide", Toast.LENGTH_LONG).show();
                }

                else {
                    try {
                        DatabaseHandler db = new DatabaseHandler(AddTask.this, "toDodb", null, 1);
                        db.addTask(nameStr, descriptionStr, Newstatus);
                        finish();
                    }catch (Exception e){
                        Log.e("connection failed", "exception", null);
                    }
                }

                // Optionally, you can log the values to check if they are correct
                Log.d("AddTask", "Task Name: " + nameStr + ", Description: " + descriptionStr + ", Status: " + Newstatus);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), MainActivity.class);
                startActivity(i);
            }
        });
        ArrayList<Spinner_item> spinner_list = new ArrayList<Spinner_item>();
        spinner_list.add(new Spinner_item("grey","To Do"));
        spinner_list.add(new Spinner_item("bleu","In progress"));
        spinner_list.add(new Spinner_item("vert","Done"));
        spinner_list.add(new Spinner_item("rouge","Bug"));


        AdapterSpinner adapter = new AdapterSpinner(this,R.layout.spinnertest, spinner_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
