package com.example.project1vaccinationmanagement

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.*

/**
 * Activity for scheduling and viewing upcoming vaccination appointments.
 */
class ScheduleVaccinations : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UpcomingVaccinationAdapter
    private lateinit var buttonBackToMain: Button

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, then this Bundle contains the data it most recently supplied in [onSaveInstanceState]. Otherwise, it is null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_vaccinations)

        recyclerView = findViewById(R.id.recyclerViewUpcomingVaccinations)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = UpcomingVaccinationAdapter(emptyList())
        recyclerView.adapter = adapter

        buttonBackToMain = findViewById(R.id.buttonBackToMainFromSchedule)
        buttonBackToMain.setOnClickListener { finish() }

        Log.d("ScheduleVaccinations", "onCreate: Initialized views")

        loadData()
    }

    /**
     * Loads the vaccination schedules from the database for the current user.
     */
    private fun loadData() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val userId = currentUser?.uid

        if (userId == null) {
            showToast("Please log in to view your schedules")
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            try {
                Log.d("ScheduleVaccinations", "loadData: Attempting to connect to database")
                val connection = DatabaseConnection.getConnection()
                if (connection == null) {
                    Log.e("ScheduleVaccinations", "loadData: Failed to connect to database")
                    showToast("Failed to connect to database")
                    return@launch
                }
                val dbHelper = DatabaseHelper(connection)
                val schedules = withContext(Dispatchers.IO) {
                    dbHelper.getUserSchedules(userId)
                }
                Log.d("ScheduleVaccinations", "loadData: Retrieved schedules from database")
                adapter.updateData(schedules)
                connection.close()
            } catch (e: Exception) {
                Log.e("ScheduleVaccinations", "loadData: Exception ${e.message}", e)
                showToast("Failed to load schedules: ${e.message}")
            }
        }
    }

    /**
     * Displays a toast message.
     *
     * @param message The message to display.
     */
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    /**
     * Adapter class for the RecyclerView displaying upcoming vaccination appointments.
     *
     * @property items The list of scheduled vaccination appointments.
     */
    inner class UpcomingVaccinationAdapter(private var items: List<Schedule>) : RecyclerView.Adapter<UpcomingVaccinationAdapter.ViewHolder>() {

        /**
         * ViewHolder class for the adapter.
         *
         * @property view The view for each item in the RecyclerView.
         */
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val textViewVaccineName: TextView = view.findViewById(R.id.textViewVaccineName)
            val textViewDateAdministered: TextView = view.findViewById(R.id.textViewDateAdministered)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_upcoming_vaccination, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = items[position]
            holder.textViewVaccineName.text = "Vaccination ID: ${item.vaccinationId}"
            holder.textViewDateAdministered.text = "Scheduled Date: ${item.scheduledDate}"
        }

        override fun getItemCount(): Int = items.size

        /**
         * Updates the data in the adapter.
         *
         * @param newItems The new list of scheduled vaccination appointments.
         */
        fun updateData(newItems: List<Schedule>) {
            items = newItems
            notifyDataSetChanged()
        }
    }
}




