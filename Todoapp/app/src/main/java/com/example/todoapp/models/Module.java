package com.example.todoapp.models;

public abstract class Module {
    protected int id;
    protected String title;

    public Module(String title) {
        this.title = title;
    }

    public Module(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public Module() {

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
}
