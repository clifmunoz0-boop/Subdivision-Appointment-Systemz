package com.example.myapplication

import android.util.Log

/**
 * UserRepository handles data operations.
 * Currently it uses a Mock (local) list of users.
 * To connect to XAMPP later:
 * 1. Use a library like Retrofit or Volley.
 * 2. Create a PHP script on XAMPP (e.g., login.php) that queries MySQL.
 * 3. Update the authenticate function to make a network call to your XAMPP server.
 */
object UserRepository {
    // Current local data (Mock Database)
    private val users = mutableListOf(
        User("testtest", "123", "Charles LeKirk", "charleslekirk@gmail.com", "Homeowner", "09587653211", "Blk 20 Lot 21c Ginuntuang St."),
        User("test", "123", "Test User", "test@example.com", "Visitor", "09123456789", "Test Address")
    )

    /**
     * Authenticates the user.
     * When you switch to XAMPP, this logic will move to a background thread
     * and use a network request instead of searching a local list.
     */

    fun authenticate(username: String, password: String): User? {
        Log.d("UserRepository", "Attempting login for: $username")
        
        // MOCK LOGIC: Replace this with an API call later
        return users.find { it.username == username && it.password == password }
    }
}
