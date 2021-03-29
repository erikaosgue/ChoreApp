package com.erikaosgue.choresapp.data

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.erikaosgue.choresapp.R
import com.erikaosgue.choresapp.model.Chore

class ChoreListAdapter(private var listOfChores: ArrayList<Chore>, private val context: Context): RecyclerView.Adapter<ChoreListAdapter.ChoreViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
    : ChoreListAdapter.ChoreViewHolder {

        val viewObject = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.list_row, parent, false)

        return ChoreViewHolder(viewObject, context)
    }

    override fun onBindViewHolder(holder: ChoreListAdapter.ChoreViewHolder, position: Int) {
        holder.render(listOfChores[position])
    }

    override fun getItemCount(): Int {
        return listOfChores.size
    }



    inner class ChoreViewHolder(view: View, context: Context): RecyclerView.ViewHolder(view), View.OnClickListener  {

        // Get the different  views from the list_row view
        var myContext = context
        private val choreName =  view.findViewById<TextView>(R.id.listChoreName)
        private val assignedBy = view.findViewById<TextView>(R.id.listAssignedBy)
        private val assignedTo = view.findViewById<TextView>(R.id.listAssignedTo)
        private val timeAssigned = view.findViewById<TextView>(R.id.listDate)

        // Add the buttons Edit and Delete
        private var editButton = view.findViewById<Button>(R.id.listEditButton)
        private var deleteButton = view.findViewById<Button>(R.id.listDeleteButton)


        /** Render sets the data form the list (From database) to the viewHolder
         * Chore is the object from the list that comes from the database
         */
        fun render(chore: Chore){

            //Pass the data to the view from the list that comes from the database
            choreName.text = "Chore: ${chore.choreName}"
            assignedBy.text = "Assigned By: ${chore.assignedBy}"
            assignedTo.text = "Assigned To: ${chore.assignedTo}"
            timeAssigned.text = chore.showHumanDate(chore.timeAssigned!!)


            editButton.setOnClickListener(this)
            deleteButton.setOnClickListener(this)

        }

        override fun onClick(view: View?) {

            val myPosition: Int = adapterPosition
            val chore = listOfChores[myPosition]


            when (view?.id) {
                deleteButton.id -> {
                    deleteChore(chore.id!!)
                    listOfChores.removeAt(myPosition)
                    notifyItemRemoved(myPosition)
                }

                editButton.id -> {
                    editChore(chore)

                }
            }
        }
        private fun deleteChore(id: Int){

            val db = ChoresDatabaseHandler(myContext)
            db.deleteChore(id)
        }

        private fun editChore(chore: Chore){

            val dialog: AlertDialog?
            val dialogBuilder: AlertDialog.Builder?
            val dbHandler = ChoresDatabaseHandler(context)

            val view = LayoutInflater.from(context).inflate(R.layout.popup, null)
            val choreName = view.findViewById<TextView>(R.id.popEnterChoreId)
            val assignedBy = view.findViewById<TextView>(R.id.popEnterAssignedById)
            val assignedTo = view.findViewById<TextView>(R.id.popEnterAssignToId)
            val saveButton  = view.findViewById<Button>(R.id.popSaveChoreButton)


            dialogBuilder = AlertDialog.Builder(context).setView(view)
            dialog = dialogBuilder?.create()
            dialog?.show()

            saveButton.setOnClickListener {

                val name = choreName.text.toString().trim()
                val assignBY = assignedBy.text.toString().trim()
                val assignTO = assignedTo.text.toString().trim()


                //if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(assignBY) && !TextUtils.isEmpty(assignTO)) {
                if (name.isNotBlank() && assignBY.isNotBlank() && assignTO.isNotBlank()) {

                    chore.choreName = name
                    chore.assignedTo = assignTO
                    chore.assignedBy = assignBY

                    dbHandler.updateChore(chore)
                    notifyItemChanged(adapterPosition, chore)

                    dialog!!.dismiss()

                }
                else {
                    Toast.makeText(
                        context, "Chore name, assigned to and Assgined by should not be empty",
                        Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }
}