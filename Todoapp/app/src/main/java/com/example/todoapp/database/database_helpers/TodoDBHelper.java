package com.example.todoapp.database.database_helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.todoapp.database.Database;
import com.example.todoapp.modules.Todo;

import java.util.List;


public class TodoDBHelper extends ModelHelper {

    public TodoDBHelper(Context context){ // pass the context to the database helper
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

        sqLiteDatabase.beginTransaction(); // insert todo tag connections
        long todoID = sqLiteDatabase.insert(
            Database.TABLE_TODO_NAME,
            null,
            todoContentValues
        ); // insert the todo

        for(int tagID : todo.getTagIDs()) {
            ContentValues todoTagsContentValues = new ContentValues();

            todoTagsContentValues.put(Database.COL_TODOTAGS_TODO_ID, todoID);
            todoTagsContentValues.put(Database.COL_TODOTAGS_TAG_ID, tagID);

            sqLiteDatabase.insert(
                Database.TABLE_TODOTAGS_NAME,
                null,
                todoTagsContentValues
            );
        }
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
    }

    public void deleteTodo(int todoId) {
        // TODO: Remove TODO rows from TODOTAGS table of removed TODO
        String todoIdString = String.valueOf(todoId);

        sqLiteDatabase.beginTransaction();

        sqLiteDatabase.delete(
            Database.TABLE_TODOTAGS_NAME,
            Database.COL_TODOTAGS_TODO_ID + "=?",
            new String[]{todoIdString}
        ); // delete todo row connections of deleted todos in TODOTAGS table

        sqLiteDatabase.delete(
            Database.TABLE_TODO_NAME,
            Database.COL_TODO_ID + "=?",
            new String[]{todoIdString}
        );
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
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

    public int fetchSingleTodoId(String name){
        Cursor cursor = sqLiteDatabase.rawQuery(
                "SELECT _id FROM todos WHERE todo_title LIKE ?",
                new String[]{ name }
        );
        if(cursor.moveToFirst()){
            int todoId = cursor.getInt(cursor.getColumnIndex(Database.COL_TODO_ID));
            cursor.close();
            return todoId;
        }
        cursor.close();
        throw new SQLException();
    }

    public void updateToDo(Todo todo) {
        ContentValues todoContentValues = populateToDoValues(todo);
        int toDoId = todo.getId();

        sqLiteDatabase.beginTransaction(); // update todo tag connections
        sqLiteDatabase.update(
                Database.TABLE_TODO_NAME,
                todoContentValues,
                Database.COL_TODO_ID + "=?",
                new String[]{String.valueOf(todo.getId())}
        );

        // TODO: Fix this banal farce of an 'Update' query
        //  by using an actual 'Update' on rows affected (and deleting old ones/inserting new ones)
        // delete all prior tags
        sqLiteDatabase.delete(
                Database.TABLE_TODOTAGS_NAME,
                Database.COL_TODOTAGS_TODO_ID + "=?",
                new String[]{String.valueOf(toDoId)}
        );

        // reinsert
        for(int tagId : todo.getTagIDs()) {
            ContentValues todoTagsContentValues = new ContentValues();

            todoTagsContentValues.put(Database.COL_TODOTAGS_TODO_ID, toDoId);
            todoTagsContentValues.put(Database.COL_TODOTAGS_TAG_ID, tagId);

            // reinsert tags
            sqLiteDatabase.insert(
                Database.TABLE_TODOTAGS_NAME,
                null,
                todoTagsContentValues
            );
                // will update rows with the same tag and todo Id (i.e. the same TODO id)
        }
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
    }

}











