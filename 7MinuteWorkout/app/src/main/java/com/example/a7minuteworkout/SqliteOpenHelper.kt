package com.example.a7minuteworkout

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqliteOpenHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) : SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    companion object{
        private val DATABASE_VERSION = 1        // We can increment version when we add new column to database or any other changes. We need to pass it inherited sqlite class
        private val DATABASE_NAME = "SevenMinutesWorkout.db"
        private val TABLE_HISTORY = "history"     // table name
        private val COLUMN_ID = "_id"       // Column Id - primary key
        private val COLUMN_COMPLETED_DATE = "completed_date"

    }

    // CREATE TABLE history (_id INTEGER PRIMARY KEY, completed_date TEXT)
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_EXERCISE_TABLE = ("CREATE TABLE " + TABLE_HISTORY + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_COMPLETED_DATE + " TEXT)")
        db?.execSQL(CREATE_EXERCISE_TABLE)
    }

    // Upgrading database - actually dropping and creating new one
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY)
        onCreate(db)
    }

    fun addDate(date: String){
        val values = ContentValues()        // With ContentValues we can add a bunch of values inside and add them to database this way.
        values.put(COLUMN_COMPLETED_DATE, date)

        val db = this.writableDatabase      // this allows us write and read database too.
        db.insert(TABLE_HISTORY, null, values)
        db.close()
    }

    fun getAllCompletedDatesList() : ArrayList<String>{

        val list = ArrayList<String>()
        val db = this.readableDatabase      // get readable database object
        val cursor = db.rawQuery("SELECT * FROM $TABLE_HISTORY", null)      // sql query

        // get row by row from all from db table
        while (cursor.moveToNext()){
            // get all ordered date column and add it to list.
            val dateValue = (cursor.getString(cursor.getColumnIndex(COLUMN_COMPLETED_DATE)))
            list.add(dateValue)
        }
        cursor.close()

        return list
    }
}