package com.example.irishka.movieapp.ui;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.irishka.movieapp.R;

import java.util.List;

public class ExampleAdapter extends CursorAdapter {

    private List<String> items;

    private TextView text;

    public ExampleAdapter(Context context, Cursor cursor, List<String> items) {

        super(context, cursor, false);

        this.items = items;

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        if (cursor.getPosition() < items.size())
        text.setText(items.get(cursor.getPosition()));

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.search, parent, false);

        text = (TextView) view.findViewById(R.id.text);

        return view;

    }

}
