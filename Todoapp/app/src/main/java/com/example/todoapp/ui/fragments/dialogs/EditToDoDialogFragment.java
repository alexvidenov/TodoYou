package com.example.todoapp.ui.fragments.dialogs;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.todoapp.modules.Todo;
import com.example.todoapp.ui.adapter.ExtendedSimpleCursorAdapter;

public class EditToDoDialogFragment extends EditModuleDialogFragment {

    public EditToDoDialogFragment(Todo todo, ExtendedSimpleCursorAdapter adapter) {
        super(todo, adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
