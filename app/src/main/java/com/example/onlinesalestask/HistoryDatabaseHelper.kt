package com.example.onlinesalestask

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class HistoryDatabaseHelper(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "history.db"
        private const val TABLE_HISTORY = "history"
        private const val COLUMN_ID = "id"
        private const val COLUMN_EXPRESSION = "expression"
        private const val COLUMN_RESULT = "result"
        private const val COLUMN_SUBMISSION_DATE = "submission_date"
    }

    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL("CREATE TABLE $TABLE_HISTORY( id integer primary key autoincrement, expression TEXT, result TEXT, submission_date TEXT)")

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Handle database upgrades if needed
    }

    // Function to add a new history entry
    fun addHistoryEntry(entry: HistoryEntry) {

        val db = this.writableDatabase
        val values = ContentValues()

        values.put(COLUMN_EXPRESSION, entry.expression)
        values.put(COLUMN_RESULT, entry.result)
        values.put(COLUMN_SUBMISSION_DATE, entry.submissionDate)

        try {
            // Insert the data into the database with conflict handling
            db.insert(TABLE_HISTORY, null, values)
        } catch (e: Exception) {
            // Handle any exceptions that may occur during insertion
            e.printStackTrace()
        } finally {
           // db.close()
        }
    }

    // Function to retrieve all history entries

    fun getAllHistoryEntries(): List<HistoryEntry> {
        val historyEntries = mutableListOf<HistoryEntry>()
        val db = readableDatabase
        val cursor: Cursor? = db.rawQuery("SELECT * FROM $TABLE_HISTORY", null)

        cursor?.use {
            if (it.moveToFirst()) {
                val idIndex = it.getColumnIndex(COLUMN_ID)
                val expressionIndex = it.getColumnIndex(COLUMN_EXPRESSION)
                val resultIndex = it.getColumnIndex(COLUMN_RESULT)
                val submissionDateIndex = it.getColumnIndex(COLUMN_SUBMISSION_DATE)

                do {
                    if (idIndex != -1) {
                        val id = it.getLong(idIndex)
                        val expression = it.getString(expressionIndex)
                        val result = it.getString(resultIndex)
                        val submissionDate = it.getString(submissionDateIndex)
                        historyEntries.add(HistoryEntry(id, expression, result, submissionDate))
                    } else {
                        // Handle the case where the ID column does not exist in the cursor
                        // You can choose to log an error or take other appropriate action
                    }
                } while (it.moveToNext())
            }
        }

        cursor?.close()
       // db.close()
        return historyEntries
    }


}