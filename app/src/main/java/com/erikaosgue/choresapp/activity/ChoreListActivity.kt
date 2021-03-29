package com.erikaosgue.choresapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
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
        val list: ArrayList<Chore>? = dbHandler?.readChores()
        val adapter = list?.let { ChoreListAdapter(it) }
        recyclerView?.adapter = adapter

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //Inflating the top_menu.xml into an object
        menuInflater.inflate(R.menu.top_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item?.itemId == R.id.add_menu_button)
            Log.d("Item Clicked", "Item clicked")
        return super.onOptionsItemSelected(item)
    }

}