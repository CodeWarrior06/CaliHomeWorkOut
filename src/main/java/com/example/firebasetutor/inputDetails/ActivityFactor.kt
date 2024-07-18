
package com.example.firebasetutor.inputDetails


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.firebasetutor.R
import com.example.firebasetutor.databinding.ActivityFactorBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase



@Suppress("DEPRECATION")
class activityFactor  : AppCompatActivity() {


    private lateinit var binding: ActivityFactorBinding
    private var isActFactorNxtBtnClicked = false
    private lateinit var dbRef: DatabaseReference
    //Choices button
    private var isSedentaryClicked = false
    private var isLightlyClicked = false
    private var isModeratelyClicked = false
    private var isVeryClicked = false
    private var isExtraClicked = false
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFactorBinding.inflate(layoutInflater)
        setContentView(binding.root)


        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        //backbutton
        binding.actfactorBckBtn.setOnClickListener {
            val intent = Intent(this, askWeight::class.java)

            startActivity(intent)
            overridePendingTransition(0, 0)

        }
        val sedentaryClickableBox = findViewById<ConstraintLayout>(R.id.sedentaryOptId)
        val lightlyClickableBox = findViewById<ConstraintLayout>(R.id.lightlyOptId)
        val moderatelyClickableBox = findViewById<ConstraintLayout>(R.id.moderateOptID)
        val veryClickableBox = findViewById<ConstraintLayout>(R.id.veryOptId)


        sedentaryClickableBox.setBackgroundResource(R.drawable.custom_button_background_pressed)
        lightlyClickableBox.setBackgroundResource(R.drawable.custom_button_background_pressed)
        moderatelyClickableBox.setBackgroundResource(R.drawable.custom_button_background_pressed)
        veryClickableBox.setBackgroundResource(R.drawable.custom_button_background_pressed)


        //Channel to employee folder
        dbRef = FirebaseDatabase.getInstance().getReference("Employee")
        //sedentary click
        sedentaryClickableBox.setOnClickListener{
            isSedentaryClicked = true
            isLightlyClicked = false
            isModeratelyClicked = false
            isVeryClicked = false

            sedentaryClickableBox.setBackgroundResource(R.drawable.yellow_button_background_pressed)
            lightlyClickableBox.setBackgroundResource(R.drawable.custom_button_background_pressed)
            moderatelyClickableBox.setBackgroundResource(R.drawable.custom_button_background_pressed)
            veryClickableBox.setBackgroundResource(R.drawable.custom_button_background_pressed)

        }

        //lightly click
        lightlyClickableBox.setOnClickListener{
            isSedentaryClicked = false
            isLightlyClicked = true
            isModeratelyClicked = false
            isVeryClicked = false

            lightlyClickableBox.setBackgroundResource(R.drawable.yellow_button_background_pressed)
            moderatelyClickableBox.setBackgroundResource(R.drawable.custom_button_background_pressed)
            sedentaryClickableBox.setBackgroundResource(R.drawable.custom_button_background_pressed)
            veryClickableBox.setBackgroundResource(R.drawable.custom_button_background_pressed)

        }

        moderatelyClickableBox.setOnClickListener{
            isSedentaryClicked = false
            isLightlyClicked = false
            isModeratelyClicked = true
            isVeryClicked = false

            moderatelyClickableBox.setBackgroundResource(R.drawable.yellow_button_background_pressed)
            lightlyClickableBox.setBackgroundResource(R.drawable.custom_button_background_pressed)
            sedentaryClickableBox.setBackgroundResource(R.drawable.custom_button_background_pressed)
            veryClickableBox.setBackgroundResource(R.drawable.custom_button_background_pressed)

        }

        veryClickableBox.setOnClickListener{
            isSedentaryClicked = false
            isLightlyClicked = false
            isModeratelyClicked = false
            isVeryClicked = true
            veryClickableBox.setBackgroundResource(R.drawable.yellow_button_background_pressed)
            lightlyClickableBox.setBackgroundResource(R.drawable.custom_button_background_pressed)
            sedentaryClickableBox.setBackgroundResource(R.drawable.custom_button_background_pressed)
            moderatelyClickableBox.setBackgroundResource(R.drawable.custom_button_background_pressed)

        }





        //Next button on click func


        val nxtActFactorClickableBox = findViewById<LinearLayout>(R.id.nxtBtnActivityFactor)
        nxtActFactorClickableBox.isClickable = true

        // Set the default background drawable
        nxtActFactorClickableBox.setBackgroundResource(R.drawable.custom_button_background_pressed)

        nxtActFactorClickableBox.setOnClickListener {
            localData() //For Offline mode to send the data in Profile Pg
            // Check if you do not chose option
            if (!isSedentaryClicked) {
                if(!isLightlyClicked){
                    if(!isModeratelyClicked){
                        if(!isVeryClicked){
                            if(!isExtraClicked){

                                Toast.makeText(this, "Please Choose your Activity Factor", Toast.LENGTH_SHORT).show()
                                return@setOnClickListener
                            }
                        }
                    }
                }
            }
            isActFactorNxtBtnClicked = !isActFactorNxtBtnClicked
            nxtActFactorClickableBox.setBackgroundResource(
                if (isActFactorNxtBtnClicked) {
                    R.drawable.select_height_centimeter_after_color
                } else R.drawable.custom_button_background_pressed
            )


            val intent = Intent(this, AskDisease1::class.java)
            val userUid = FirebaseAuth.getInstance().currentUser?.uid
            if (!userUid.isNullOrEmpty()) {
                // Save the height to the "Employee" node under the user's UID
                val activityFactorValue = if(isSedentaryClicked) "Sedentary Active"
                else if(isLightlyClicked) "Lightly Active"
                else if(isModeratelyClicked) "Moderately Active"
                else if(isVeryClicked) "Very Active"
                else "Extra Active"
                dbRef.child(userUid).child("activity_factor").setValue(activityFactorValue)


            }

            startActivity(intent)
            overridePendingTransition(0, 0)


        }

        //go to next page


    }
    private fun localData(){
        if(isSedentaryClicked){
            var activityFactor = "Sedentary"
            editor.putString("activityFactor", activityFactor)
            editor.apply()

        }
        else if(isLightlyClicked){
            var activityFactor = "Lightly Active"
            editor.putString("activityFactor", activityFactor)
            editor.apply()
        }
        else if(isModeratelyClicked){
            var activityFactor = "Moderately Active"
            editor.putString("activityFactor", activityFactor)
            editor.apply()
        }
        else if(isVeryClicked){
            var activityFactor = "Very Active"
            editor.putString("activityFactor", activityFactor)
            editor.apply()
        }
    }
}

