package com.example.todoapp.ui.ui_core;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.todoapp.R;
import com.example.todoapp.activities.MainActivity;
import com.example.todoapp.database.database_helpers.ToDoDBHelper;
import com.example.todoapp.models.Todo;

import java.util.ArrayList;

public class CreateToDoFragment extends Fragment implements View.OnClickListener {
    private EditText title, content, date, tag;
    private Button addBtn;
    private ToDoDBHelper toDoDBHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_todo, container, false); // inflate the fragment view to the screen
        title = view.findViewById(R.id.title);
        content = view.findViewById(R.id.content);
        date = view.findViewById(R.id.date);
        tag = view.findViewById(R.id.enter_tag_id);

        addBtn = view.findViewById(R.id.add_record);

        toDoDBHelper = new ToDoDBHelper(getActivity()); // the context is the activity, holding the fragments.
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.add_record){
            final String todoTitle = title.getText().toString();
            final String todoContent = content.getText().toString();
            final String todoDate = date.getText().toString();
            final int todoTagsID; // TODO: convert to list later on and get info from dialogfragment

            try {
                todoTagsID = Integer.parseInt(tag.getText().toString());
            } catch(NumberFormatException e) {
                e.printStackTrace();
                return;
            }

            // TODO: Show dialog fragment and get id from DB and then pass an entire list on button click here!
            // TODO: For now, it's just one random ID given in an EditText

            Todo todo;

            try {
                todo = new Todo(
                    todoTitle,
                    todoContent,
                    todoDate,
                    new ArrayList<Integer>(){{ add(todoTagsID); }}
                ); // TODO: Replace with adding entire list
            } catch(IllegalArgumentException e) {
                e.printStackTrace();
                return; // TODO: Add error handling for validation
            }

            toDoDBHelper.addTodo(todo);

        }
    }

    private void returnToHomeScreen(){
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }
}
