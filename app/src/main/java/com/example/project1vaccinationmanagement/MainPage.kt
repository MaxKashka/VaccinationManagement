package com.example.project1vaccinationmanagement

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

/**
 * Main page activity that provides navigation options to various features of the application.
 */
class MainPage : AppCompatActivity() {
    private lateinit var buttonAddRecord: Button
    private lateinit var buttonViewHistory: Button
    private lateinit var buttonScheduleVaccinations: Button
    private lateinit var buttonLogout: Button
    private lateinit var buttonExit: Button

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, then this Bundle contains the data it most recently supplied in [onSaveInstanceState]. Otherwise, it is null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)
        initViews()
        setupButtonListeners()
    }

    /**
     * Initializes the view components.
     */
    private fun initViews() {
        buttonAddRecord = findViewById(R.id.buttonAddRecord)
        buttonViewHistory = findViewById(R.id.buttonViewHistory)
        buttonScheduleVaccinations = findViewById(R.id.buttonScheduleVaccinations)
        buttonLogout = findViewById(R.id.buttonLogout)
        buttonExit = findViewById(R.id.buttonExit)
    }

    /**
     * Sets up listeners for the buttons to handle user interactions.
     */
    private fun setupButtonListeners() {
        buttonAddRecord.setOnClickListener {
            startActivity(Intent(this, AddVaccinationRecord::class.java))
        }
        buttonViewHistory.setOnClickListener {
            startActivity(Intent(this, AddHistoryVaccinationActivity::class.java))
        }
        buttonScheduleVaccinations.setOnClickListener {
            startActivity(Intent(this, ScheduleVaccinations::class.java))
        }
        buttonLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        buttonExit.setOnClickListener {
            finishAffinity()
        }
    }
}




