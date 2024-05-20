package com.example.todoapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TaskAdapter extends ArrayAdapter<Task> {

    private final Context context;
    int layoutId;

    Task[] data;
    public TaskAdapter(@NonNull Context context, int resource, Task[] task) {
        super(context, resource, task);
        this.context = context;
        layoutId = resource;
        data = task;

    }

    public Task getItem(int position){
        return super.getItem(position);
    }

    @SuppressLint("ViewHolder")
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View row = convertView;
        LayoutInflater inflater = LayoutInflater.from(context);
        row = inflater.inflate(layoutId, parent, false);

        TextView taskName = row.findViewById(R.id.taskName);
        TextView statusButt = row.findViewById(R.id.statusButton);

        Task task = data[position];

        taskName.setText(task.getTaskName());




        return row;
    }
}
