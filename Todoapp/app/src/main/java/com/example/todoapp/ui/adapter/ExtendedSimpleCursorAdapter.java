package com.example.todoapp.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.R;
import com.example.todoapp.database.database_helpers.TagDBHelper;
import com.example.todoapp.database.database_helpers.TodoDBHelper;
import com.example.todoapp.database.database_helpers.TodoTagDBHelper;
import com.example.todoapp.handlers.UIHandler;
import com.example.todoapp.modules.Tag;
import com.example.todoapp.ui.fragments.dialogs.EditTagDialogFragment;
import com.example.todoapp.ui.fragments.dialogs.EditToDoDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class ExtendedSimpleCursorAdapter extends SimpleCursorAdapter {
    private boolean isTag;

    private TodoDBHelper toDoDBHelper; // helper for toDo DB transactions
    private TagDBHelper tagDBHelper; // helper for tag DB transactions

    private ImageButton deleteButton, editButton;

    private Cursor cursor; // current cursor (to be requeired if needed)
    private Fragment targetFragment; // fragment from wherein the adapter is used (used in dialog invocation)

    public ExtendedSimpleCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
        super(context, layout, c, from, to);
    }

    public ExtendedSimpleCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }

    public ExtendedSimpleCursorAdapter( // For todos
            @NonNull Cursor cursor,
            @NonNull TodoDBHelper toDoDBHelper,
            @NonNull Fragment targetFragment,
            Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        this(context, layout, c, from, to, flags);

        this.cursor = cursor;
        this.toDoDBHelper = toDoDBHelper;
        this.targetFragment = targetFragment;
    }

    public ExtendedSimpleCursorAdapter( // For tags
            @NonNull Cursor cursor,
            @NonNull TagDBHelper tagDBHelper,
            @NonNull Fragment targetFragment,
            Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        this(context, layout, c, from, to, flags);

        this.cursor = cursor;
        this.tagDBHelper = tagDBHelper;
        this.targetFragment = targetFragment;
    }

    public ExtendedSimpleCursorAdapter( // For both
                                        boolean isTag,
            @NonNull Cursor cursor,
            @NonNull TodoDBHelper toDoDBHelper,
            @NonNull TagDBHelper tagDBHelper,
            @NonNull Fragment targetFragment,
            Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        this(context, layout, c, from, to, flags);

        this.isTag = isTag;
        this.cursor = cursor;
        this.toDoDBHelper = toDoDBHelper;
        this.tagDBHelper = tagDBHelper;
        this.targetFragment = targetFragment;
    }

    private void initTagList(View receivedView, int toDoId) {
        RecyclerView todoRecyclerView; // holds all the tags pertaining to the todo
        todoRecyclerView = receivedView.findViewById(R.id.todo_recycler_view);

        TodoTagDBHelper todoTagDBHelper = new TodoTagDBHelper(targetFragment.getActivity());
        List<Tag> tags = todoTagDBHelper.fetchCorrespondingTags(toDoId);

        todoRecyclerView.setLayoutManager(
                new LinearLayoutManager(
                        targetFragment.getActivity(),
                        LinearLayoutManager.HORIZONTAL,
                        false
                )
        );

        HorizontalTagAdapter tagListViewAdapter = new HorizontalTagAdapter(
                tags.toArray(new Tag[0])
        );

        todoRecyclerView.setAdapter(
            tagListViewAdapter
        );

        tagListViewAdapter.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View receivedView = super.getView(position, convertView, parent);

        if(isTag) {
            deleteButton = receivedView.findViewById(R.id.delete_tag_button);
            editButton = receivedView.findViewById(R.id.edit_tag_button);

            TextView tagIdTextView = receivedView.findViewById(R.id.tag_id_textView);
            int tagId = Integer.parseInt(tagIdTextView.getText().toString());

            deleteButton.setOnClickListener((view) -> {
                tagDBHelper.deleteTag(tagId);

                cursor.requery();

                notifyDataSetChanged();
            });

            editButton.setOnClickListener((view) -> {
                EditTagDialogFragment editTagDialogFragment =
                        new EditTagDialogFragment(UIHandler.getTagFromView(receivedView), this);

                editTagDialogFragment.setTargetFragment(targetFragment, 1); // set original fragment
                editTagDialogFragment.show(targetFragment.getFragmentManager(), "EditTagDialogFragment");
            });
        } else {
            deleteButton = receivedView.findViewById(R.id.delete_todo_button);
            editButton = receivedView.findViewById(R.id.edit_todo_button);

            TextView toDoIdTextView; // useful when searching for all tags of a given todo
            toDoIdTextView = receivedView.findViewById(R.id.todo_id);
            int toDoId = Integer.parseInt(toDoIdTextView.getText().toString()); // get todo id

            initTagList(receivedView, toDoId);

            deleteButton.setOnClickListener((view) -> {
                // delete the item visually for the current state
                // (it won't render on the next because it's gone from DB)

                toDoDBHelper.deleteTodo(toDoId);

                cursor.requery(); // TODO: Update with LoaderManager and handle cursor state that way

                notifyDataSetChanged();
            });

            editButton.setOnClickListener((view) -> {

                EditToDoDialogFragment editToDoDialogFragment =
                        new EditToDoDialogFragment(
                                UIHandler.getToDoFromView(receivedView),
                                this
                        );
                editToDoDialogFragment.setTargetFragment(targetFragment, 1); // set original fragment
                editToDoDialogFragment.show(targetFragment.getFragmentManager(), "EditToDoDialogFragment");
                // show dialog
            });
        }

        return receivedView;
    }

    public TodoDBHelper getToDoDBHelper() {
        return toDoDBHelper;
    }

    public void setToDoDBHelper(TodoDBHelper toDoDBHelper) {
        this.toDoDBHelper = toDoDBHelper;
    }

    public TagDBHelper getTagDBHelper() {
        return tagDBHelper;
    }

    public void setTagDBHelper(TagDBHelper tagDBHelper) {
        this.tagDBHelper = tagDBHelper;
    }
}
