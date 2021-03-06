package com.example.todoapp.database.database_helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.todoapp.database.Database;
import com.example.todoapp.modules.Tag;

import java.util.ArrayList;
import java.util.List;

public class TodoTagDBHelper extends ModelHelper{

    public TodoTagDBHelper(Context context) {
        super(context);
    }

    private ContentValues populateTodoTags(int todoId, int tagId){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Database.COL_TODOTAGS_TODO_ID, todoId);
        contentValues.put(Database.COL_TODOTAGS_TAG_ID, tagId);
        return contentValues;
    }

    public void addTodoTag(int todoId, int tagId){
        ContentValues contentValues = populateTodoTags(todoId, tagId);
        sqLiteDatabase.insert(Database.TABLE_TODOTAGS_NAME, null, contentValues);
    }

    public List<Integer> fetchCorrespondingTagIds(int todoId) {
        List<Integer> tagIds = new ArrayList<>();
       Cursor cursor = sqLiteDatabase.rawQuery("SELECT tt.todotags_tag_id " +
                                   "FROM todotags tt " +
                                   "INNER JOIN todos t ON tt.todotags_todo_id = t._id " +
                                   "WHERE t._id = ? ", new String[]{String.valueOf(todoId)});
        if(cursor.moveToNext()) {
            do{
                int tagId = cursor.getInt(cursor.getColumnIndex(Database.COL_TODOTAGS_TAG_ID));
                tagIds.add(tagId);
            } while(cursor.moveToNext());
       }
       cursor.close();
       return tagIds;
    }

    public List<Tag> fetchCorrespondingTags(int todoId) {
        List<Tag> tags = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT tgs._id, tgs.tag_title " +
                "FROM tags tgs " +
                "INNER JOIN todotags tt ON tt.todotags_tag_id = tgs._id " + // get relevant todo tags
                "INNER JOIN todos t ON tt.todotags_todo_id = t._id " + // get relevant todo tags info
                "WHERE t._id = ? ", new String[]{String.valueOf(todoId)});
        if(cursor.moveToNext()) {
            do{
                int tagId = cursor.getInt(cursor.getColumnIndex(Database.COL_TAG_ID));
                String tagTitle = cursor.getString(cursor.getColumnIndex(Database.COL_TAG_TITLE));
                tags.add(new Tag(tagId, tagTitle));
            } while(cursor.moveToNext());
        }
        cursor.close();
        return tags;
    }

}
