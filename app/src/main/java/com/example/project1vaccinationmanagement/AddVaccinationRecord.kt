package com.example.project1vaccinationmanagement

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

/**
 * Activity for adding or updating vaccination records.
 */
class AddVaccinationRecord : AppCompatActivity() {

    private lateinit var editTextVaccinationID: EditText
    private lateinit var editTextUserID: EditText
    private lateinit var editTextVaccineName: EditText
    private lateinit var editTextDateAdministered: EditText
    private lateinit var editTextNextDoseDate: EditText
    private lateinit var buttonSaveRecord: Button
    private lateinit var buttonDeleteRecord: Button
    private lateinit var buttonBackToMain: Button

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, then this Bundle contains the data it most recently supplied in [onSaveInstanceState]. Otherwise, it is null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_vaccination_record)

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
        editTextNextDoseDate = findViewById(R.id.editTextNextDoseDate)
        buttonSaveRecord = findViewById(R.id.buttonSaveRecord)
        buttonDeleteRecord = findViewById(R.id.buttonDeleteRecord)
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

        val nextDoseDatePicker = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            editTextNextDoseDate.setText(dateFormat.format(calendar.time))
        }

        editTextNextDoseDate.setOnClickListener {
            DatePickerDialog(
                this,
                nextDoseDatePicker,
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
        val nextDoseDate = editTextNextDoseDate.text.toString()

        if (vaccinationId == null) {
            showToast("Please enter a valid Vaccination ID")
            return
        }

        if (userId.isBlank() || vaccineName.isBlank() || dateAdministered.isBlank() || nextDoseDate.isBlank()) {
            showToast("Please fill all fields")
            return
        }

        val currentDate = LocalDate.now()
        val dateAdministeredParsed = LocalDate.parse(dateAdministered)
        val nextDoseDateParsed = LocalDate.parse(nextDoseDate)

        if (dateAdministeredParsed.isAfter(currentDate) || nextDoseDateParsed.isBefore(currentDate)) {
            showToast("Invalid dates: Date Administered should be today or before, and Next Dose Date should be today or later")
            return
        }

        withContext(Dispatchers.IO) {
            val connection = DatabaseConnection.getConnection()
            val dbHelper = DatabaseHelper(connection)
            val recordExists = dbHelper.checkIfVaccinationRecordExists(vaccinationId)
            if (recordExists) {
                dbHelper.updateVaccinationRecord(vaccinationId, vaccineName, dateAdministered, nextDoseDate)
                withContext(Dispatchers.Main) {
                    showToast("Record updated successfully")
                }
            } else {
                dbHelper.addVaccinationRecord(vaccinationId, userId, vaccineName, dateAdministered, nextDoseDate)
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
                dbHelper.deleteVaccinationRecord(vaccinationId)
                withContext(Dispatchers.Main) {
                    showToast("Record deleted!")
                }
                connection.close()
            }
        } else {
            showToast("Invalid Vaccination ID!")
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








