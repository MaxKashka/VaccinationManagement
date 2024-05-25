package com.example.project1vaccinationmanagement

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.*

/**
 * Activity for user registration.
 */
class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var editTextEmail: EditText
    private lateinit var editTextName: EditText
    private lateinit var editTextSurname: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextRepeatPassword: EditText
    private lateinit var buttonSignUp: Button
    private lateinit var buttonChangeToLogin: Button

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, then this Bundle contains the data it most recently supplied in [onSaveInstanceState]. Otherwise, it is null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        initViews()

        buttonSignUp.setOnClickListener {
            attemptRegistration()
        }

        buttonChangeToLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    /**
     * Initializes the view components.
     */
    private fun initViews() {
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextName = findViewById(R.id.editTextName)
        editTextSurname = findViewById(R.id.editTextSurname)
        editTextPassword = findViewById(R.id.editTextPassword)
        editTextRepeatPassword = findViewById(R.id.editTextRepeatPassword)
        buttonSignUp = findViewById(R.id.buttonSignUp)
        buttonChangeToLogin = findViewById(R.id.buttonChangeToLogin)
    }

    /**
     * Attempts to register a new user by validating input and calling registration methods.
     */
    private fun attemptRegistration() {
        val email = editTextEmail.text.toString().trim()
        val name = editTextName.text.toString().trim()
        val surname = editTextSurname.text.toString().trim()
        val password = editTextPassword.text.toString().trim()
        val repeatPassword = editTextRepeatPassword.text.toString().trim()

        if (email.isEmpty() || name.isEmpty() || surname.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
            showToast("Please fill in all fields")
            return
        }

        if (password != repeatPassword) {
            showToast("Passwords do not match")
            return
        }

        if (validatePassword(password)) {
            registerUser(email, password, name, surname)
        } else {
            showToast("Password must be at least 5 characters long and contain special characters: !, @, #, $, %, ^, &, *")
        }
    }

    /**
     * Registers a new user with Firebase Authentication and saves the user to the database.
     *
     * @param email The user's email.
     * @param password The user's password.
     * @param name The user's name.
     * @param surname The user's surname.
     */
    private fun registerUser(email: String, password: String, name: String, surname: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val firebaseUid = auth.currentUser?.uid ?: return@addOnCompleteListener
                CoroutineScope(Dispatchers.IO).launch {
                    val result = addUserToDatabase(firebaseUid, name, email, password)
                    withContext(Dispatchers.Main) {
                        if (result) {
                            showToast("User registered successfully")
                            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                            finish()
                        } else {
                            showToast("Failed to save user to database")
                        }
                    }
                }
            } else {
                showToast("Registration failed: ${task.exception?.message}")
            }
        }
    }

    /**
     * Adds a new user to the database.
     *
     * @param userId The user's ID.
     * @param name The user's name.
     * @param email The user's email.
     * @param password The user's password.
     * @return True if the user was added successfully, false otherwise.
     */
    private suspend fun addUserToDatabase(userId: String, name: String, email: String, password: String): Boolean {
        val connection = DatabaseConnection.getConnection()
        val databaseHelper = DatabaseHelper(connection)
        val result = databaseHelper.addUser(userId, name, email, password)
        connection.close()
        return result
    }

    /**
     * Validates the user's password.
     *
     * @param password The user's password.
     * @return True if the password is valid, false otherwise.
     */
    private fun validatePassword(password: String): Boolean {
        val specialCharacters = setOf('!', '@', '#', '$', '%', '^', '&', '*')
        return password.length >= 5 && password.any { it in specialCharacters }
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




