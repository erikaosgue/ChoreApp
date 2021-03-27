package com.erikaosgue.choresapp.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import android.widget.Toast
import com.erikaosgue.choresapp.model.*
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList

class ChoresDatabaseHandler(val context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_CHORE_TABLE = "CREATE TABLE $TABLE_NAME (" +
                "$KEY_ID INTEGER PRIMARY KEY, " +
                "$KEY_CHORE_NAME TEXT, $KEY_CHORE_ASSIGNED_BY TEXT, " +
                "$KEY_CHORE_ASSIGNED_TO TEXT, $KEY_CHORE_ASSIGNED_TIME LONG)"
        Log.d("success", "Enter create: $CREATE_CHORE_TABLE")
        db?.execSQL(CREATE_CHORE_TABLE)


    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")

        //create a New table "Again"
        onCreate(db)
    }

    /**
     * CRUD - Create, Read, Update, Delete
     **/
    fun createChore(chore: Chore) {


        val db: SQLiteDatabase = this.writableDatabase

        // values is an object that will store a set of values (hashMap)
        val values = ContentValues()


        //Putting a key-value into the Value Object
        values.put(KEY_CHORE_NAME, chore.choreName)
        values.put(KEY_CHORE_ASSIGNED_BY, chore.assignedBy)
        values.put(KEY_CHORE_ASSIGNED_TO, chore.assignedTo)
        values.put(KEY_CHORE_ASSIGNED_TIME, System.currentTimeMillis())

        // Inserting  to the db the Table name, column hack=null and the Values
        val result = db.insert(TABLE_NAME, null, values)
        if (result == (0).toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
            Log.d("DATA NOT INSERTED", "FAIL")
        }
        else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
            Log.d("DATA INSERTED", "SUCCESS")
        }

        Log.d("DATA INSERTED", "SUCCESS")
        db.close()
    }


    fun readChores(): ArrayList<Chore> {

        val db: SQLiteDatabase = readableDatabase
        val list: ArrayList<Chore> = ArrayList()

        val query = "SELECT * FROM $TABLE_NAME"
        val result = db.rawQuery(query, null)

        if (result.moveToFirst()) {
            do {
                val chore = Chore()
                chore.choreName = result.getString(result.getColumnIndex(KEY_CHORE_NAME))
                chore.assignedTo = result.getString(result.getColumnIndex(KEY_CHORE_ASSIGNED_TO))
                chore.assignedBy = result.getString(result.getColumnIndex(KEY_CHORE_ASSIGNED_BY))
                chore.timeAssigned = result.getLong(result.getColumnIndex(KEY_CHORE_ASSIGNED_TIME))
                list.add(chore)
                println("***** Chore Name: ${list[0].choreName},\n***** AssignedBy: ${list[0].assignedBy},\n***** AssignedTo: ${list[0].assignedTo}, Time: ${list[0].timeAssigned}")
            } while (result.moveToNext())
        }
        return list

    }

/*
        val db: SQLiteDatabase = readableDatabase

        val cursor: Cursor = db.query(
                TABLE_NAME,
                arrayOf(KEY_ID, KEY_CHORE_NAME, KEY_CHORE_ASSIGNED_BY,
                        KEY_CHORE_ASSIGNED_TO, KEY_CHORE_ASSIGNED_TIME),
                "$KEY_ID=?",
                arrayOf(id.toString()),
                null,
                null,
                null)

        if (cursor != null)

        if(cursor.moveToFirst()) {
            val chore = Chore()
            chore.choreName = cursor.getString(cursor.getColumnIndex(KEY_CHORE_NAME))
            chore.assignedTo = cursor.getString(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_TO))
            chore.assignedBy = cursor.getString(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_BY))
            chore.timeAssigned = cursor.getLong(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_TIME))

            val dateFormat: DateFormat = DateFormat.getDateInstance()
            //Create a Date object
            val date = Date(cursor.getLong(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_TIME))).time
            var formatDate = dateFormat.format(date)
            list.add(chore)

        }
        else {
            println("Didn't enter")
        }

        return list
       */


    fun updateChore(chore: Chore): Int {
        var db: SQLiteDatabase = writableDatabase
        var values = ContentValues()

        //Putting a key-value into the Value Object
        values.put(KEY_CHORE_NAME, chore.choreName)
        values.put(KEY_CHORE_ASSIGNED_BY, chore.assignedBy)
        values.put(KEY_CHORE_ASSIGNED_TO, chore.assignedTo)
        values.put(KEY_CHORE_ASSIGNED_TIME, System.currentTimeMillis())

        //Update a row
        return db.update(TABLE_NAME, values, KEY_ID + "=?", arrayOf(chore.id.toString()))

    }


    fun deleteChore(chore: Chore) {

        var db: SQLiteDatabase = writableDatabase

        db.delete(TABLE_NAME, KEY_ID + "=?", arrayOf(chore.id.toString()))
        db.close()
    }
    fun getChoreCount(): Int {
        var db: SQLiteDatabase = readableDatabase
        var countQuery = "SELECT * FROM $TABLE_NAME"
        var cursor = db.rawQuery(countQuery, null)

        return cursor.count

    }
}