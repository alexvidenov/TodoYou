package com.example.todoapp.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.opengl.Visibility;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.todoapp.R;
import com.example.todoapp.database.database_helpers.TagDBHelper;
import com.example.todoapp.database.database_helpers.ToDoDBHelper;

public class ExtendedSimpleCursorAdapter extends SimpleCursorAdapter {

    private ToDoDBHelper toDoDBHelper; // helper for toDo DB transactions
    private TagDBHelper tagDBHelper; // helper for tag DB transactions
    private Cursor cursor; // current cursor (to be required if needed)

    public ExtendedSimpleCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
        super(context, layout, c, from, to);
    }

    public ExtendedSimpleCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }

    public ExtendedSimpleCursorAdapter(
            @NonNull ToDoDBHelper toDoDBHelper,
            @NonNull TagDBHelper tagDBHelper,
            Context context, int layout, Cursor c, String[] from, int[] to) {
        this(context, layout, c, from, to);

        this.toDoDBHelper = toDoDBHelper;
        this.tagDBHelper = tagDBHelper;
    }

    public ExtendedSimpleCursorAdapter(
            @NonNull ToDoDBHelper toDoDBHelper,
            Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        this(context, layout, c, from, to, flags);

        this.toDoDBHelper = toDoDBHelper;
    }

    public ExtendedSimpleCursorAdapter(
            @NonNull Cursor cursor,
            @NonNull ToDoDBHelper toDoDBHelper,
            Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        this(context, layout, c, from, to, flags);

        this.toDoDBHelper = toDoDBHelper;
        this.tagDBHelper = tagDBHelper;
        this.cursor = cursor;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View receivedView = super.getView(position, convertView, parent);

        ImageButton deleteButton, editButton;
        deleteButton = receivedView.findViewById(R.id.delete_todo_button);
        editButton = receivedView.findViewById(R.id.edit_todo_button);

        deleteButton.setOnClickListener((view) -> {
                // delete the item visually for the current state
                // (it won't render on the next because it's gone from DB)

            TextView toDoIdTextView = receivedView.findViewById(R.id.todo_id);
            int toDoId = Integer.parseInt(toDoIdTextView.getText().toString()); // get todo id

            toDoDBHelper.deleteTodo(toDoId);

            cursor.requery(); // TODO: Update with LoaderManager and handle cursor state that way

            notifyDataSetChanged();
        });

        editButton.setOnClickListener((view) -> {

        });

        return receivedView;
    }

    public ToDoDBHelper getToDoDBHelper() {
        return toDoDBHelper;
    }

    public void setToDoDBHelper(ToDoDBHelper toDoDBHelper) {
        this.toDoDBHelper = toDoDBHelper;
    }

    public TagDBHelper getTagDBHelper() {
        return tagDBHelper;
    }

    public void setTagDBHelper(TagDBHelper tagDBHelper) {
        this.tagDBHelper = tagDBHelper;
    }
}
