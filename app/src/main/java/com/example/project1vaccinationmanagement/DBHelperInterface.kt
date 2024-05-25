package com.example.project1vaccinationmanagement

/**
 * Interface defining the database helper functions.
 */
interface DBHelperInterface {
    /**
     * Adds a new user to the database.
     *
     * @param userId The user's ID.
     * @param name The user's name.
     * @param email The user's email.
     * @param password The user's password.
     * @return True if the user was added successfully, false otherwise.
     */
    suspend fun addUser(userId: String, name: String, email: String, password: String): Boolean

    /**
     * Updates user data in the database.
     *
     * @param userId The user's ID.
     * @param name The user's name.
     * @param email The user's email.
     * @param password The user's password.
     * @return True if the user data was updated successfully, false otherwise.
     */
    suspend fun updateUserData(userId: String, name: String, email: String, password: String): Boolean

    /**
     * Deletes a user from the database.
     *
     * @param userId The user's ID.
     * @return True if the user was deleted successfully, false otherwise.
     */
    suspend fun deleteUser(userId: String): Boolean

    /**
     * Adds a new vaccination record to the database.
     *
     * @param vaccinationId The vaccination ID.
     * @param userId The user's ID.
     * @param vaccineName The name of the vaccine.
     * @param dateAdministered The date the vaccine was administered.
     * @param nextDoseDate The date for the next dose.
     * @return True if the record was added successfully, false otherwise.
     */
    suspend fun addVaccinationRecord(vaccinationId: Int, userId: String, vaccineName: String, dateAdministered: String, nextDoseDate: String): Boolean

    /**
     * Updates an existing vaccination record in the database.
     *
     * @param vaccinationId The vaccination ID.
     * @param vaccineName The name of the vaccine.
     * @param dateAdministered The date the vaccine was administered.
     * @param nextDoseDate The date for the next dose.
     * @return True if the record was updated successfully, false otherwise.
     */
    suspend fun updateVaccinationRecord(vaccinationId: Int, vaccineName: String, dateAdministered: String, nextDoseDate: String): Boolean

    /**
     * Deletes a vaccination record from the database.
     *
     * @param vaccinationId The vaccination ID.
     * @return True if the record was deleted successfully, false otherwise.
     */
    suspend fun deleteVaccinationRecord(vaccinationId: Int): Boolean

    /**
     * Retrieves all history records for a specific user.
     *
     * @param userId The user's ID.
     * @return A list of [HistoryRecord] objects.
     */
    suspend fun getAllHistoryRecords(userId: String): List<HistoryRecord>

    /**
     * Adds a new history record to the database.
     *
     * @param historyId The history record ID.
     * @param userId The user's ID.
     * @param vaccineName The name of the vaccine.
     * @param dateAdministered The date the vaccine was administered.
     * @param dateOfDose The date of the dose.
     * @return True if the record was added successfully, false otherwise.
     */
    suspend fun addHistoryRecord(historyId: Int, userId: String, vaccineName: String, dateAdministered: String, dateOfDose: String): Boolean

    /**
     * Updates an existing history record in the database.
     *
     * @param historyId The history record ID.
     * @param userId The user's ID.
     * @param vaccineName The name of the vaccine.
     * @param dateAdministered The date the vaccine was administered.
     * @param dateOfDose The date of the dose.
     * @return True if the record was updated successfully, false otherwise.
     */
    suspend fun updateHistoryRecord(historyId: Int, userId: String, vaccineName: String, dateAdministered: String, dateOfDose: String): Boolean

    /**
     * Deletes a history record from the database.
     *
     * @param historyId The history record ID.
     * @return True if the record was deleted successfully, false otherwise.
     */
    suspend fun deleteHistoryRecord(historyId: Int): Boolean

    /**
     * Adds a new schedule to the database.
     *
     * @param vaccinationId The vaccination ID.
     * @param scheduledDate The date the vaccination is scheduled.
     * @return True if the schedule was added successfully, false otherwise.
     */
    suspend fun addSchedule(vaccinationId: Int, scheduledDate: String): Boolean

    /**
     * Updates an existing schedule in the database.
     *
     * @param scheduleId The schedule ID.
     * @param scheduledDate The new scheduled date.
     * @return True if the schedule was updated successfully, false otherwise.
     */
    suspend fun updateSchedule(scheduleId: Int, scheduledDate: String): Boolean

    /**
     * Deletes a schedule from the database.
     *
     * @param scheduleId The schedule ID.
     * @return True if the schedule was deleted successfully, false otherwise.
     */
    suspend fun deleteSchedule(scheduleId: Int): Boolean

    /**
     * Checks if a vaccination record exists in the database.
     *
     * @param vaccinationId The vaccination ID.
     * @return True if the record exists, false otherwise.
     */
    suspend fun checkIfVaccinationRecordExists(vaccinationId: Int): Boolean

    /**
     * Checks if a history record exists in the database.
     *
     * @param historyId The history record ID.
     * @return True if the record exists, false otherwise.
     */
    suspend fun checkIfHistoryRecordExists(historyId: Int): Boolean

    /**
     * Retrieves all schedules for a specific user.
     *
     * @param userId The user's ID.
     * @return A list of [Schedule] objects.
     */
    suspend fun getUserSchedules(userId: String): List<Schedule>
}







