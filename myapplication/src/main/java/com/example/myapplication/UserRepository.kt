package com.example.myapplication

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf

/**
 * UserRepository handles data operations with SharedPreferences persistence.
 */
object UserRepository {
    private const val PREFS_NAME = "user_prefs"
    
    // Initial static list of users
    private val initialUsers = listOf(
        User("admin", "admin123", "System Administrator", "admin@subdivision.com", "Admin", "0000000000", "Admin Office"),
        User("test", "123", "Test User", "test@example.com", "Homeowner", "09123456789", "Blk 1 Lot 1"),
        User("eyong", "123", "Eyong Gabriel", "eyong@example.com", "Homeowner", "09223334455", "Blk 5 Lot 12 Phase 2")
    )

    // Reactive list for the current session
    val users = mutableStateListOf<User>().apply { addAll(initialUsers) }

    /**
     * Loads saved passwords from memory and applies them to the current session.
     */
    fun loadPersistedData(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        users.forEachIndexed { index, user ->
            val savedPassword = prefs.getString("pwd_${user.username}", null)
            if (savedPassword != null) {
                users[index] = user.copy(password = savedPassword)
            }
        }
    }

    /**
     * Authenticates the user.
     */
    fun authenticate(context: Context, username: String, password: String): User? {
        loadPersistedData(context) // Ensure we have latest saved data
        return users.find { user -> user.username == username && user.password == password }
    }

    /**
     * Updates and permanently saves the password.
     */
    fun updatePassword(context: Context, username: String, newPassword: String): Boolean {
        val index = users.indexOfFirst { it.username == username }
        if (index != -1) {
            users[index] = users[index].copy(password = newPassword)
            
            // Permanently save to phone memory
            val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            prefs.edit().putString("pwd_$username", newPassword).apply()
            
            Log.d("UserRepository", "Password permanently saved for: $username")
            return true
        }
        return false
    }
}
