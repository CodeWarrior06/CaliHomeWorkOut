package com.example.firebasetutor.inputDetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasetutor.R
import com.example.firebasetutor.databinding.ActivityAskGenderBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

@Suppress("DEPRECATION")
class AskGenderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAskGenderBinding
    private var isMaleClicked = false
    private var isFemaleClicked = false
    private var isGenderNxtBtnClicked = false
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAskGenderBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //backbutton
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        binding.askGenderBckBtn.setOnClickListener {
            val intent = Intent(this, AskName::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)

        }

        val maleClickableBox = findViewById<LinearLayout>(R.id.maleOptId)
        val femaleClickableBox = findViewById<LinearLayout>(R.id.femaleOptId)
        dbRef = FirebaseDatabase.getInstance().getReference("Employee")
        maleClickableBox.isClickable = true
        femaleClickableBox.isClickable = true
        // Set the default background drawable
        maleClickableBox.setBackgroundResource(R.drawable.custom_button_background_pressed)
        femaleClickableBox.setBackgroundResource(R.drawable.custom_button_background_pressed)

        val userUid = FirebaseAuth.getInstance().currentUser?.uid
        //MALEEEE
        maleClickableBox.setOnClickListener {
            // Toggle between background drawables when clicked
            isMaleClicked = true
            isFemaleClicked = false
            maleClickableBox.setBackgroundResource(R.drawable.white_custom_button_background)
            femaleClickableBox.setBackgroundResource(R.drawable.custom_button_background_pressed)
            editor.putString("gender", "Male")
            editor.apply()
        }
        //FEMALEEEE
        femaleClickableBox.setOnClickListener {
            // Toggle between background drawables when clicked
            isFemaleClicked = true
            isMaleClicked = false
            femaleClickableBox.setBackgroundResource(R.drawable.white_custom_button_background)
            maleClickableBox.setBackgroundResource(R.drawable.custom_button_background_pressed)
            editor.putString("gender", "Female")
            editor.apply()
        }

        //Onclick effect of next button
        val nxtGenderClickableBox = findViewById<LinearLayout>(R.id.nextBtnGender)
        nxtGenderClickableBox.isClickable = true

        // Set the default background drawable
        nxtGenderClickableBox.setBackgroundResource(R.drawable.custom_button_background_pressed)

        nxtGenderClickableBox.setOnClickListener {
            // Check if at least one gender is selected
            if (!isMaleClicked && !isFemaleClicked) {
                Toast.makeText(this, "Please Choose your Gender", Toast.LENGTH_SHORT).show()
                // Show a message or handle the case where no gender is selected
                return@setOnClickListener
            }

            // Toggle between background drawables when clicked
            isGenderNxtBtnClicked = !isGenderNxtBtnClicked
            nxtGenderClickableBox.setBackgroundResource(
                if (isGenderNxtBtnClicked) {
                    R.drawable.select_height_centimeter_after_color
                } else R.drawable.custom_button_background_pressed
            )

            // Save the selected gender to the database if the user is signed in
            if (!userUid.isNullOrEmpty()) {
                // Save the selected gender to the "gender" node under the user's UID
                val genderValue = if (isMaleClicked) "Male" else "Female"
                dbRef.child(userUid).child("gender").setValue(genderValue)
            }

            // Go to the next page
            val intent = Intent(this, askAge::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)

        }
    }
}


