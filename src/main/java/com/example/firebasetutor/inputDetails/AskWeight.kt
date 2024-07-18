package com.example.firebasetutor.inputDetails

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasetutor.R
import com.example.firebasetutor.databinding.ActivityAskWeightBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.DecimalFormat

@Suppress("DEPRECATION")
class askWeight : AppCompatActivity() {
    private lateinit var binding: ActivityAskWeightBinding
    private var isKgClicked = false
    private var isLbsClicked = false
    private lateinit var inputWeight: EditText
    private lateinit var dbRef: DatabaseReference
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var sharedPreferences: SharedPreferences



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAskWeightBinding.inflate(layoutInflater)
        setContentView(binding.root)


        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        // Back button
        binding.weightBckBtn.setOnClickListener {
            val intent = Intent(this, askHeight::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)

        }

        // Get the references to kg and lbs TextViews
        val kgClickableBox = findViewById<TextView>(R.id.selectKg)
        val lbsClickableBox = findViewById<TextView>(R.id.selectLbs)

        // Set the default background drawable
        kgClickableBox.setBackgroundResource(R.drawable.custom_button_background_pressed)
        lbsClickableBox.setBackgroundResource(R.drawable.custom_button_background_pressed)

        inputWeight = findViewById(R.id.inputWeightId)

        // Set input type to allow decimal numbers
        inputWeight.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL

        // Set length limit to 6 digits (3 whole numbers + 3 decimal points)
        inputWeight.filters = arrayOf(InputFilter.LengthFilter(6))

        // Firebase Database reference
        dbRef = FirebaseDatabase.getInstance().getReference("Employee")

        // OnClickListeners for kg and lbs
        kgClickableBox.setOnClickListener {
            // Toggle between background drawables when clicked
            isKgClicked = true
            isLbsClicked = false

            kgClickableBox.setBackgroundResource(R.drawable.select_height_centimeter_after_color)
            lbsClickableBox.setBackgroundResource(R.drawable.custom_button_background_pressed)
        }

        lbsClickableBox.setOnClickListener {
            // Toggle between background drawables when clicked
            isKgClicked = false
            isLbsClicked = true

            lbsClickableBox.setBackgroundResource(R.drawable.select_height_centimeter_after_color)
            kgClickableBox.setBackgroundResource(R.drawable.custom_button_background_pressed)
        }

        // Next button
        val nxtWeightClickableBox = findViewById<LinearLayout>(R.id.nxtBtnWeight)
        nxtWeightClickableBox.isClickable = true

        // Set the default background drawable
        nxtWeightClickableBox.setBackgroundResource(R.drawable.custom_button_background_pressed)

        nxtWeightClickableBox.setOnClickListener {
            // Check if at least one metric unit is selected
                localData()
                if (inputWeight.text.isNotEmpty()) {
                    val intent = Intent(this, activityFactor::class.java)
                    // Get the UID of the signed-in user
                    val userUid = FirebaseAuth.getInstance().currentUser?.uid
                    if (!userUid.isNullOrEmpty()) {
                        // Save the weight to the "Employee" node under the user's UID
                        if(isKgClicked){
                            val convertedWeight = inputWeight.text.toString().toDouble()
                            val formattedHeight = formatDoubleWithMaxTwoDecimalPlaces(convertedWeight)
                            dbRef.child(userUid).child("weight").setValue(formattedHeight)
                        }
                        else if(isLbsClicked){
                            val convertedWeight = convertLbsToKg(inputWeight.text.toString().toDouble())
                            val formattedHeight = formatDoubleWithMaxTwoDecimalPlaces(convertedWeight)

                            dbRef.child(userUid).child("weight").setValue(formattedHeight)

                        }else{
                            dbRef.child(userUid).child("weight").setValue("None")
                        }

                    }
                    startActivity(intent)
                    overridePendingTransition(0, 0)

                } else {
                    Toast.makeText(this, "Please Enter your Weight", Toast.LENGTH_SHORT).show()
                }



        }
    }

    fun convertLbsToKg(inputWeight: Double): Double {
        return inputWeight * 0.453592
    }
    private fun formatDoubleWithMaxTwoDecimalPlaces(value: Double): String {
        val decimalFormat = DecimalFormat("#.##")
        return decimalFormat.format(value)
    }

    private fun localData() {
        val weightString = inputWeight.text.toString()

        if (weightString.isNotEmpty()) {
            if (isKgClicked) {
                val weightDouble = weightString.toDouble()
                val weightKgFormat = String.format("%.2f", weightDouble)
                editor.putString("weight", weightKgFormat)
                editor.apply()
            } else if (isLbsClicked) {
                val weightDouble = weightString.toDouble()
                val lbsToKg = 0.453592
                val weightLbsToKg = weightDouble * lbsToKg
                val weightLbsToKgFormat = String.format("%.2f", weightLbsToKg)
                editor.putString("weight", weightLbsToKgFormat)
                editor.apply()
            }
        } else {

        }
    }

}
