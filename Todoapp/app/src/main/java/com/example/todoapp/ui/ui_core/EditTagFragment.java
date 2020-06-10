package com.example.todoapp.ui.ui_core;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.todoapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditTagFragment extends Fragment {


    public EditTagFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_edit_tag, container, false);

        // TODO: CONVERT TO DIALOGFRAGMENT

        return view;
    }

}
