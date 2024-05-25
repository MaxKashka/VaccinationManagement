package com.example.project1vaccinationmanagement

/**
 * Represents a vaccination record in the system.
 *
 * @property vaccinationId The unique identifier for the vaccination record.
 * @property userId The unique identifier of the user associated with the vaccination record.
 * @property vaccineName The name of the vaccine administered.
 * @property dateAdministered The date on which the vaccine was administered.
 * @property nextDoseDate The date for the next scheduled dose.
 */
data class VaccinationRecord(
    val vaccinationId: Int? = null,
    val userId: String,
    val vaccineName: String,
    val dateAdministered: String,
    val nextDoseDate: String
)

