package com.example.firebasetutor

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the layout for the splash screen if needed
        setContentView(R.layout.activity_splash)

        val currentUser = FirebaseAuth.getInstance().currentUser


        // Delay for a certain duration (e.g., 2000 milliseconds or 2 seconds)
        val splashDuration: Long = 2000

        // Handler to post a delayed action

        Handler(Looper.getMainLooper()).postDelayed({
            // Create an Intent to start the RegisterActivity
            val mainIntent = Intent(this, RegisterActivity::class.java)

            if (currentUser != null) {
                // If user is logged in, navigate to the dashboard
                startActivity(Intent(this, Dashboard::class.java))
                finish()
            } else {
                // If user is not logged in, navigate to the login activity
                startActivity(mainIntent)

                // Close the current activity (splash screen)
                finish()

            }

            // Start the MainActivity


        }, splashDuration)
    }
}
