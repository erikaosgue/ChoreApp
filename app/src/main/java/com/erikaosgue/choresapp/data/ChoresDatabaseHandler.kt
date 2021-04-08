package com.erikaosgue.choresapp.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import android.widget.Toast
import com.erikaosgue.choresapp.model.*
import kotlin.collections.ArrayList

object mDataBaseHandlerObject {
    fun create(context: Context) = ChoresDatabaseHandler(context)
}


class ChoresDatabaseHandler(private val context: Context):
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {


        val CREATE_CHORE_TABLE = "CREATE TABLE $TABLE_NAME (" +
                "$KEY_ID INTEGER PRIMARY KEY, " +
                "$KEY_CHORE_NAME TEXT, $KEY_CHORE_ASSIGNED_BY TEXT, " +
                "$KEY_CHORE_ASSIGNED_TO TEXT, $KEY_CHORE_ASSIGNED_TIME LONG)"

        db?.execSQL(CREATE_CHORE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        // Drop "Delete the old Table"
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")

        //create a New table "Again"
        onCreate(db)
    }


    /**
     * CRUD - Create, Read, Update, Delete
     **/
    fun createChore(chore: Chore) {

        val db: SQLiteDatabase = writableDatabase

        // values is an object that will store a set of values (hashMap)
        val values = ContentValues()


        //Putting a key-value into the Value Object
        values.put(KEY_CHORE_NAME, chore.choreName)
        values.put(KEY_CHORE_ASSIGNED_BY, chore.assignedBy)
        values.put(KEY_CHORE_ASSIGNED_TO, chore.assignedTo)
        values.put(KEY_CHORE_ASSIGNED_TIME, System.currentTimeMillis())

        // Inserting  to the db the Table name, column hack=null and the Values
        val result = db.insert(TABLE_NAME, null, values)

        //Check if the Data was correctly inserted
        //result will be the Id of the last row inserted,
        // if an error happened, it will be -1
        if (result == (-1).toLong()) {

            Toast.makeText(context, "Failed Data not Inserted", Toast.LENGTH_SHORT).show()
            Log.d("DATA NOT INSERTED", "FAIL")
        }
        else {

            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
            Log.d("DATA INSERTED", "SUCCESS")
        }
        db.close()
    }


    fun readChores(): ArrayList<Chore> {

        val db: SQLiteDatabase = readableDatabase
        val list: ArrayList<Chore> = ArrayList()

        val query = "SELECT * FROM $TABLE_NAME"
        //
        val cursor = db.rawQuery(query, null)

        // If cursor is not empty will return true and move to the first entry
        if (cursor.moveToFirst()) {
            do {
                val chore = Chore()

                chore.id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                chore.choreName = cursor.getString(cursor.getColumnIndex(KEY_CHORE_NAME))
                chore.assignedTo = cursor.getString(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_TO))
                chore.assignedBy = cursor.getString(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_BY))
                chore.timeAssigned = cursor.getLong(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_TIME))
                list.add(chore)
            } while (cursor.moveToNext())
        }
        return list

    }

    fun updateChore(chore: Chore): Int {
        val db: SQLiteDatabase = writableDatabase
        val values = ContentValues()

        //Putting a key-value into the Value Object
        values.put(KEY_CHORE_NAME, chore.choreName)
        values.put(KEY_CHORE_ASSIGNED_BY, chore.assignedBy)
        values.put(KEY_CHORE_ASSIGNED_TO, chore.assignedTo)
        values.put(KEY_CHORE_ASSIGNED_TIME, System.currentTimeMillis())

        //Update a row
        return db.update(TABLE_NAME, values, "$KEY_ID=?", arrayOf(chore.id.toString()))
    }


    fun deleteChore(id: Int) {

        val db: SQLiteDatabase = writableDatabase

        db.delete(TABLE_NAME, "$KEY_ID=?", arrayOf(id.toString()))
        db.close()
    }

    fun getChoreCount(): Int {
        val db: SQLiteDatabase = readableDatabase
        val countQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(countQuery, null)

        return cursor.count
    }
}