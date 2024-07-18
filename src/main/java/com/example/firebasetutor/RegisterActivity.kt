package com.example.firebasetutor

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasetutor.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

@Suppress("DEPRECATION")
class RegisterActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    private var isToastShowing: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Register"

        // Initialize FirebaseAuth instance
        auth = FirebaseAuth.getInstance()

        binding.loginTV.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            overridePendingTransition(0, 0)
            finish()
        }
        //register xml  btn id
        binding.createAccountBtn.setOnClickListener {
            //register =.xml  email and pass id
            val email = binding.emailRegister.text.toString()
            val password = binding.passwordRegister.text.toString()
            val confirmPassword = binding.passwordConfirm.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                    if(confirmPassword.isNotEmpty() && confirmPassword == password){
                        // Create user with email and password
                        auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    // Registration successful, navigate to login activity
                                    startActivity(Intent(this, LoginActivity::class.java))
                                    overridePendingTransition(0, 0)
                                    finish()
                                } else {
                                    // Registration failed, display error message
                                    Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                                }
                            }
                    }
                    else if(confirmPassword.isNullOrEmpty()){
                        showToast("Please fill all the blanks")
                    }
                    else if(confirmPassword != password){
                        showToast("You're confirm password is incorrect")
                    }

                    }
                else {
                // Email or password is empty, show appropriate message
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun showToast(message: String) {
        val toastDuration : Long
        // Check if a Toast is currently being displayed
        if (!isToastShowing) {
            isToastShowing = true
            val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
            toast.show()

            // Schedule a task to reset the isToastShowing flag after the Toast duration
            toastDuration = 2000 // Duration for Toast.LENGTH_SHORT in milliseconds (2 seconds)
            Handler(Looper.getMainLooper()).postDelayed({
                isToastShowing = false
            }, toastDuration)
        }
    }
}