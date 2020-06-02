package com.example.todoapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "tododatabase";

    // todo tags (tags are like todo categories)
    public static final String TABLE_TAG_NAME = "tags";
    public static final String COL_TAG_ID = "tag_id";
    public static final String COL_TAG_TITLE = "tag_title";

    // todos
    public static final String TABLE_TODO_NAME = "todos";
    public static final String COL_TODO_ID = "todo_id";
    public static final String COL_TODO_TITLE = "todO_title";
    public static final String COL_TODO_CONTENT = "todo_content";
    public static final String COL_TODO_DATE = "todo_date";
    public static final String COL_TODO_TAG = "todo_tag";

    public static final String FOREIGN_KEY = "PRAGMA foreign_keys=ON"; // allow foreign keys

    // create table queries
    private static final String CREATE_TAGS_TABLE = String.format(
            "CREATE TABLE IF NOT EXISTS %s (" +
                    "%s INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "%s TEXT NOT NULL)", TABLE_TAG_NAME, COL_TAG_ID, COL_TAG_TITLE);

    private static final String CREATE_TODOS_TABLE = String.format(
           "CREATE TABLE IF NOT EXISTS %s (" +
                    "%s INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "FOREIGN KEY("+COL_TODO_TAG+") REFERENCES "+TABLE_TAG_NAME+"("+COL_TAG_ID+")"+")",
            TABLE_TODO_NAME, COL_TODO_ID, COL_TODO_TITLE, COL_TODO_CONTENT, COL_TODO_DATE);

    // drop table queries
    private static final String DROP_TAGS_TABLE = String.format("DROP TABLE IF EXISTS %s", TABLE_TAG_NAME);

    private static final String DROP_TODOS_TABLE = String.format("DROP TABLE IF EXISTS %s", TABLE_TODO_NAME);

    public Database(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TAGS_TABLE);
        db.execSQL(CREATE_TODOS_TABLE);
        db.execSQL(FOREIGN_KEY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TAGS_TABLE);
        db.execSQL(DROP_TODOS_TABLE);
        onCreate(db);
    }
}













