package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "LoginSignup.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USERS = "users";
    private static final String COL_ID = "id";
    private static final String COL_USERNAME = "username";
    private static final String COL_EMAIL = "email";
    private static final String COL_PASSWORD = "password";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_USERS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USERNAME + " TEXT NOT NULL, " +
                COL_EMAIL + " TEXT NOT NULL UNIQUE, " +
                COL_PASSWORD + " TEXT NOT NULL)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public boolean insertUser(String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        if (checkUserExists(email)) return false;

        ContentValues values = new ContentValues();
        values.put(COL_USERNAME, username);
        values.put(COL_EMAIL, email);
        values.put(COL_PASSWORD, password);

        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    public String loginUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT " + COL_USERNAME + " FROM " + TABLE_USERS +
                        " WHERE " + COL_EMAIL + "=? AND " + COL_PASSWORD + "=?",
                new String[]{email, password}
        );

        String username = null;
        if (cursor.moveToFirst()) {
            username = cursor.getString(0);
        }
        cursor.close();
        return username;
    }

    public boolean checkUserExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT " + COL_ID + " FROM " + TABLE_USERS + " WHERE " + COL_EMAIL + "=?",
                new String[]{email}
        );

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
}
