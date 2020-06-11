package com.example.todoapp.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.R;
import com.example.todoapp.database.database_helpers.TagDBHelper;
import com.example.todoapp.database.database_helpers.ToDoDBHelper;
import com.example.todoapp.database.database_helpers.TodoTagDBHelper;
import com.example.todoapp.handlers.UIHandler;
import com.example.todoapp.models.Todo;
import com.example.todoapp.ui.fragments.dialogs.EditToDoDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class ExtendedSimpleCursorAdapter extends SimpleCursorAdapter {

    private ToDoDBHelper toDoDBHelper; // helper for toDo DB transactions
    private TagDBHelper tagDBHelper; // helper for tag DB transactions
    private Cursor cursor; // current cursor (to be requeired if needed)
    private Fragment targetFragment; // fragment from wherein the adapter is used (used in dialog invocation)
    private RecyclerView todoRecyclerView; // holds all the tags pertaining to the todo
    private TextView todoId; // useful when searching for all tags of a given todo

    public ExtendedSimpleCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
        super(context, layout, c, from, to);
    }

    public ExtendedSimpleCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }

    public ExtendedSimpleCursorAdapter( // For todos
            @NonNull Cursor cursor,
            @NonNull ToDoDBHelper toDoDBHelper,
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
            @NonNull Cursor cursor,
            @NonNull ToDoDBHelper toDoDBHelper,
            @NonNull TagDBHelper tagDBHelper,
            @NonNull Fragment targetFragment,
            Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        this(context, layout, c, from, to, flags);

        this.cursor = cursor;
        this.toDoDBHelper = toDoDBHelper;
        this.tagDBHelper = tagDBHelper;
        this.targetFragment = targetFragment;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View receivedView = super.getView(position, convertView, parent);

        ImageButton deleteButton, editButton;
        deleteButton = receivedView.findViewById(R.id.delete_todo_button);
        editButton = receivedView.findViewById(R.id.edit_todo_button);
        todoId = receivedView.findViewById(R.id.todo_id);

        todoRecyclerView = receivedView.findViewById(R.id.todoRecyclerView);

        TodoTagDBHelper todoTagDBHelper = new TodoTagDBHelper(targetFragment.getActivity());
        List<Integer> tagIds = todoTagDBHelper.fetchCorrespondingTagIds(Integer.parseInt(todoId.getText().toString()));
        List<String> tagTitles = new ArrayList<>();

        for(int tagId : tagIds){
            String tagTitle = tagDBHelper.fetchSingleTagTitle(tagId);
            tagTitles.add(tagTitle);
        }

        todoRecyclerView.setLayoutManager(new LinearLayoutManager(targetFragment.getActivity(), LinearLayoutManager.HORIZONTAL, false));
        todoRecyclerView.setAdapter(new HorizontalTagAdapter((String[]) tagTitles.toArray()));

        deleteButton.setOnClickListener((view) -> {
                // delete the item visually for the current state
                // (it won't render on the next because it's gone from DB)

            int toDoId = Integer.parseInt(todoId.getText().toString()); // get todo id

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
            editToDoDialogFragment.show(targetFragment.getFragmentManager(), "EditToDoDialogFragment"); // show dialog
        });

        return receivedView;
    }

    public ToDoDBHelper getToDoDBHelper() {
        return toDoDBHelper;
    }

    public void setToDoDBHelper(ToDoDBHelper toDoDBHelper) {
        this.toDoDBHelper = toDoDBHelper;
    }

    public TagDBHelper getTagDBHelper() {
        return tagDBHelper;
    }

    public void setTagDBHelper(TagDBHelper tagDBHelper) {
        this.tagDBHelper = tagDBHelper;
    }
}
