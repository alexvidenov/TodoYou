package com.example.todoapp.handlers;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.todoapp.R;
import com.example.todoapp.models.Todo;

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
}
