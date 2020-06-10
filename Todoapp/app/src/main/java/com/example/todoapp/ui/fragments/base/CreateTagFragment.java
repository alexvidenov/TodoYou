package com.example.todoapp.ui.fragments.base;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.todoapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateTagFragment extends Fragment {


    public CreateTagFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_tag, container, false);

        return view;
    }

}
