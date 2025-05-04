package com.example.cleanit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class CheckItemDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "checkitems.db";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "items";
    private static final String COL_ID = "id";
    private static final String COL_TEXT = "text";
    private static final String COL_CHECKED = "checked";

    public CheckItemDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_TEXT + " TEXT NOT NULL,"
                + COL_CHECKED + " INTEGER NOT NULL DEFAULT 0)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertItem(CheckItem item) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_TEXT, item.getText());
        values.put(COL_CHECKED, item.isChecked() ? 1 : 0);
        db.insert(TABLE_NAME, null, values);
    }

    public void deleteItem(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, COL_ID + "=?", new String[]{String.valueOf(id)});
    }

    public List<CheckItem> getAllItems() {
        List<CheckItem> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
                String text = cursor.getString(cursor.getColumnIndexOrThrow(COL_TEXT));
                boolean checked = cursor.getInt(cursor.getColumnIndexOrThrow(COL_CHECKED)) == 1;
                list.add(new CheckItem(id, text, checked));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public void clearAll() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
    }
}
