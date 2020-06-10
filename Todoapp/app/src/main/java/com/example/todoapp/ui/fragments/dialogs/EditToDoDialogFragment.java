package com.example.todoapp.ui.fragments.dialogs;

import com.example.todoapp.models.Tag;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.todoapp.R;
import com.example.todoapp.models.Todo;
import com.example.todoapp.ui.adapter.ExtendedSimpleCursorAdapter;

public class EditToDoDialogFragment extends EditModuleDialogFragment {

    public EditToDoDialogFragment(Todo todo, ExtendedSimpleCursorAdapter adapter) {
        super(todo, adapter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
