package com.example.todoapp.ui.fragments.dialogs;


import com.example.todoapp.database.database_helpers.TagDBHelper;
import com.example.todoapp.modules.Tag;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void setActionButtonListeners() { // TODO: Override with specifities for updating tags here
        Button submit = actionButtons[0];
        Button cancel = actionButtons[1];

        tagDBHelper = new TagDBHelper(getContext());

        submit.setOnClickListener((view) -> {
            final Tag tag = new Tag(
                    module.getId(),
                    editFields[0].getText().toString()
            );

            tagDBHelper.updateTag(tag);

            adapter.changeCursor(tagDBHelper.fetchAllTagsReturnsCursor());
            adapter.notifyDataSetChanged();

            getDialog().dismiss();
        });

        cancel.setOnClickListener((view) -> {
            getDialog().dismiss();
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.d_fragment_edit_tag, container, false);
        dialogHeader = view.findViewById(R.id.edit_tag_header);
        dialogDescription = view.findViewById(R.id.edit_tag_description);

        editFields = new EditText[]{view.findViewById(R.id.edit_tag_title)};

        actionButtons = new Button[]{view.findViewById(R.id.tag_confirm_button), view.findViewById(R.id.tag_cancel_button)};

        setEditFields();

        setActionButtonListeners();

        return view;
    }

}
