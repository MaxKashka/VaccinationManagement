package com.example.project1vaccinationmanagement

import java.sql.Connection
import java.sql.PreparedStatement

/**
 * A helper class to manage database operations.
 *
 * @property connection The database connection to be used for operations.
 */
class DatabaseHelper(private val connection: Connection) : DBHelperInterface {

    /**
     * Adds a new user to the database.
     *
     * @param userId The user's ID.
     * @param name The user's name.
     * @param email The user's email.
     * @param password The user's password.
     * @return True if the user was added successfully, false otherwise.
     */
    override suspend fun addUser(userId: String, name: String, email: String, password: String): Boolean {
        val query = "CALL AddUser(?, ?, ?, ?)"
        val statement: PreparedStatement = connection.prepareStatement(query)
        statement.setString(1, userId)
        statement.setString(2, name)
        statement.setString(3, email)
        statement.setString(4, password)
        val result = statement.executeUpdate()
        statement.close()
        return result > 0
    }

    /**
     * Updates user data in the database.
     *
     * @param userId The user's ID.
     * @param name The user's name.
     * @param email The user's email.
     * @param password The user's password.
     * @return True if the user data was updated successfully, false otherwise.
     */
    override suspend fun updateUserData(userId: String, name: String, email: String, password: String): Boolean {
        val query = "CALL UpdateUserData(?, ?, ?, ?)"
        val statement: PreparedStatement = connection.prepareStatement(query)
        statement.setString(1, userId)
        statement.setString(2, name)
        statement.setString(3, email)
        statement.setString(4, password)
        val result = statement.executeUpdate()
        statement.close()
        return result > 0
    }

    /**
     * Deletes a user from the database.
     *
     * @param userId The user's ID.
     * @return True if the user was deleted successfully, false otherwise.
     */
    override suspend fun deleteUser(userId: String): Boolean {
        val query = "CALL DeleteUser(?)"
        val statement: PreparedStatement = connection.prepareStatement(query)
        statement.setString(1, userId)
        val result = statement.executeUpdate()
        statement.close()
        return result > 0
    }

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
    override suspend fun addVaccinationRecord(vaccinationId: Int, userId: String, vaccineName: String, dateAdministered: String, nextDoseDate: String): Boolean {
        val query = "CALL AddVaccinationRecord(?, ?, ?, ?, ?)"
        val statement: PreparedStatement = connection.prepareStatement(query)
        statement.setInt(1, vaccinationId)
        statement.setString(2, userId)
        statement.setString(3, vaccineName)
        statement.setString(4, dateAdministered)
        statement.setString(5, nextDoseDate)
        val result = statement.executeUpdate()
        statement.close()
        return result > 0
    }

    /**
     * Updates an existing vaccination record in the database.
     *
     * @param vaccinationId The vaccination ID.
     * @param vaccineName The name of the vaccine.
     * @param dateAdministered The date the vaccine was administered.
     * @param nextDoseDate The date for the next dose.
     * @return True if the record was updated successfully, false otherwise.
     */
    override suspend fun updateVaccinationRecord(vaccinationId: Int, vaccineName: String, dateAdministered: String, nextDoseDate: String): Boolean {
        val query = "CALL UpdateVaccinationRecord(?, ?, ?, ?, ?)"
        val statement: PreparedStatement = connection.prepareStatement(query)
        statement.setInt(1, vaccinationId)
        statement.setString(2, vaccineName)
        statement.setString(3, dateAdministered)
        statement.setString(4, nextDoseDate)
        val result = statement.executeUpdate()
        statement.close()
        return result > 0
    }

    /**
     * Deletes a vaccination record from the database.
     *
     * @param vaccinationId The vaccination ID.
     * @return True if the record was deleted successfully, false otherwise.
     */
    override suspend fun deleteVaccinationRecord(vaccinationId: Int): Boolean {
        val query = "CALL DeleteVaccinationRecord(?)"
        val statement: PreparedStatement = connection.prepareStatement(query)
        statement.setInt(1, vaccinationId)
        val result = statement.executeUpdate()
        statement.close()
        return result > 0
    }

    /**
     * Retrieves all history records for a specific user.
     *
     * @param userId The user's ID.
     * @return A list of [HistoryRecord] objects.
     */
    override suspend fun getAllHistoryRecords(userId: String): List<HistoryRecord> {
        val query = "CALL GetAllHistoryRecords(?)"
        val statement: PreparedStatement = connection.prepareStatement(query)
        statement.setString(1, userId)
        val resultSet = statement.executeQuery()
        val historyRecords = mutableListOf<HistoryRecord>()
        while (resultSet.next()) {
            historyRecords.add(
                HistoryRecord(
                    historyId = resultSet.getInt("history_id"),
                    userId = resultSet.getString("user_id"),
                    vaccineName = resultSet.getString("vaccine_name"),
                    dateAdministered = resultSet.getString("date_administered"),
                    dateOfDose = resultSet.getString("date_of_dose")
                )
            )
        }
        resultSet.close()
        statement.close()
        return historyRecords
    }

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
    override suspend fun addHistoryRecord(historyId: Int, userId: String, vaccineName: String, dateAdministered: String, dateOfDose: String): Boolean {
        val query = "CALL AddHistoryRecord(?, ?, ?, ?, ?)"
        val statement: PreparedStatement = connection.prepareStatement(query)
        statement.setInt(1, historyId)
        statement.setString(2, userId)
        statement.setString(3, vaccineName)
        statement.setString(4, dateAdministered)
        statement.setString(5, dateOfDose)
        val result = statement.executeUpdate()
        statement.close()
        return result > 0
    }

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
    override suspend fun updateHistoryRecord(historyId: Int, userId: String, vaccineName: String, dateAdministered: String, dateOfDose: String): Boolean {
        val query = "CALL UpdateHistoryRecord(?, ?, ?, ?, ?)"
        val statement: PreparedStatement = connection.prepareStatement(query)
        statement.setInt(1, historyId)
        statement.setString(2, userId)
        statement.setString(3, vaccineName)
        statement.setString(4, dateAdministered)
        statement.setString(5, dateOfDose)
        val result = statement.executeUpdate()
        statement.close()
        return result > 0
    }

    /**
     * Deletes a history record from the database.
     *
     * @param historyId The history record ID.
     * @return True if the record was deleted successfully, false otherwise.
     */
    override suspend fun deleteHistoryRecord(historyId: Int): Boolean {
        val query = "CALL DeleteHistoryRecord(?)"
        val statement: PreparedStatement = connection.prepareStatement(query)
        statement.setInt(1, historyId)
        val result = statement.executeUpdate()
        statement.close()
        return result > 0
    }

    /**
     * Adds a new schedule to the database.
     *
     * @param vaccinationId The vaccination ID.
     * @param scheduledDate The date the vaccination is scheduled.
     * @return True if the schedule was added successfully, false otherwise.
     */
    override suspend fun addSchedule(vaccinationId: Int, scheduledDate: String): Boolean {
        val query = "CALL AddSchedule(?, ?)"
        val statement: PreparedStatement = connection.prepareStatement(query)
        statement.setInt(1, vaccinationId)
        statement.setString(2, scheduledDate)
        val result = statement.executeUpdate()
        statement.close()
        return result > 0
    }

    /**
     * Updates an existing schedule in the database.
     *
     * @param scheduleId The schedule ID.
     * @param scheduledDate The new scheduled date.
     * @return True if the schedule was updated successfully, false otherwise.
     */
    override suspend fun updateSchedule(scheduleId: Int, scheduledDate: String): Boolean {
        val query = "CALL UpdateSchedule(?, ?)"
        val statement: PreparedStatement = connection.prepareStatement(query)
        statement.setInt(1, scheduleId)
        statement.setString(2, scheduledDate)
        val result = statement.executeUpdate()
        statement.close()
        return result > 0
    }

    /**
     * Deletes a schedule from the database.
     *
     * @param scheduleId The schedule ID.
     * @return True if the schedule was deleted successfully, false otherwise.
     */
    override suspend fun deleteSchedule(scheduleId: Int): Boolean {
        val query = "CALL DeleteSchedule(?)"
        val statement: PreparedStatement = connection.prepareStatement(query)
        statement.setInt(1, scheduleId)
        val result = statement.executeUpdate()
        statement.close()
        return result > 0
    }

    /**
     * Checks if a vaccination record exists in the database.
     *
     * @param vaccinationId The vaccination ID.
     * @return True if the record exists, false otherwise.
     */
    override suspend fun checkIfVaccinationRecordExists(vaccinationId: Int): Boolean {
        val query = "SELECT COUNT(*) FROM Vaccinations WHERE vaccination_id = ?"
        val statement: PreparedStatement = connection.prepareStatement(query)
        statement.setInt(1, vaccinationId)
        val resultSet = statement.executeQuery()
        resultSet.next()
        val exists = resultSet.getInt(1) > 0
        resultSet.close()
        statement.close()
        return exists
    }

    /**
     * Checks if a history record exists in the database.
     *
     * @param historyId The history record ID.
     * @return True if the record exists, false otherwise.
     */
    override suspend fun checkIfHistoryRecordExists(historyId: Int): Boolean {
        val query = "SELECT COUNT(*) FROM HistoryRecords WHERE history_id = ?"
        val statement: PreparedStatement = connection.prepareStatement(query)
        statement.setInt(1, historyId)
        val resultSet = statement.executeQuery()
        resultSet.next()
        val exists = resultSet.getInt(1) > 0
        resultSet.close()
        statement.close()
        return exists
    }

    /**
     * Retrieves all schedules for a specific user.
     *
     * @param userId The user's ID.
     * @return A list of [Schedule] objects.
     */
    override suspend fun getUserSchedules(userId: String): List<Schedule> {
        val query = "CALL GetUserSchedules(?)"
        val statement: PreparedStatement = connection.prepareStatement(query)
        statement.setString(1, userId)
        val resultSet = statement.executeQuery()
        val schedules = mutableListOf<Schedule>()
        while (resultSet.next()) {
            schedules.add(
                Schedule(
                    scheduleId = resultSet.getInt("schedule_id"),
                    vaccinationId = resultSet.getInt("vaccination_id"),
                    scheduledDate = resultSet.getString("scheduled_date")
                )
            )
        }
        resultSet.close()
        statement.close()
        return schedules
    }
}




