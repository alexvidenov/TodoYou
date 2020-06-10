package com.example.todoapp.ui.fragments.dialogs;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.todoapp.R;
import com.example.todoapp.database.database_helpers.ToDoDBHelper;
import com.example.todoapp.models.Module;
import com.example.todoapp.models.Todo;
import com.example.todoapp.models.Tag;
import com.example.todoapp.ui.adapter.ExtendedSimpleCursorAdapter;

import java.util.ArrayList;

// superclass for all dialogfragments relating to editing of the app's modules
// defaults to EditTodo layout
public abstract class EditModuleDialogFragment <T extends Module> extends DialogFragment {
    protected TextView dialogHeader, dialogDescription;
        // ex: title - Edit Tag; description - Input your new values!
    protected Button[] actionButtons; // action buttons at the bottom (back, confirm)
    protected EditText[] editFields; // fields for editing

    protected T module; // given instance of given module (tag/todo)
    protected ExtendedSimpleCursorAdapter adapter; // adapter for the ListView in ViewToDoFragment

    // TODO: Add 'Edit Tags' button and functionality for edit todo
    //  Can't edit tags for now (add later)

    protected void setEditFields() { // overriden later. . .
        editFields[0].setText(module.getTitle()); // all modules have a title, get it here
        if(module instanceof Todo) {
            // get content and date if needed
            editFields[1].setText(((Todo) module).getContent());
            editFields[2].setText(((Todo) module).getDate());
        }
    }

    protected void setActionButtonListeners() { // overridden later. . .
        Button submit = actionButtons[0];
        Button cancel = actionButtons[1];

        ToDoDBHelper toDoDBHelper = new ToDoDBHelper(getContext()); // get the dialog context

        submit.setOnClickListener((view) -> {
            final Todo todo = new Todo(
                module.getId(), // ID of new todos are the same as old ones
                editFields[0].getText().toString(),
                editFields[1].getText().toString(),
                editFields[2].getText().toString(),
                new ArrayList<>() // TODO: Replace with mapped array of tag Ids
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

    // IMPORTANT:
    // default implementation reverts to todo
    // onCreate layout handling

    // all other DialogFragments should override the onCreateView method
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.d_fragment_edit_todo, container, false);

        dialogHeader = view.findViewById(R.id.edit_todo_header);
        dialogDescription = view.findViewById(R.id.edit_todo_description);

        editFields = new EditText[]{view.findViewById(R.id.edit_todo_title), view.findViewById(R.id.edit_todo_content),
                view.findViewById(R.id.edit_todo_date)};

        actionButtons = new Button[]{view.findViewById(R.id.confirm_button), view.findViewById(R.id.cancel_button)};

        setEditFields();

        setActionButtonListeners();

        return view;
    }
}
