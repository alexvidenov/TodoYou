package com.example.todoapp.ui.fragments.base;


import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.todoapp.R;
import com.example.todoapp.database.Database;
import com.example.todoapp.database.database_helpers.TagDBHelper;
import com.example.todoapp.database.database_helpers.TodoDBHelper;
import com.example.todoapp.ui.adapter.ExtendedSimpleCursorAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewTagFragment extends Fragment {
    private ListView tagsListView;
    private TagDBHelper tagDBHelper;
    private TodoDBHelper todoDBHelper;

    private SimpleCursorAdapter adapter;

    final String[] from = new String[] {
            Database.COL_TAG_ID,
            Database.COL_TAG_TITLE,
    };

    final int[] to = new int[] {
            R.id.tag_id_textView,
            R.id.tag_title,
    };

    public ViewTagFragment() {
        // Required empty public constructor
    }

    private void initTagList() {
        Cursor cursor = tagDBHelper.fetchAllTagsReturnsCursor();

        adapter = new ExtendedSimpleCursorAdapter(
                true,
                cursor,
                todoDBHelper,
                tagDBHelper,
                this,
                getActivity(), R.layout.single_tag,
                cursor,
                from,
                to,
                0
        );
        adapter.notifyDataSetChanged();

        // get all tag information in list

        tagsListView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_view_tag, container, false);

        // init ListView
        tagsListView = view.findViewById(R.id.tags_listView);
        tagsListView.setEmptyView(view.findViewById(R.id.tag_empty));

        // init DB cursor
        Activity fragmentActivity = getActivity();
        tagDBHelper = new TagDBHelper(fragmentActivity);
        todoDBHelper = new TodoDBHelper(fragmentActivity);

        initTagList();

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            initTagList();
        }
    }

}
