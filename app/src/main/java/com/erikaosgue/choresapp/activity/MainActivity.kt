package com.erikaosgue.choresapp.activity

import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.erikaosgue.choresapp.data.ChoresDatabaseHandler
import com.erikaosgue.choresapp.databinding.ActivityMainBinding
import com.erikaosgue.choresapp.model.Chore

class MainActivity : AppCompatActivity() {

	var dbHandler: ChoresDatabaseHandler?= null

	lateinit var  activityMainBinding: ActivityMainBinding
 	override fun onCreate(savedInstanceState: Bundle?) {
 		super.onCreate(savedInstanceState)
 		activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
 		setContentView(activityMainBinding.root)

		dbHandler = ChoresDatabaseHandler(this)

		val chore = Chore()
		chore.choreName
		chore.assignedTo
		chore.assignedBy
		chore.timeAssigned

		dbHandler?.createChore(chore)
    }

}