package com.example.todoapp.database.database_helpers;

import android.content.Context;

import com.example.todoapp.database.Database;

abstract class ModelHelper {
    Database database;

    ModelHelper(Context context) {
        database = new Database(context);
    }
}
