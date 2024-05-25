package com.example.project1vaccinationmanagement

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Adapter for displaying vaccination history records in a RecyclerView.
 *
 * @property items The list of vaccination history records to display.
 */
class VaccinationHistoryAdapter(private var items: List<HistoryRecord>) : RecyclerView.Adapter<VaccinationHistoryAdapter.ViewHolder>() {

    /**
     * ViewHolder class for the adapter.
     *
     * @property view The view for each item in the RecyclerView.
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewVaccineName: TextView = view.findViewById(R.id.textViewVaccineName)
        val textViewDateAdministered: TextView = view.findViewById(R.id.textViewDateAdministered)
        val textViewDateOfDose: TextView = view.findViewById(R.id.textViewDateOfDose)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_vaccination_record, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.textViewVaccineName.text = item.vaccineName
        holder.textViewDateAdministered.text = item.dateAdministered
        holder.textViewDateOfDose.text = item.dateOfDose
    }

    override fun getItemCount(): Int = items.size

    /**
     * Updates the data in the adapter.
     *
     * @param newItems The new list of vaccination history records.
     */
    fun updateData(newItems: List<HistoryRecord>) {
        items = newItems
        notifyDataSetChanged()
    }
}

