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
        tag = view.findViewById(R.id.enter_tag);

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
            final String todoTag = tag.getText().toString();
            final Todo todo = new Todo(todoTitle, todoContent, todoDate, todoTag);
            toDoDBHelper.addTodo(todo);

            returnToHomeScreen();
        }
    }

    private void returnToHomeScreen(){
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }
}
