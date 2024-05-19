package com.example.memorization;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper
{
    public DBHelper(@Nullable Context context)
    {
        super(context, "Memorization.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE accounts(id INTEGER PRIMARY KEY AUTOINCREMENT, username VARCHAR(50), password VARCHAR(50), profile BLOB)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS accounts");
        onCreate(db);
    }

    public boolean addAccount(String uname, String pass, byte[] profile)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", uname);
        values.put("password", pass);
        values.put("profile", profile);

        long rowAffected = db.insert("accounts", null, values);

        if(rowAffected != -1)
        {
            return true;
        }
        return false;
    }

    public Cursor getAccount()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM accounts", null);
    }


    public boolean updateAccount(int id, byte[] profile)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("profile", profile);

        long rowAffected = db.update("accounts", values, "id = ?", new String[]{String.valueOf(id)});

        if(rowAffected > 0)
        {
            return true;
        }
        return false;
    }

}
