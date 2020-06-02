package com.example.todoapp.database.database_helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.todoapp.database.Database;

abstract class ModelHelper {
    protected Database database;
    protected SQLiteDatabase sqLiteDatabase;

    ModelHelper(Context context) {
        database = new Database(context);
        sqLiteDatabase = this.database.getWritableDatabase();
    }

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public SQLiteDatabase getSqLiteDatabase() {
        return sqLiteDatabase;
    }

    public void setSqLiteDatabase(SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase = sqLiteDatabase;
    }
}
