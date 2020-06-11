package com.example.todoapp.ui.fragments.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.example.todoapp.R;
import com.example.todoapp.database.database_helpers.TagDBHelper;
import com.example.todoapp.database.database_helpers.TodoDBHelper;
import com.example.todoapp.database.database_helpers.TodoTagDBHelper;
import com.example.todoapp.modules.Module;
import com.example.todoapp.modules.Tag;
import com.example.todoapp.modules.Todo;
import com.example.todoapp.ui.adapter.ExtendedSimpleCursorAdapter;

import java.util.List;

// superclass for all dialogfragments relating to editing of the app's modules
// defaults to EditTodo layout
public abstract class EditModuleDialogFragment <T extends Module> extends DialogFragment {
    protected TextView dialogHeader, dialogDescription;
        // ex: title - Edit Tag; description - Input your new values!
    protected Button[] actionButtons; // action buttons at the bottom (back, confirm)
    protected EditText[] editFields; // fields for editing
    protected Button chooseNewTag; // editing todo also allows you to change tags

    protected TagDBHelper tagDBHelper;

    protected List<Tag> allTags; // all tags available
    protected boolean[] selectedTags;

    protected List<Integer> toDoTagIds; // holds tag id's that the user wants in their todo

    protected T module; // given instance of given module (tag/todo)
    protected ExtendedSimpleCursorAdapter adapter; // adapter for the ListView in ViewToDoFragment

    protected void setEditFields() { // overriden later. . .
        editFields[0].setText(module.getTitle()); // all modules have a title, get it here
        if(module instanceof Todo) {
            // get content and date if needed
            editFields[1].setText(((Todo) module).getContent());
            editFields[2].setText(((Todo) module).getDate());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void setActionButtonListeners() { // overridden later. . .
        Button submit = actionButtons[0];
        Button cancel = actionButtons[1];
        Button chooseTag = chooseNewTag;

        TodoDBHelper toDoDBHelper = new TodoDBHelper(getContext()); // get the dialog context

        chooseTag.setOnClickListener((view) -> {
            // TODO: Extract in widget (AlertDialog fragment). Horrid code duplication!
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Choose your Tag!");
            builder.setMultiChoiceItems(
                    allTags.stream()
                            .map(Tag::getTitle)
                            .toArray(String[]::new),
                    selectedTags,
                (dialog, which, isChecked) -> {
                    selectedTags[which] = isChecked;

                    // which - idx
                    // id - tag id
                    int id = which + 1;

                    if(!toDoTagIds.contains(id) && isChecked) {
                        toDoTagIds.add(id); // id = idx + 1 (ids in DB start from 1 and array elements - 0)
                            // add at the id - 1 index
                    } else {
                       toDoTagIds.remove((Integer) id);
                    }
            });

            builder.setCancelable(false);

            builder.setPositiveButton("Add", (dialog, which) -> dialog.dismiss());

            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

            builder.show();
        });

        submit.setOnClickListener((view) -> {
            System.out.println(toDoTagIds);
            final Todo todo = new Todo(
                module.getId(), // ID of new todos are the same as old ones
                editFields[0].getText().toString(),
                editFields[1].getText().toString(),
                editFields[2].getText().toString(),
                    toDoTagIds
            );
            toDoDBHelper.updateToDo(todo);

            adapter.changeCursor(toDoDBHelper.fetchAllTodos()); // swap the old cursor w/ new one w/ new information

            adapter.notifyDataSetChanged(); // reflect the update in the ListView

            getDialog().dismiss(); // dismiss the dialog
        });

        cancel.setOnClickListener((view) -> getDialog().dismiss()); // dismiss the dialog from the view;
    }

    public EditModuleDialogFragment(T module, ExtendedSimpleCursorAdapter adapter) {
        this.module = module;
        this.adapter = adapter;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initTagInfo() {
        Context context = getContext();
        tagDBHelper = new TagDBHelper(context);

        allTags = tagDBHelper.fetchAllTags(); // fetching all tags
        selectedTags = new boolean[allTags.size()];

        // TODO: Ensure that module is todo
        toDoTagIds = new TodoTagDBHelper(context).fetchCorrespondingTagIds(module.getId());
        System.out.println("initial tag ids: " + toDoTagIds);
        toDoTagIds.forEach(id -> selectedTags[id - 1] = true);
    }

    // IMPORTANT:
    // default implementation reverts to todo
    // onCreate layout handling

    // all other DialogFragments should override the onCreateView method
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.d_fragment_edit_todo, container, false);

        dialogHeader = view.findViewById(R.id.edit_todo_header);
        dialogDescription = view.findViewById(R.id.edit_todo_description);

        editFields = new EditText[]{view.findViewById(R.id.edit_todo_title), view.findViewById(R.id.edit_todo_content),
                view.findViewById(R.id.edit_todo_date)};

        actionButtons = new Button[]{view.findViewById(R.id.confirm_button), view.findViewById(R.id.cancel_button)};

        chooseNewTag = view.findViewById(R.id.edit_tag);

        initTagInfo();

        setEditFields();

        setActionButtonListeners();

        return view;
    }
}
