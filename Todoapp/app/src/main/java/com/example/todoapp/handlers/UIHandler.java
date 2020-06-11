package com.example.todoapp.handlers;

import android.os.Build;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.todoapp.R;
import com.example.todoapp.modules.Tag;
import com.example.todoapp.modules.Todo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UIHandler { // Handler designed to handle general UI trials and tribulations

    // TODO: May be used later (got replaced with XML styles; go XML!)
    public static void setMenuItemColor(MenuItem item, int color) {
        SpannableString s = new SpannableString(item.getTitle().toString()); // immutable string with mutable markup content
        s.setSpan(new ForegroundColorSpan(color), 0, s.length(), 0);
            // attach markup content from start to length of MenuItem title w/ no flags
        item.setTitle(s); // set the MenuItem title to the Spannable string
    }

    // Map structure is as follows:
    // id - contains the id of the todo
    // title - contains the title of the todo
    // content - contains the content of the todo
    // date - contains the due date of the todo
    // tagIds - contains all the applicable tag Ids for the todo
    public static Map<String, Object> getToDoValuesByView(View toDoView) {
        Map<String, Object> result = new HashMap<>();

        TextView toDoIdTextView = toDoView.findViewById(R.id.todo_id);
        Integer toDoId = Integer.parseInt(toDoIdTextView.getText().toString()); // get 2do id
        result.put("id", toDoId);

        TextView toDoTitleTextView = toDoView.findViewById(R.id.todo_title);
        String toDoTitle = toDoTitleTextView.getText().toString();
        result.put("title", toDoTitle);

        TextView toDoContentTextView = toDoView.findViewById(R.id.todo_content);
        String toDoContent = toDoContentTextView.getText().toString();
        result.put("content", toDoContent);

        TextView toDoDateTextView = toDoView.findViewById(R.id.todo_date);
        String toDoDate = toDoDateTextView.getText().toString();
        result.put("date", toDoDate);

        result.put("tagIds", new ArrayList<Integer>());
        // TODO: Add mapping to tag IDs for tags ListView
        //  by iterating through the listview inside of the current view

        return result;
    }

    public static Map<String, Object> getTagValuesByView(View tagView){
        Map<String, Object> result = new HashMap<>();
        TextView tagIdTextView = tagView.findViewById(R.id.tag_id_textView);

        Integer tagId = Integer.parseInt(tagIdTextView.getText().toString()); // get tag id
        result.put("id", tagId);

        TextView tagTitleTextView = tagView.findViewById(R.id.tag_title);
        String tagTitle = tagTitleTextView.getText().toString();
        result.put("title", tagTitle);

        return result;
    }

    public static Todo getToDoFromView(View todoView) {
        Map<String, Object> values = getToDoValuesByView(todoView);

        return new Todo(
                (int) values.get("id"),
                (String) values.get("title"),
                (String) values.get("content"),
                (String) values.get("date"),
                (List<Integer>) values.get("tagIds")
        );
    }

    public static Tag getTagFromView(View tagView){
        Map<String, Object> values = getTagValuesByView(tagView);

        return new Tag(
                (int) values.get("id"),
                (String) values.get("title")
        );
    }

    // Credit to Hiren Patel from SO for this method: https://stackoverflow.com/questions/6005245/how-to-have-a-gridview-that-adapts-its-height-when-items-are-added
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void setGridViewDynamicHeight(GridView gridView, int columnNumber) {
        ListAdapter gridViewAdapter = gridView.getAdapter();

        if (gridViewAdapter == null || columnNumber == 1) {
            // pre-condition (guard clause)
            return;
        }

        int items = gridViewAdapter.getCount();
        int rows;

        View listItem = gridViewAdapter.getView(0, null, gridView); // get first li view (all are the same)
        listItem.measure(0, 0); // measure li

        int gridRowVerticalSpacing = gridView.getVerticalSpacing();

        int totalHeight = listItem.getMeasuredHeight() + (gridRowVerticalSpacing > 0 ? gridRowVerticalSpacing : 1);
            // get current height with account for vertical spacing (safeguard if it's not present)

        float x;
        if(items > columnNumber){
            x = items / columnNumber; // get number of rows needed to display by dividing items to columns
            rows = (int) (x + 1); // increase rows (round by casting to int)
            totalHeight *= rows; // increase height
        }

        ViewGroup.LayoutParams params = gridView.getLayoutParams(); // get old params
        params.height = totalHeight; // set new height
        gridView.setLayoutParams(params); // set new params with new height
    }
}
