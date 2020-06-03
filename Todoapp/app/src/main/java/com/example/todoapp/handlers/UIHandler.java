package com.example.todoapp.handlers;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;

public class UIHandler { // Handler designed to handle general UI trials and tribulations

    // TODO: May be used later (got replaced with XML styles; go XML!)
    public static void setMenuItemColor(MenuItem item, int color) {
        SpannableString s = new SpannableString(item.getTitle().toString()); // immutable string with mutable markup content
        s.setSpan(new ForegroundColorSpan(color), 0, s.length(), 0);
            // attach markup content from start to length of MenuItem title w/ no flags
        item.setTitle(s); // set the MenuItem title to the Spannable string
    }
}
