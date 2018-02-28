package com.bignerdranch.android.wintervacationhomework;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 14158 on 2018/2/28.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String PERSONAL_MESSAGE = "create table Message("
            +"name text primary key,"
            +"password text,"
            +"sex text)";


    MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PERSONAL_MESSAGE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
