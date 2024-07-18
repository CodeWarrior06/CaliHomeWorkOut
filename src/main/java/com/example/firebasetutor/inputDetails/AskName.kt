package com.example.firebasetutor.inputDetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasetutor.R
import com.example.firebasetutor.databinding.ActivityAskNameBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

@Suppress("DEPRECATION")
class AskName : AppCompatActivity() {
    private var isNameNxtBtnClicked = true
    private lateinit var binding: ActivityAskNameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAskNameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()


        val dbRef = FirebaseDatabase.getInstance().getReference("Employee")
        val nextBtn = findViewById<LinearLayout>(R.id.nextBtnName)
        val inputName = findViewById<EditText>(R.id.inputNameId)


        nextBtn.setOnClickListener {
            // Toggle between background drawables when clicked

            if (inputName.text.isNullOrEmpty()) {
                Toast.makeText(this, "Please Enter your Name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else {
                // Get the UID of the signed-in user
                val username = inputName.text.toString()
                editor.putString("username", username)
                editor.apply()
                val userUid = FirebaseAuth.getInstance().currentUser?.uid
                val intent = Intent(this, AskGenderActivity::class.java)
                if (!userUid.isNullOrEmpty()) {

                    // Save the age to the "Employee" node under the user's UID
                    val nameValue = binding.inputNameId.text.toString()
                    dbRef.child(userUid).child("username").setValue(nameValue)



                }
                startActivity(intent)
                overridePendingTransition(0, 0)

            }

        }
    }
}