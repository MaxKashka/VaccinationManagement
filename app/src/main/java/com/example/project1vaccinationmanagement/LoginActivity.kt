package com.example.project1vaccinationmanagement

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.firebase.auth.FirebaseAuth

/**
 * Activity for user login functionality.
 */
class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var buttonChangeToRegister: Button
    private lateinit var progressIndicator: CircularProgressIndicator

    /**
     * Called when the activity is first created. Initializes the UI components and sets up click listeners.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied. Otherwise, it is null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)
        buttonChangeToRegister = findViewById(R.id.buttonChangeToRegister)
        progressIndicator = findViewById(R.id.progressIndicator)  // Initialize progress indicator

        buttonLogin.setOnClickListener {
            loginUser(editTextEmail.text.toString().trim(), editTextPassword.text.toString().trim())
        }

        buttonChangeToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    /**
     * Attempts to log in the user with the provided email and password.
     *
     * @param email The user's email address.
     * @param password The user's password.
     */
    private fun loginUser(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            showToast("Please enter both email and password")
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showToast("Please enter a valid email address")
            return
        }

        showProgress(true)
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            showProgress(false)
            if (task.isSuccessful) {
                startActivity(Intent(this, MainPage::class.java))
                finish()
            } else {
                showToast("Login failed: ${task.exception?.localizedMessage}")
            }
        }
    }

    /**
     * Shows or hides the progress indicator.
     *
     * @param show A boolean indicating whether to show (true) or hide (false) the progress indicator.
     */
    private fun showProgress(show: Boolean) {
        if (show) {
            progressIndicator.show()
        } else {
            progressIndicator.hide()
        }
    }

    /**
     * Displays a toast message to the user.
     *
     * @param message The message to be displayed.
     */
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

