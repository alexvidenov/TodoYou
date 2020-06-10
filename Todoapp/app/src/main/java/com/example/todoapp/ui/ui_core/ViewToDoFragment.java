package com.example.todoapp.ui.ui_core;

import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.todoapp.R;
import com.example.todoapp.database.Database;
import com.example.todoapp.database.database_helpers.ToDoDBHelper;
import com.example.todoapp.ui.adapter.ExtendedSimpleCursorAdapter;

public class ViewToDoFragment extends Fragment implements ListView.OnItemClickListener {
    private ListView todosListView;
    private ToDoDBHelper toDoDBHelper;

    private SimpleCursorAdapter adapter;

    private ImageButton deleteButton, editButton;

    final String[] from = new String[] {
            Database.COL_TODO_ID,
            Database.COL_TODO_TITLE,
            Database.COL_TODO_CONTENT,
    };

    final int[] to = new int[] { R.id.todo_id, R.id.todo_title, R.id.todo_content };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_todo, container, false); // inflate the fragment view to the screen

        // init ListView
        todosListView = view.findViewById(R.id.todos_listView);
        todosListView.setEmptyView(view.findViewById(R.id.empty));

        // init DB cursor
        toDoDBHelper = new ToDoDBHelper(getActivity());
        Cursor cursor = toDoDBHelper.fetchAllTodos();

        // get all todo information in list

        // init ListView adapter
        adapter = new ExtendedSimpleCursorAdapter(
                cursor,
                toDoDBHelper,
                getActivity(), R.layout.single_todo, cursor, from, to, 0
        );
        adapter.notifyDataSetChanged();

        // set ListView adapter and return view
        todosListView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // here, open the EditToDoFragment to update/delete the desired todo
    }


}
