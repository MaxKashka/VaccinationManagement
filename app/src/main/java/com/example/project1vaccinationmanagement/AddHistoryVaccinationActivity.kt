package com.example.project1vaccinationmanagement

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

/**
 * Activity for adding or updating historical vaccination records.
 */
class AddHistoryVaccinationActivity : AppCompatActivity() {

    private lateinit var editTextVaccinationID: EditText
    private lateinit var editTextUserID: EditText
    private lateinit var editTextVaccineName: EditText
    private lateinit var editTextDateAdministered: EditText
    private lateinit var editTextDateOfDose: EditText
    private lateinit var buttonSaveRecord: Button
    private lateinit var buttonDeleteRecord: Button
    private lateinit var buttonBackToMain: Button
    private lateinit var buttonGetAllHistory: Button

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, then this Bundle contains the data it most recently supplied in [onSaveInstanceState]. Otherwise, it is null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_history_vaccination)

        initViews()
        setupDatePickers()

        buttonSaveRecord.setOnClickListener {
            lifecycleScope.launch {
                saveOrUpdateRecord()
            }
        }
        buttonDeleteRecord.setOnClickListener {
            lifecycleScope.launch {
                deleteRecord()
            }
        }
        buttonGetAllHistory.setOnClickListener {
            getAllHistoryRecords()
        }
        buttonBackToMain.setOnClickListener { finish() }
    }

    /**
     * Initializes the view components.
     */
    private fun initViews() {
        editTextVaccinationID = findViewById(R.id.editTextVaccinationID)
        editTextUserID = findViewById(R.id.editTextUserID)
        editTextVaccineName = findViewById(R.id.editTextVaccineName)
        editTextDateAdministered = findViewById(R.id.editTextDateAdministered)
        editTextDateOfDose = findViewById(R.id.editTextDateOfDose)
        buttonSaveRecord = findViewById(R.id.buttonSaveRecord)
        buttonDeleteRecord = findViewById(R.id.buttonDeleteRecord)
        buttonGetAllHistory = findViewById(R.id.buttonGetAllHistory)
        buttonBackToMain = findViewById(R.id.buttonBackToMain)
    }

    /**
     * Sets up date pickers for date fields.
     */
    private fun setupDatePickers() {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val dateAdministeredPicker = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            editTextDateAdministered.setText(dateFormat.format(calendar.time))
        }

        editTextDateAdministered.setOnClickListener {
            DatePickerDialog(
                this,
                dateAdministeredPicker,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        val dateOfDosePicker = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            editTextDateOfDose.setText(dateFormat.format(calendar.time))
        }

        editTextDateOfDose.setOnClickListener {
            DatePickerDialog(
                this,
                dateOfDosePicker,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    /**
     * Saves or updates a vaccination record.
     */
    private suspend fun saveOrUpdateRecord() {
        val vaccinationId = editTextVaccinationID.text.toString().toIntOrNull()
        val userId = editTextUserID.text.toString()
        val vaccineName = editTextVaccineName.text.toString()
        val dateAdministered = editTextDateAdministered.text.toString()
        val dateOfDose = editTextDateOfDose.text.toString()

        if (vaccinationId == null) {
            showToast("Please enter a valid Vaccination ID")
            return
        }

        if (userId.isBlank() || vaccineName.isBlank() || dateAdministered.isBlank() || dateOfDose.isBlank()) {
            showToast("Please fill all fields")
            return
        }

        withContext(Dispatchers.IO) {
            val connection = DatabaseConnection.getConnection()
            val dbHelper = DatabaseHelper(connection)
            val recordExists = dbHelper.checkIfHistoryRecordExists(vaccinationId)
            if (recordExists) {
                dbHelper.updateHistoryRecord(vaccinationId, userId, vaccineName, dateAdministered, dateOfDose)
                withContext(Dispatchers.Main) {
                    showToast("Record updated successfully")
                }
            } else {
                dbHelper.addHistoryRecord(vaccinationId, userId, vaccineName, dateAdministered, dateOfDose)
                withContext(Dispatchers.Main) {
                    showToast("Record added successfully")
                }
            }
            connection.close()
        }
    }

    /**
     * Deletes a vaccination record.
     */
    private suspend fun deleteRecord() {
        val vaccinationId = editTextVaccinationID.text.toString().toIntOrNull()
        if (vaccinationId != null) {
            withContext(Dispatchers.IO) {
                val connection = DatabaseConnection.getConnection()
                val dbHelper = DatabaseHelper(connection)
                dbHelper.deleteHistoryRecord(vaccinationId)
                withContext(Dispatchers.Main) {
                    showToast("Record deleted!")
                }
                connection.close()
            }
        } else {
            showToast("Invalid History ID!")
        }
    }

    /**
     * Retrieves all history records for the current user and displays them.
     */
    private fun getAllHistoryRecords() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val userId = currentUser?.uid

        if (userId.isNullOrEmpty()) {
            showToast("Please log in to view your history records")
            return
        }

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val connection = DatabaseConnection.getConnection()
                val dbHelper = DatabaseHelper(connection)
                val historyRecords = dbHelper.getAllHistoryRecords(userId)
                connection.close()
                withContext(Dispatchers.Main) {
                    val intent = Intent(this@AddHistoryVaccinationActivity, VaccinationHistoryActivity::class.java).apply {
                        putExtra("historyRecords", ArrayList(historyRecords))
                    }
                    startActivity(intent)
                }
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
}









