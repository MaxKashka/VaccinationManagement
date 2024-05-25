package com.example.project1vaccinationmanagement

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

/**
 * Object that manages the database connection.
 */
object DatabaseConnection {
    private const val url = "jdbc:mysql://sql7.freesqldatabase.com:3306/sql7708769?useSSL=false&useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC"
    private const val user = "sql7708769"
    private const val password = "CcTPaZ9vx5"

    init {
        try {
            Class.forName("com.mysql.jdbc.Driver")
        } catch (e: ClassNotFoundException) {
            throw RuntimeException("Driver not found.", e)
        }
    }

    /**
     * Gets a connection to the database.
     *
     * @return A [Connection] object for interacting with the database.
     * @throws SQLException if a database access error occurs or the URL is null.
     */
    @Throws(SQLException::class)
    fun getConnection(): Connection {
        return DriverManager.getConnection(url, user, password)
    }
}





