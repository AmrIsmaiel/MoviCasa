package com.Som3a.movicasa.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "myDB.db";
    public static final String BOOKMARKS_TABLE_NAME = "bookmarks";
    public static final String BOOKMARKS_COLUMN_ID = BaseColumns._ID;
    public static final String BOOKMARKS_COLUMN_MOVIE_ID = "movie";
    public static final String BOOKMARKS_COLUMN_STATE = "title";



    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                " CREATE TABLE bookmarks (" +
                        BOOKMARKS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        BOOKMARKS_COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                            BOOKMARKS_COLUMN_STATE + " TEXT NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    //insert new row item
    public void insertBookmark(int id , String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BOOKMARKS_COLUMN_MOVIE_ID, id);
        contentValues.put(BOOKMARKS_COLUMN_STATE,title);
        db.insert(BOOKMARKS_TABLE_NAME, null, contentValues);
    }

    public void deleteBookmark(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(BOOKMARKS_TABLE_NAME,
                BOOKMARKS_COLUMN_MOVIE_ID + " = ? ",
                new String[]{Integer.toString(id)});
    }


    public Cursor getBookmark(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM bookmarks WHERE "
                        + BOOKMARKS_COLUMN_MOVIE_ID + " = " + id
                , null);
    }



    public Cursor getallBookmarks() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT " +BOOKMARKS_COLUMN_ID+","+ BOOKMARKS_COLUMN_MOVIE_ID+ ","+BOOKMARKS_COLUMN_STATE+" FROM bookmarks ORDER BY " + BOOKMARKS_COLUMN_ID, null);
    }

}
