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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //Inflating the top_menu.xml into an object
        menuInflater.inflate(R.menu.top_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add_menu_button) {
            Log.d("Item Clicked", "Item clicked")

            createPopupDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    fun createPopupDialog(){

        var view = layoutInflater.inflate(R.layout.popup, null)
        var choreName = view.findViewById<TextView>(R.id.popEnterChoreId)
        var assignedBy = view.findViewById<TextView>(R.id.popEnterAssignedById)
        var assignedTo = view.findViewById<TextView>(R.id.popEnterAssignToId)
        var saveButton  = view.findViewById<Button>(R.id.popSaveChoreButton)
        Log.d("Here yes", "Success")
        println("******* ${choreName.text}, ${assignedBy.text}, ${assignedTo.text} ******")

        dialogBuilder = AlertDialog.Builder(this).setView(view)
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
                finish()

            }
            else {
                Toast.makeText(this, "Enter a chore, assigned to and Assigned by should not be empty", Toast.LENGTH_LONG).show()
            }

        }
    }

}