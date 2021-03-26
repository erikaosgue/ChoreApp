package com.erikaosgue.choresapp.activity

import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.erikaosgue.choresapp.data.ChoresDatabaseHandler
import com.erikaosgue.choresapp.databinding.ActivityMainBinding
import com.erikaosgue.choresapp.model.Chore

class MainActivity : AppCompatActivity() {

	// Create an var Of our type class ChoresDatabaseHandler

	lateinit var  activityMainBinding: ActivityMainBinding
 	override fun onCreate(savedInstanceState: Bundle?) {
 		super.onCreate(savedInstanceState)
 		activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
 		setContentView(activityMainBinding.root)

		// Create an instance of our Class ChoresDatabaseHandler()
		val dbHandler = ChoresDatabaseHandler(this)

		// Create an Instance of type Chore()
		val chore = Chore()
		chore.choreName = "Clean Room"
		chore.assignedTo = "James"
		chore.assignedBy = "Carlos"

		dbHandler.createChore(chore)


		//Read from database
		val list = dbHandler.readAChore(2)
		if (list.isNotEmpty()) {
			Log.d("Item", list.toString())
			println("Chore Name: ${list[0].choreName}, AssignedBy: ${list[0].assignedBy}, AssignedTo: ${list[0].assignedTo}, Time: ${list[0].timeAssigned}")
		}
    }
}