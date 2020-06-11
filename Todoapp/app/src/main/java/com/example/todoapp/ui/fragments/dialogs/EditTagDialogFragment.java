package com.example.todoapp.ui.fragments.dialogs;


import com.example.todoapp.modules.Tag;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.todoapp.R;
import com.example.todoapp.ui.adapter.ExtendedSimpleCursorAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditTagDialogFragment extends EditModuleDialogFragment {


    public EditTagDialogFragment(Tag tag, ExtendedSimpleCursorAdapter adapter) {
        super(tag, adapter);
    }


    @Override
    protected void setEditFields() {
        editFields[0].setText(module.getTitle());
        // could've been smarter to implement tag in superclass. . .
        // eh, save it for later <-> TODO
    }

    @Override
    protected void setActionButtonListeners() { // TODO: Override with specifities for updating tags here
        super.setActionButtonListeners();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.d_fragment_edit_tag, container, false);

        // TODO: Init dialogheader, dialogdescription, editfields, and action buttons
        // TODO: CONVERT TO DIALOGFRAGMENT

        return view;
    }

}
