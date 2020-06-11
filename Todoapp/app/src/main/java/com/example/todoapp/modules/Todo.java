package com.example.todoapp.modules;

import androidx.annotation.NonNull;

import java.util.List;

public class Todo extends Module {
    private String content, date;
    private List<Integer> tagIDs;

    public Todo() {}

    public Todo(
            @NonNull String title,
            @NonNull String content,
            @NonNull String date,
            @NonNull List<Integer> tagIDs
    ) {
        super(title);
        this.content = content;
        this.date = date;
        this.tagIDs = tagIDs;
    }

    public Todo(
            int id,
            @NonNull String title,
            @NonNull String content,
            @NonNull String date,
            @NonNull List<Integer> tagIDs
    ) {
        super(id, title);
        this.content = content;
        this.date = date;
        this.tagIDs = tagIDs;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Integer> getTagIDs() {
        return tagIDs;
    }

    public void setTag(List<Integer> tagIDs) {
        this.tagIDs = tagIDs;
    }
}
