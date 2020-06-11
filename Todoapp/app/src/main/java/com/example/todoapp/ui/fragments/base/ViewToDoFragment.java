package com.example.todoapp.ui.fragments.base;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.todoapp.R;
import com.example.todoapp.database.Database;
import com.example.todoapp.database.database_helpers.TagDBHelper;
import com.example.todoapp.database.database_helpers.TodoDBHelper;
import com.example.todoapp.ui.adapter.ExtendedSimpleCursorAdapter;

public class ViewToDoFragment extends Fragment {
    private ListView todosListView;
    private TodoDBHelper toDoDBHelper;
    private TagDBHelper tagDBHelper;

    private SimpleCursorAdapter adapter;

    final String[] from = new String[] {
            Database.COL_TODO_ID,
            Database.COL_TODO_TITLE,
            Database.COL_TODO_CONTENT,
            Database.COL_TODO_DATE
    };

    final int[] to = new int[] {
            R.id.todo_id,
            R.id.todo_title,
            R.id.todo_content,
            R.id.todo_date
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_todo, container, false); // inflate the fragment view to the screen

        // init ListView
        todosListView = view.findViewById(R.id.todos_listView);
        todosListView.setEmptyView(view.findViewById(R.id.empty));

        // init DB cursor
        Activity fragmentActivity = getActivity();
        toDoDBHelper = new TodoDBHelper(fragmentActivity);
        tagDBHelper = new TagDBHelper(fragmentActivity);
        Cursor cursor = toDoDBHelper.fetchAllTodos();

        // get all todo information in list

        // init ListView adapter
        adapter = new ExtendedSimpleCursorAdapter(
                false,
                cursor,
                toDoDBHelper,
                tagDBHelper,
                this,
                getActivity(), R.layout.single_todo, cursor, from, to, 0
        );
        adapter.notifyDataSetChanged();

        // set ListView adapter and return view
        todosListView.setAdapter(adapter);

        return view;
    }
}
