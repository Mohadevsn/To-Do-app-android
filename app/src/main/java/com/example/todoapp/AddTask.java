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

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddTask extends AppCompatActivity {

    Button addButton;
    FloatingActionButton cancelButton;

    EditText name, description;
    Spinner spinner;

    String nameStr, descriptionStr, statusStr;

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
                // Get the values from the EditTexts and Spinner
                nameStr = name.getText().toString();
                descriptionStr = description.getText().toString();
                statusStr = spinner.getSelectedItem().toString();

                // Now you can use these strings, for example to add the task to the database

                try {
                    DatabaseHandler db = new DatabaseHandler(AddTask.this, "toDodb", null, 1);
                    db.addTask(nameStr, descriptionStr, statusStr);
                }catch (Exception e){
                    Log.e("connection failed", "exception", null);
                }


                // Optionally, you can log the values to check if they are correct
                Log.d("AddTask", "Task Name: " + nameStr + ", Description: " + descriptionStr + ", Status: " + statusStr);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), MainActivity.class);
                startActivity(i);
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.statusSpinner,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
