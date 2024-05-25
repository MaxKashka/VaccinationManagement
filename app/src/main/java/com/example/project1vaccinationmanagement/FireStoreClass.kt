package com.example.project1vaccinationmanagement.firestore

import com.example.project1vaccinationmanagement.BaseActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.example.project1vaccinationmanagement.User

/**
 * A class that provides functionalities to interact with Firebase Firestore for user management.
 */
class FireStoreClass {
    private val mFireStore = FirebaseFirestore.getInstance()

    /**
     * Registers a new user in the Firestore database.
     *
     * @param activity The activity context from which this function is called.
     * @param userInfo The user information to be registered.
     * @param onSuccess The callback function to be invoked upon successful registration.
     */
    fun registerUser(activity: BaseActivity, userInfo: User, onSuccess: () -> Unit) {
        mFireStore.collection("users")
            .document(userInfo.email)
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                activity.showFeedbackMessage("Failed to register: ${it.message}", true)
            }
    }
}
