package com.example.project1vaccinationmanagement

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

/**
 * Helper class to manage notifications for vaccination reminders.
 *
 * @property context The context in which the notifications are created.
 */
class NotificationHelper(private val context: Context) {

    /**
     * Creates a notification channel for vaccination reminders.
     * This is required for Android O (API level 26) and above.
     */
    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Vaccination Reminders",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Vaccination Reminder"
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    /**
     * Schedules a notification for a vaccination reminder.
     *
     * @param vaccineName The name of the vaccine.
     * @param nextDoseDate The date and time for the next dose in milliseconds since epoch.
     */
    fun scheduleNotification(vaccineName: String, nextDoseDate: Long) {
        val intent = Intent(context, ReminderBroadcastReceiver::class.java).apply {
            putExtra("vaccineName", vaccineName)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            nextDoseDate,
            pendingIntent
        )
    }

    companion object {
        const val CHANNEL_ID = "VACCINATION_REMINDER_CHANNEL"
        const val REQUEST_CODE = 101
    }
}


