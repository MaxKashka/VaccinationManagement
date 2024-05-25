package com.example.project1vaccinationmanagement

/**
 * Represents a user in the vaccination management system.
 *
 * @property userId The unique identifier of the user.
 * @property name The name of the user.
 * @property email The email address of the user.
 * @property password The password of the user's account.
 */
data class User(
    val userId: String,
    val name: String,
    val email: String,
    val password: String
)
