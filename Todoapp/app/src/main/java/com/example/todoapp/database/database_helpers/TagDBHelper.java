package com.example.todoapp.database.database_helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.todoapp.database.Database;
import com.example.todoapp.models.Tag;
import com.example.todoapp.models.Todo;

public class TagDBHelper extends ModelHelper {

    public TagDBHelper(Context context){ // pass the context to the database helper
        super(context);
    }

    private ContentValues populateTagValues(Tag tag) {
        ContentValues tagContentValues = new ContentValues();
        tagContentValues.put(Database.COL_TAG_TITLE, tag.getTitle());

        return tagContentValues;
    }

    public void addTag(Tag tag) {
        ContentValues tagContentValues = populateTagValues(tag);
        sqLiteDatabase.insert(Database.TABLE_TAG_NAME, null, tagContentValues);
    }

    public void deleteTag(int tagId){
        String tagIdString = String.valueOf(tagId);

        sqLiteDatabase.beginTransaction();
        sqLiteDatabase.delete(
                Database.TABLE_TAG_NAME,
                Database.COL_TAG_ID + "=?",
                new String[]{tagIdString}
        );

        /* sqLiteDatabase.delete(
                Database.TABLE_TODOTAGS_NAME,
                Database.COL_TODOTAGS_TAG_ID + "=?",
                new String[]{tagIdString}
        ); */
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
    }

    // implement other methods like update, query all todo tags, etc.
    public void updateTag(Tag tag) {
        ContentValues tagContentValues = populateTagValues(tag);

        sqLiteDatabase.update(
                Database.TABLE_TAG_NAME,
                tagContentValues,
                Database.COL_TAG_ID + "=?",
                new String[]{String.valueOf(tag.getId())}
        );
    }

}
