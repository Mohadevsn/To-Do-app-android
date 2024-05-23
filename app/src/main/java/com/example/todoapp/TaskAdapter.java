package com.example.todoapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<Task> {

    private final Context context;
    private final int layoutId;
    private final ArrayList<Task> data;

    public TaskAdapter(@NonNull Context context, int resource, ArrayList<Task> tasks) {
        super(context, resource, tasks);
        this.context = context;
        this.layoutId = resource;
        this.data = tasks;
    }

    @Override
    public Task getItem(int position) {
        return data.get(position);
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            row = inflater.inflate(layoutId, parent, false);
        }


        TextView taskName = row.findViewById(R.id.taskName);
        Button statusButton = row.findViewById(R.id.statusButton);

        Task task = getItem(position);
        int colorId;

        if (task != null) {
            taskName.setText(task.getTaskName());
            switch (task.getStatus().toLowerCase()){
                case "to do": colorId = R.color.Gris2;
                break;
                case "in progress": colorId = R.color.Bleu;
                break;
                case "done": colorId = R.color.Vert;
                break;
                default:
                    colorId = R.color.Rouge;
            }

            switch (task.getStatus().toLowerCase()) {
                case "to do":
                    row.setBackgroundResource(R.drawable.border_gris2);
                    break;
                case "in progress":
                    row.setBackgroundResource(R.drawable.boder_bleu);
                    break;
                case "done":
                    row.setBackgroundResource(R.drawable.border_vert);
                    break;
                default:
                    row.setBackgroundResource(R.drawable.border_red);
            }



            statusButton.setBackgroundColor(ContextCompat.getColor(context, colorId));
        }

        return row;
    }
}
