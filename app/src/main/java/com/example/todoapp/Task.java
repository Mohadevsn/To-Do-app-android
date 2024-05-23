package com.example.todoapp;

import java.io.Serializable;

public class Task implements Serializable {

    private int id;

    public int getId() {
        return id;
    }

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

    public Task(int id, String taskName, String description, String status) {
        this.id = id;
        this.taskName = taskName;
        this.description = description;
        this.status = status;
    }


}
