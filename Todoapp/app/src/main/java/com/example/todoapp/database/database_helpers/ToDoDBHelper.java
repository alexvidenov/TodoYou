package com.example.todoapp.database.database_helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.todoapp.database.Database;
import com.example.todoapp.models.Todo;


public class ToDoDBHelper extends ModelHelper {

    public ToDoDBHelper(Context context){ // pass the context to the database helper
        super(context);
    }

    private ContentValues populateToDoValues(Todo todo) {
        ContentValues todoContentValues = new ContentValues();
        todoContentValues.put(Database.COL_TODO_TITLE, todo.getTitle());
        todoContentValues.put(Database.COL_TODO_CONTENT, todo.getContent());
        todoContentValues.put(Database.COL_TODO_DATE, todo.getDate());

        return todoContentValues;
    }

    public void addTodo(Todo todo) {
        ContentValues todoContentValues = populateToDoValues(todo);
        // TODO: Add records in todotags table depending on todo

        sqLiteDatabase.insert(
                Database.TABLE_TODO_NAME,
                null,
                todoContentValues
        ); // insert the todo

        /* sqLiteDatabase.beginTransaction(); // insert todo tag connections
        for(int tagID : todo.getTagIDs()) {
            ContentValues todoTagsContentValues = new ContentValues();

            todoTagsContentValues.put(Database.COL_TODOTAGS_TODO_ID, todo.getId());
            todoTagsContentValues.put(Database.COL_TODOTAGS_TAG_ID, tagID);

            sqLiteDatabase.insert(Database.TABLE_TODOTAGS_NAME, null, todoTagsContentValues);
        }
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction(); */
    }

    public void deleteTodo(int todoId) {
        // TODO: Remove TODO rows from TODOTAGS table of removed TODO
        String todoIdString = String.valueOf(todoId);

        // sqLiteDatabase.beginTransaction();
        sqLiteDatabase.delete(
                Database.TABLE_TODO_NAME,
                Database.COL_TODO_ID + "=?",
                new String[]{todoIdString}
        );

        /* sqLiteDatabase.delete(
                Database.TABLE_TODOTAGS_NAME,
                Database.COL_TODOTAGS_TODO_ID + "=?",
                new String[]{todoIdString}
        ); */
            // delete todo row connections of deleted todos in TODOTAGS table
        // sqLiteDatabase.setTransactionSuccessful();
        // sqLiteDatabase.endTransaction();
    }

    public Cursor fetchAllTodos() {
        // TODO: Add JOIN on tags from TODOTAGS table to fetch the relevant tag IDs for each Tag
        String[] columns = new String[] {
                Database.COL_TODO_ID,
                Database.COL_TODO_TITLE,
                Database.COL_TODO_CONTENT,
                Database.COL_TODO_DATE
        };
        Cursor cursor = sqLiteDatabase.query(
                Database.TABLE_TODO_NAME, columns,
                null,
                null,
                null,
                null,
                null
        );
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public void updateToDo(Todo todo) {
        ContentValues todoContentValues = populateToDoValues(todo);

        sqLiteDatabase.update(
                Database.TABLE_TODO_NAME,
                todoContentValues,
                Database.COL_TODO_ID + "=?",
                new String[]{String.valueOf(todo.getId())}
        );


        /* sqLiteDatabase.beginTransaction(); // update todo tag connections
        for(int tagID : todo.getTagIDs()) {
            ContentValues todoTagsContentValues = new ContentValues();

            todoTagsContentValues.put(Database.COL_TODOTAGS_TODO_ID, todo.getId());
            todoTagsContentValues.put(Database.COL_TODOTAGS_TAG_ID, tagID);

            sqLiteDatabase.replace(Database.TABLE_TODOTAGS_NAME, null, todoTagsContentValues);
                // will replace if there is a row with the same primary key (i.e. the same TODO id)
        }
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction(); */
    }
    // implement other methods like update, delete, etc.


}











