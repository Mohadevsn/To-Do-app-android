package com.example.todoapp;

import static com.example.todoapp.R.color.Bleu;
import static com.example.todoapp.R.color.Gris2;
import static com.example.todoapp.R.color.Rouge;
import static com.example.todoapp.R.color.Vert;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Objects;

public class AdapterSpinner extends ArrayAdapter<Spinner_item> {
    private Context mContext;
    private int mResource;
    private ArrayList<Spinner_item> mSpinner_item;

    public AdapterSpinner(@NonNull Context context, int resource, @NonNull ArrayList<Spinner_item> spinner_item) {
        super(context, resource, spinner_item);
        this.mContext = context;
        this.mResource = resource;
        this.mSpinner_item = spinner_item;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createViewFromResource(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createViewFromResource(position, convertView, parent);
    }

    @SuppressLint("ResourceAsColor")
    private View createViewFromResource(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(mResource, parent, false);
        }

        View circle = view.findViewById(R.id.status_color);
        TextView textView = view.findViewById(R.id.status_text);

        Spinner_item spinnerItem = mSpinner_item.get(position);
        textView.setText(spinnerItem.getText());

        // Set the background color based on the item's color property
        int colorResId = R.drawable.status_circle;
        if (Objects.equals(spinnerItem.getColor(), "bleu")) {
            colorResId = R.drawable.status_circle_bleu;
            textView.setTextColor(ContextCompat.getColor(mContext,Bleu));

        } else if (Objects.equals(spinnerItem.getColor(), "rouge")){
            colorResId = R.drawable.status_circle_bug;
            textView.setTextColor(ContextCompat.getColor(mContext,Rouge));

        }else if (Objects.equals(spinnerItem.getColor(), "vert")){
            colorResId = R.drawable.status_circle_vert;
            textView.setTextColor(ContextCompat.getColor(mContext,Vert));

        }else{
            colorResId = R.drawable.status_circle;
            textView.setTextColor(ContextCompat.getColor(mContext,Gris2));
        }
        // Add other colors as needed

        Drawable drawable = ContextCompat.getDrawable(mContext, colorResId);
        circle.setBackground(drawable);

        return view;
    }
}
