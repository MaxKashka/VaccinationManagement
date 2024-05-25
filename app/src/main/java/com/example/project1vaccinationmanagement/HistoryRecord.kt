package com.example.project1vaccinationmanagement

import java.io.Serializable

/**
 * Data class representing a history record of a vaccination.
 *
 * @property historyId The unique identifier for the history record.
 * @property userId The unique identifier of the user who received the vaccine.
 * @property vaccineName The name of the vaccine administered.
 * @property dateAdministered The date on which the vaccine was administered.
 * @property dateOfDose The date of the dose.
 */
data class HistoryRecord(
    val historyId: Int? = null,
    val userId: String,
    val vaccineName: String,
    val dateAdministered: String,
    val dateOfDose: String
) : Serializable

