package com.example.todoapp.models;

import java.util.List;

public class Todo {
    private int id;
    private String title, content, date;
    private List<Integer> tagIDs;

    public Todo(){ }

    public Todo(String title, String content, String date, List<Integer> tagIDs) throws IllegalArgumentException {
        if(title == null || content == null || date == null || tagIDs == null) {
            throw new IllegalArgumentException();
        }

        this.title = title;
        this.content = content;
        this.date = date;
        this.tagIDs = tagIDs;
    }

    public Todo(int id, String title, String content, String date, List<Integer> tagIDs) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.tagIDs = tagIDs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
