package com.example.todoapp.ui.fragments.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.todoapp.R;
import com.example.todoapp.database.database_helpers.TagDBHelper;
import com.example.todoapp.database.database_helpers.TodoDBHelper;
import com.example.todoapp.modules.Tag;
import com.example.todoapp.modules.Todo;

import java.util.ArrayList;
import java.util.List;

public class CreateToDoFragment extends Fragment implements View.OnClickListener {
    private EditText title, content, date;
    private Button addBtn, tag;

    private TodoDBHelper toDoDBHelper;
    private TagDBHelper tagDBHelper;

    private List<Tag> allTags; // all tags available
    private boolean[] selectedTags;
    private List<Integer> finalTagIndices; // holds indexes of values from allTagTitles that are checked

    private List<Integer> todoTagIds; // holds tag id's that the user wants in their todo

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_todo, container, false); // inflate the fragment view to the screen
        title = view.findViewById(R.id.title);
        content = view.findViewById(R.id.content);
        date = view.findViewById(R.id.date);

        addBtn = view.findViewById(R.id.add_record);
        tag = view.findViewById(R.id.choose_tag);

        Activity fragmentActivity = getActivity();
        toDoDBHelper = new TodoDBHelper(fragmentActivity); // the context is the activity, holding the fragments.
        tagDBHelper = new TagDBHelper(fragmentActivity);

        allTags = tagDBHelper.fetchAllTags(); // fetching all tags
        selectedTags = new boolean[allTags.size()];
        finalTagIndices = new ArrayList<>();
        todoTagIds = new ArrayList<>();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addBtn.setOnClickListener(this);
        tag.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
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

            case R.id.choose_tag:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Choose your Tag!");
                builder.setMultiChoiceItems(
                        allTags.stream()
                                .map(Tag::getTitle)
                                .toArray(String[]::new),
                        selectedTags,
                (dialog, which, isChecked) -> {
                    selectedTags[which] = isChecked;
                    if(!finalTagIndices.contains(which) && isChecked) {
                        finalTagIndices.add(which);
                    } else {
                        finalTagIndices.remove(which == finalTagIndices.size() ? which - 1 : which);
                    }
                });

                builder.setCancelable(false);

                builder.setPositiveButton("Add", (dialog, which) -> {
                    for(int i = 0; i < finalTagIndices.size(); i++){
                        Tag currentTag = allTags.get(finalTagIndices.get(i));
                        todoTagIds.add(currentTag.getId());
                    }
                });

                builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

                builder.show();
                break;
        }
    }
}
