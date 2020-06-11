package com.example.todoapp.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.todoapp.R;
import com.example.todoapp.database.database_helpers.TagDBHelper;
import com.example.todoapp.database.database_helpers.TodoDBHelper;
import com.example.todoapp.database.database_helpers.TodoTagDBHelper;
import com.example.todoapp.handlers.UIHandler;
import com.example.todoapp.modules.Tag;
import com.example.todoapp.ui.fragments.dialogs.EditTagDialogFragment;
import com.example.todoapp.ui.fragments.dialogs.EditToDoDialogFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.SortedSet;

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

    // TODO: Extract the 2 methods below from this Adapter into a separate adapter extending SimpleAdapter
    private SimpleAdapter getToDoTagAdapter(List<Tag> tags) {
        List<HashMap<String, String>> tagsInfo = new ArrayList<>();

        for(Tag tag : tags) {
            tagsInfo.add(new HashMap<String, String>(){{
                put("title", tag.getTitle());
            }}); // add new hashmap to the new tag info row list
        }

        String[] from = new String[]{ "title" };
        int[] to = new int[]{ R.id.tag_title };

        return new SimpleAdapter(
                targetFragment.getContext(),
                tagsInfo,
                R.layout.todo_tag,
                from,
                to
        );
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void initToDoTagGrid(View receivedView, int toDoId) {
        GridView toDoGridView; // holds all the tags pertaining to the todo
        toDoGridView = receivedView.findViewById(R.id.todo_grid_view);

        TodoTagDBHelper todoTagDBHelper = new TodoTagDBHelper(targetFragment.getActivity());
        List<Tag> tags = todoTagDBHelper.fetchCorrespondingTags(toDoId);

        SimpleAdapter toDoGridAdapter = getToDoTagAdapter(tags);

        toDoGridView.setAdapter(
            toDoGridAdapter
        );

        UIHandler.setGridViewDynamicHeight(toDoGridView, (tags.size() / 2) + 1);
            // 2 tags on each row (that's when we know when to rescale the gridview for more items)
            // add 1 to round up to extra row if needed (0.5 + 1 = 1.5 = 2 => make space for 2 columns on 3 elements)

        toDoGridAdapter.notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View receivedView = super.getView(position, convertView, parent);

        // TODO: Replace with goddamn function interfaces
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

            initToDoTagGrid(receivedView, toDoId);

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
