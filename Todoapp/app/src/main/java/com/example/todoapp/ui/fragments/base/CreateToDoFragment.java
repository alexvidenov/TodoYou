package com.example.todoapp.ui.fragments.base;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.example.todoapp.database.database_helpers.TagDBHelper;
import com.example.todoapp.database.database_helpers.ToDoDBHelper;
import com.example.todoapp.database.database_helpers.TodoTagDBHelper;
import com.example.todoapp.models.Todo;

import java.util.ArrayList;
import java.util.List;

public class CreateToDoFragment extends Fragment implements View.OnClickListener {
    private EditText title, content, date;
    private Button addBtn, tag;
    private ToDoDBHelper toDoDBHelper;
    private TagDBHelper tagDBHelper;
    private TodoTagDBHelper todoTagDBHelper;

    private String[] allTags; // all tags available
    private boolean[] selectedTags;
    private List<Integer> finalTags; // holds indexes of values from allTags that are checked

    private List<Integer> todoTagIds; // holds tag id's that the user wants in their todo

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_todo, container, false); // inflate the fragment view to the screen
        title = view.findViewById(R.id.title);
        content = view.findViewById(R.id.content);
        date = view.findViewById(R.id.date);

        addBtn = view.findViewById(R.id.add_record);
        tag = view.findViewById(R.id.enter_tag_id);

        toDoDBHelper = new ToDoDBHelper(getActivity()); // the context is the activity, holding the fragments.
        tagDBHelper = new TagDBHelper(getActivity());
        todoTagDBHelper = new TodoTagDBHelper(getActivity());

        allTags = (String[]) tagDBHelper.fetchAllTags().toArray(); // fetching all tags
        selectedTags = new boolean[allTags.length];
        finalTags = new ArrayList<>();
        todoTagIds = new ArrayList<>();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addBtn.setOnClickListener(this);
        tag.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_record:
                final String todoTitle = title.getText().toString();
                final String todoContent = content.getText().toString();
                final String todoDate = date.getText().toString();

                Todo todo;

                try {
                    todo = new Todo(
                            todoTitle,
                            todoContent,
                            todoDate,
                            todoTagIds
                    );
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    return;
                }

                toDoDBHelper.addTodo(todo);

                getActivity().recreate(); // retrigger the state (rebuild it for the new todo)
                break;

            case R.id.enter_tag_id:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Choose your Tag!");
                builder.setMultiChoiceItems(allTags, selectedTags, (dialog, which, isChecked) -> {
                    if(isChecked){
                        if(!finalTags.contains(which)){
                            finalTags.add(which);
                        } else {
                            finalTags.remove(which);
                        }
                    }
                });

                builder.setCancelable(false);

                builder.setPositiveButton("Add", (dialog, which) -> {
                    for(int i = 0; i < finalTags.size(); i++){
                        String currentTagName = allTags[finalTags.get(i)];
                        int currentTagId = tagDBHelper.fetchSingleTagId(currentTagName); // adding the tagId to the list of tagIds
                        todoTagIds.add(currentTagId);
                    }
                });

                builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        }
    }
}
