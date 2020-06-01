package com.example.todoapp.Models;

public class Todo {
    private int id;
    private String title, content, date, tag;

    public Todo(){ }

    public Todo(String title, String content, String date, String tag) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.tag = tag;
    }

    public Todo(int id, String title, String content, String date, String tag) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.tag = tag;
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
