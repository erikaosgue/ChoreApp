package com.erikaosgue.choresapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.erikaosgue.choresapp.data.ChoreListAdapter
import com.erikaosgue.choresapp.data.ChoresDatabaseHandler
import com.erikaosgue.choresapp.databinding.ActivityChoreListBinding
import com.erikaosgue.choresapp.databinding.ActivityMainBinding
import com.erikaosgue.choresapp.model.Chore
//import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

	// Create an var Of our type class ChoresDatabaseHandler
	private var dbHandler: ChoresDatabaseHandler? = null


	lateinit var  activityMainBinding: ActivityMainBinding

	//Setting the Layout activity_chore_list

 	override fun onCreate(savedInstanceState: Bundle?) {
 		super.onCreate(savedInstanceState)
 		activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
 		setContentView(activityMainBinding.root)


		// Create an instance of our Class ChoresDatabaseHandler()
		dbHandler = ChoresDatabaseHandler(this)

		/*
		 Check if there is data in the editText  and add that data
		 when clicking in the Button saveChore
		 */
		activityMainBinding.saveChore.setOnClickListener {

			// Make visible the progress bar
			activityMainBinding.progressBarId.visibility = View.VISIBLE

			// Check if EditText field are not empty
			if(!TextUtils.isEmpty(activityMainBinding.enterChoreId.text.toString())
					&& !TextUtils.isEmpty(activityMainBinding.assignedById.text.toString())
					&& !TextUtils.isEmpty(activityMainBinding.assignToId.text.toString())) {
				Toast.makeText(this, "Success... entering data", Toast.LENGTH_SHORT).show()

				// Create a chore Object and save it into the dataBase
				val chore = Chore()
				chore.choreName = activityMainBinding.enterChoreId.text.toString()
				chore.assignedTo = activityMainBinding.assignedById.text.toString()
				chore.assignedBy = activityMainBinding.assignToId.text.toString()
				saveToDB(chore)


				Toast.makeText(this, "Getting to a new activity", Toast.LENGTH_LONG).show()
						activityMainBinding.progressBarId.visibility = View.GONE
				startActivity(Intent(this, ChoreListActivity::class.java))

			}else {
				Toast.makeText(this, "Empty fields not allowed", Toast.LENGTH_SHORT).show()
			}

		}

    }
	private fun saveToDB(chore: Chore) {
		dbHandler?.createChore(chore)
	}


}