package com.example.project1vaccinationmanagement

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Activity for displaying the vaccination history of the logged-in user.
 */
class VaccinationHistoryActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: VaccinationHistoryAdapter
    private lateinit var buttonBackToMain: Button

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, then this Bundle contains the data it most recently supplied in [onSaveInstanceState]. Otherwise, it is null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vaccination_history)

        recyclerView = findViewById(R.id.recyclerViewVaccinationHistory)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = VaccinationHistoryAdapter(emptyList())
        recyclerView.adapter = adapter
        buttonBackToMain = findViewById(R.id.buttonBackToMain)

        buttonBackToMain.setOnClickListener { finish() }

        val historyRecords = intent.getSerializableExtra("historyRecords") as? List<HistoryRecord>
        if (historyRecords != null) {
            adapter.updateData(historyRecords)
        } else {
            loadData() // Fall back to loading data if none is passed
        }
    }

    /**
     * Loads the vaccination history from the database for the current user.
     */
    private fun loadData() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val userId = currentUser?.uid

        if (userId == null) {
            Toast.makeText(this, "No user logged in", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            try {
                val connection = DatabaseConnection.getConnection()
                val dbHelper = DatabaseHelper(connection)
                val historyRecords = withContext(Dispatchers.IO) {
                    dbHelper.getAllHistoryRecords(userId)
                }
                adapter.updateData(historyRecords)
                connection.close()
            } catch (e: Exception) {
                println("Failed to load data: ${e.message}")
                Toast.makeText(this@VaccinationHistoryActivity, "Error loading history: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}


