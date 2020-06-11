package com.example.todoapp.database.database_helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.example.todoapp.database.Database;
import com.example.todoapp.modules.Tag;

import java.util.ArrayList;
import java.util.List;

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

        sqLiteDatabase.delete(
                Database.TABLE_TODOTAGS_NAME,
                Database.COL_TODOTAGS_TAG_ID + "=?",
                new String[]{tagIdString}
        );
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

    public List<String> fetchAllTagTitles() {
        List<String> tagTitles = new ArrayList<>();
        String[] columns = new String[] {
                Database.COL_TAG_ID,
                Database.COL_TAG_TITLE
        };

        Cursor cursor = sqLiteDatabase.query(
                Database.TABLE_TAG_NAME,
                columns,
                null,
                null,
                null,
                null,
                null
        );

        if(cursor.moveToFirst()){
            do{
                String title = cursor.getString(cursor.getColumnIndex(Database.COL_TAG_TITLE));
                tagTitles.add(title);
            } while(cursor.moveToNext());
        }
        cursor.close();

       return tagTitles;
    }

    public List<Tag> fetchAllTags() {
        List<Tag> tags = new ArrayList<>();
        String[] columns = new String[] {
                Database.COL_TAG_ID,
                Database.COL_TAG_TITLE
        };

        Cursor cursor = sqLiteDatabase.query(
                Database.TABLE_TAG_NAME,
                columns,
                null,
                null,
                null,
                null,
                null
        );

        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(cursor.getColumnIndex(Database.COL_TAG_ID));
                String title = cursor.getString(cursor.getColumnIndex(Database.COL_TAG_TITLE));

                tags.add(new Tag(id, title));
            } while(cursor.moveToNext());
        }
        cursor.close();

        return tags;
    }

    public int fetchSingleTagId(String title){
        Cursor cursor = sqLiteDatabase.rawQuery(
                "SELECT _id FROM tags WHERE tag_title LIKE ?",
                new String[]{ title }
        );
        if(cursor.moveToNext()) {
            int tagId = cursor.getInt(cursor.getColumnIndex(Database.COL_TAG_ID));
            cursor.close();
            return tagId;
        }
        throw new SQLException();
    }

    public String fetchSingleTagTitle(int tagID){
        Cursor cursor = sqLiteDatabase.rawQuery(
                "SELECT tag_title FROM tags WHERE _id = ?",
                new String[]{ String.valueOf(tagID) }
        );
        if(cursor.moveToNext()) {
            String tagTitle = cursor.getString(cursor.getColumnIndex(Database.COL_TAG_TITLE));
            cursor.close();
            return tagTitle;
        }
        throw new SQLException();
    }

}
