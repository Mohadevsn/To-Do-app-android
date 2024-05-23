package com.example.todoapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.SQLException;
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

public class ModifyTask extends AppCompatActivity {

    EditText name, description;
    Spinner spinner;

    String nameStr, descriptionStr, statusStr, idStr;

    Button modifyButton;
    Button suppressButton;
    FloatingActionButton cancelButton;
    DatabaseHandler databaseHandler;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_modify_task);

        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
        spinner = findViewById(R.id.statusSpinner);

        modifyButton = findViewById(R.id.modifyButton);
        cancelButton = findViewById(R.id.cancel);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.statusSpinner,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Task task;
        Bundle extra = getIntent().getExtras();
        task = (Task) extra.get("task");


        if(task != null){
            idStr = String.valueOf(task.getId());
            nameStr = task.getTaskName();
            descriptionStr = task.getDescription();
            statusStr = task.getStatus();
            Log.e("extra", "idStr =" + idStr, null);
            Log.e("extra", "nameStr =" + nameStr, null);
            Log.e("extra", "descriptionStr " + descriptionStr, null);
            Log.e("extra", "statusStr " + statusStr, null);
            switch (statusStr.toLowerCase()){
                case "to do": spinner.setSelection(0);
                break;
                case "in progress": spinner.setSelection(1);
                break;
                case "done" : spinner.setSelection(2);
                break;
                default:
                    spinner.setSelection(3);
            }
        }

        name.setText(nameStr);
        description.setText(descriptionStr);

        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseHandler = new DatabaseHandler(v.getContext(), "toDodb", null, 1);
                try {
                    databaseHandler.updateTask(idStr,name.getText().toString(), description.getText().toString(), spinner.getSelectedItem().toString() );
                }catch (SQLException e){
                    Log.e("modify", "onClick: modify Button", null);
                }
                Intent i = new Intent(ModifyTask.this, MainActivity.class);
                startActivity(i);
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}