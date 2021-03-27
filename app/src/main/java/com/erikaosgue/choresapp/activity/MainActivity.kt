package com.erikaosgue.choresapp.activity

import android.animation.ObjectAnimator
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.erikaosgue.choresapp.data.ChoresDatabaseHandler
import com.erikaosgue.choresapp.databinding.ActivityMainBinding
import com.erikaosgue.choresapp.model.Chore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

	// Create an var Of our type class ChoresDatabaseHandler
	var dbHandler: ChoresDatabaseHandler? = null

	lateinit var  activityMainBinding: ActivityMainBinding
 	override fun onCreate(savedInstanceState: Bundle?) {
 		super.onCreate(savedInstanceState)
 		activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
 		setContentView(activityMainBinding.root)


		// Create an instance of our Class ChoresDatabaseHandler()
		dbHandler = ChoresDatabaseHandler(this)

		saveChore.setOnClickListener {

			progressBarId.visibility = View.VISIBLE

			if(!TextUtils.isEmpty(enterChoreId.text.toString())
					&& !TextUtils.isEmpty(assignedById.text.toString())
					&& !TextUtils.isEmpty(assignToId.text.toString())) {
				Toast.makeText(this, "Please enter a chore", Toast.LENGTH_SHORT).show()

				// Save to dataBase

				var chore = Chore()
				chore.choreName = enterChoreId.text.toString()
				chore.assignedTo = assignedById.text.toString()
				chore.assignedBy = assignToId.text.toString()

				saveToDB(chore)
				Toast.makeText(this, "Getting to a new activity", Toast.LENGTH_LONG).show()
				progressBarId.visibility = View.GONE
				startActivity(Intent(this, ChoreListActivity::class.java))

			}else {
				Toast.makeText(this, "Please enter a chore", Toast.LENGTH_SHORT).show()
			}

		}






/*
		 Create an Instance of type Chore()
		val chore = Chore()
		chore.choreName = "Clean Room"
		chore.assignedTo = "James"
		chore.assignedBy = "Carlos"

		dbHandler?.createChore(chore)

		var chores: ArrayList<Chore> = dbHandler?.readChores()

		//Read from database
		val list = dbHandler.readChores()
		if (list.isNotEmpty()) {
			Log.d("Item", list.toString())
			println("Chore Name: ${list[0].choreName}, AssignedBy: ${list[0].assignedBy}, AssignedTo: ${list[0].assignedTo}, Time: ${list[0].timeAssigned}")
		}

*/
    }
	fun saveToDB(chore: Chore) {
		dbHandler?.createChore(chore)
	}

}