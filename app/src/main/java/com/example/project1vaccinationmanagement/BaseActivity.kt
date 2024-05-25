package com.example.project1vaccinationmanagement

import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import androidx.core.content.ContextCompat

/**
 * A base activity class to provide common functionality across all activities.
 *
 * This class extends [AppCompatActivity] and provides a method to display feedback messages to the user via a [Snackbar].
 */
open class BaseActivity : AppCompatActivity() {

    /**
     * Shows a feedback message in a snackbar on the current screen.
     *
     * @param message The message to be displayed.
     * @param isError A Boolean indicating whether the message is an error message. This affects the color of the snackbar.
     */
    fun showFeedbackMessage(message: String, isError: Boolean) {
        val snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT)
        val snackbarColor = if (isError) {
            ContextCompat.getColor(this, R.color.black)
        } else {
            ContextCompat.getColor(this, R.color.purple_700)
        }
        snackbar.view.setBackgroundColor(snackbarColor)
        snackbar.show()
    }
}
