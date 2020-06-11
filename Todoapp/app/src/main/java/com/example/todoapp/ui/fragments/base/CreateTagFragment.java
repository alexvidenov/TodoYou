package com.example.todoapp.ui.fragments.base;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.todoapp.R;
import com.example.todoapp.database.database_helpers.TagDBHelper;
import com.example.todoapp.modules.Tag;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateTagFragment extends Fragment implements View.OnClickListener {
    private EditText tagTitle;
    private Button addBtn;
    private TagDBHelper tagDBHelper;

    public CreateTagFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_tag, container, false);

        tagTitle = view.findViewById(R.id.tag_title);
        addBtn = view.findViewById(R.id.add_tag);

        tagDBHelper = new TagDBHelper(getActivity());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.add_tag){
            final String tag = tagTitle.getText().toString();

            if(!tag.equals("")) {
                tagDBHelper.addTag(new Tag(tag));
                getActivity().recreate(); // TODO: Replace w/ something else (use threads and wait/notify maybe)
            }
        }
    }
}
