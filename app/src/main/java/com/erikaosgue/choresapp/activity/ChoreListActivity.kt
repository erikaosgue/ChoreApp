package com.erikaosgue.choresapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.erikaosgue.choresapp.R
import com.erikaosgue.choresapp.data.ChoreListAdapter
import com.erikaosgue.choresapp.data.ChoresDatabaseHandler
import com.erikaosgue.choresapp.databinding.ActivityChoreListBinding
import com.erikaosgue.choresapp.model.Chore

class ChoreListActivity : AppCompatActivity() {

    private var dbHandler: ChoresDatabaseHandler? = null
    private var recyclerView: RecyclerView? = null
    private var dialog: AlertDialog? = null
    private  var dialogBuilder: AlertDialog.Builder? = null



    lateinit var activityChoreListBinding: ActivityChoreListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityChoreListBinding = ActivityChoreListBinding.inflate(layoutInflater)
        setContentView(activityChoreListBinding.root)


        dbHandler = ChoresDatabaseHandler(this)

        // Get the Recycler View
        recyclerView =  activityChoreListBinding.recyclerViewId
        recyclerView?.layoutManager = LinearLayoutManager(this)

        // Create the Adapter
        //Load the chores into the List
        val choreList: ArrayList<Chore>? = dbHandler?.readChores()
        choreList?.reverse()
        val adapter = ChoreListAdapter(choreList!!, this)
        recyclerView?.adapter = adapter

    }

    // This method is call everytime the ChoreListActivity runs
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //Inflating the top_menu.xml into an object
        menuInflater.inflate(R.menu.top_menu, menu)
        Log.d("onCreateOptionsMenu", "createOptionsMenu")
        return true
    }

    // This method is call only when the user clicks the button plus sign
    // to add a new chore
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add_menu_button) {
            Log.d("onOptionsItemSelected", "Item clicked")

            createPopupDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    // This method is call from the method onOptionsItemSelected
    private fun createPopupDialog(){

        val popupview = layoutInflater.inflate(R.layout.popup, null)
        val choreName = popupview.findViewById<TextView>(R.id.popEnterChoreId)
        val assignedBy = popupview.findViewById<TextView>(R.id.popEnterAssignedById)
        val assignedTo = popupview.findViewById<TextView>(R.id.popEnterAssignToId)
        val saveButton  = popupview.findViewById<Button>(R.id.popSaveChoreButton)

        dialogBuilder = AlertDialog.Builder(this).setView(popupview)
        dialog = dialogBuilder?.create()
        dialog?.show()

        saveButton.setOnClickListener {

            val name = choreName.text.toString().trim()
            val assignBY = assignedBy.text.toString().trim()
            val assignTO = assignedTo.text.toString().trim()


            //if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(assignBY) && !TextUtils.isEmpty(assignTO)) {
            if (name.isNotBlank() && assignBY.isNotBlank() && assignTO.isNotBlank()) {

                val chore = Chore()

                chore.choreName = name
                chore.assignedTo = assignTO
                chore.assignedBy = assignBY

                dbHandler!!.createChore(chore)

                dialog!!.dismiss()

                startActivity(Intent(this, ChoreListActivity::class.java))
                // Close the current Activity to avoid Stacking multiples activities
                finish()

            }
            else {
                Toast.makeText(this, "Enter a chore, assigned to and Assigned by should not be empty", Toast.LENGTH_LONG).show()
            }

        }
    }

}