package com.example.project1vaccinationmanagement

/**
 * Represents a schedule for a vaccination.
 *
 * @property scheduleId The unique identifier for the schedule.
 * @property vaccinationId The ID of the vaccination associated with the schedule.
 * @property scheduledDate The date on which the vaccine is scheduled to be administered.
 */
data class Schedule(
    val scheduleId: Int,
    val vaccinationId: Int,
    val scheduledDate: String
)
