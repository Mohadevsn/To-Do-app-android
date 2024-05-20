package com.example.todoapp;

import android.graphics.PorterDuff;

public class Task {
    private String taskName;
    private String description;
    private String status;


    public String getTaskName() {
        return taskName;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public Task(String taskName, String description, String status) {
        this.taskName = taskName;
        this.description = description;
        this.status = status;
    }


}
