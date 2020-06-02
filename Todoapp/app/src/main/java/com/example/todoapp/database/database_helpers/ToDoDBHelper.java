package com.example.todoapp.database.database_helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.todoapp.database.Database;
import com.example.todoapp.models.Todo;


public class ToDoDBHelper extends ModelHelper {

    public ToDoDBHelper(Context context){ // pass the context to the database helper
        super(context);
    }

    public void addTodo(Todo todo){
        SQLiteDatabase sqLiteDatabase = this.database.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Database.COL_TODO_TITLE, todo.getTitle());
        contentValues.put(Database.COL_TODO_CONTENT, todo.getContent());
        contentValues.put(Database.COL_TODO_DATE, todo.getDate());
        contentValues.put(Database.COL_TODO_TAG, todo.getTag());
        sqLiteDatabase.insert(Database.TABLE_TODO_NAME, null, contentValues);
        sqLiteDatabase.close();
    }

    public void deleteTodo(int todoId){
        SQLiteDatabase sqLiteDatabase = this.database.getWritableDatabase();
        sqLiteDatabase.delete(Database.TABLE_TODO_NAME, Database.COL_TODO_ID + "=?", new String[]{String.valueOf(todoId)});
    }

    // implement other methods like update, query all todos, etc.


}











