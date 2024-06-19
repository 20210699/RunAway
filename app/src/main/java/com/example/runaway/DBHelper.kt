package com.example.runaway

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context):SQLiteOpenHelper(context, "memodb",null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        //SQL 쿼리
        db?.execSQL("create table memo_t (_id integer primary key autoincrement, memo not null, date not null)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}