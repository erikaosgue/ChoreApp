package com.erikaosgue.choresapp.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.erikaosgue.choresapp.R
import com.erikaosgue.choresapp.model.Chore

class ChoreListAdapter(private var listOfChores: ArrayList<Chore>): RecyclerView.Adapter<ChoreListAdapter.ChoreViewHolder>() {

    class ChoreViewHolder(var view: View): RecyclerView.ViewHolder(view) {

        /** Render sets the data form the list (From database) to the viewHolder
          * Chore is the object from the list that comes from the database
        */
        fun render(chore: Chore){

            // Get the different  views from the list_row view
            val choreName =  view.findViewById<TextView>(R.id.listChoreName)
            val assignedBy = view.findViewById<TextView>(R.id.listAssignedBy)
            val assignedTo = view.findViewById<TextView>(R.id.listAssignedTo)
            val timeAssigned = view.findViewById<TextView>(R.id.listDate)
//            var imageView = view.findViewById<ImageView>(R.id.imageViewID)

            //Pass the data to the view from the list that comes from the database
            choreName.text = chore.choreName
            assignedBy.text = chore.assignedBy
            assignedTo.text = chore.assignedTo
            timeAssigned.text = chore.showHumanDate(chore.timeAssigned!!)
//            imageView.setImageDrawable(chore.)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChoreListAdapter.ChoreViewHolder {
        val viewObject = LayoutInflater.from(parent.context).inflate(R.layout.list_row, parent, false)
        return ChoreViewHolder(viewObject)
    }

    override fun onBindViewHolder(holder: ChoreListAdapter.ChoreViewHolder, position: Int) {
        holder.render(listOfChores[position])
    }

    override fun getItemCount(): Int {
        return listOfChores.size
    }
}