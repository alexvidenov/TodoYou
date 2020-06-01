package com.example.todoapp.Database.DatabaseHelpers;

import android.content.Context;

import com.example.todoapp.Database.Database;

abstract class ModelHelper {
    Database database;

    ModelHelper(Context context) {
        database = new Database(context);
    }
}
