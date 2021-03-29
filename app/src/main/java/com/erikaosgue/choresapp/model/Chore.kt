package com.erikaosgue.choresapp.model

import java.text.DateFormat
import java.util.*

class Chore() {
    var choreName: String? = null
    var assignedBy: String? = null
    var assignedTo: String? = null
    var timeAssigned: Long? = null
    var id: Int? = null


    fun showHumanDate(timeAssigned: Long): String {

        val dateFormat: DateFormat = DateFormat.getDateInstance()
        val formattedDate: String = dateFormat.format(Date(timeAssigned).time)

        return "Created:\n$formattedDate"

    }

    override fun toString(): String {
        return "Chore(choreName=$choreName, assignedBy=$assignedBy, assignedTo=$assignedTo, timeAssigned=$timeAssigned, id=$id)"
    }
}