package com.example.firebasetutor.inputDetails

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.util.Log
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasetutor.R
import com.example.firebasetutor.databinding.ActivityAskHeightBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.DecimalFormat

@Suppress("DEPRECATION")
class askHeight : AppCompatActivity() {
    private lateinit var binding: ActivityAskHeightBinding

    private var isCmClicked = false
    private var isInchClicked = false
    private var isFtClicked = false
    private lateinit var inputHeight: EditText
    private lateinit var nxtHeightClickableBox: LinearLayout
    private lateinit var backbutton: LinearLayout
    private lateinit var dbRef: DatabaseReference
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var sharedPreferences: SharedPreferences
    private var heightValueString = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAskHeightBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        // Back button
        backbutton = findViewById(R.id.heightBckBtn)
        backbutton.setOnClickListener {
            val intentToAge = Intent(this, askAge::class.java)
            startActivity(intentToAge)
            overridePendingTransition(0, 0)
            finish()
        }

        // Get the references to cm and inch TextViews
        val cmClickableBox = findViewById<TextView>(R.id.selectCm)
        val inchClickableBox = findViewById<TextView>(R.id.selectInch)
        val ftClickableBox = findViewById<TextView>(R.id.selectFt)

        // Set the default background drawable
        cmClickableBox.setBackgroundResource(R.drawable.custom_button_background_pressed)
        inchClickableBox.setBackgroundResource(R.drawable.custom_button_background_pressed)
        ftClickableBox.setBackgroundResource(R.drawable.custom_button_background_pressed)


        inputHeight = findViewById(R.id.inputHeightId)

        // Set input type to allow decimal numbers
        inputHeight.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL

        // Set length limit to 6 digits (3 whole numbers + 3 decimal points)
        inputHeight.filters = arrayOf(InputFilter.LengthFilter(6))

        // Firebase Database reference
        dbRef = FirebaseDatabase.getInstance().getReference("Employee")

        cmClickableBox.setOnClickListener {
            // Toggle between background drawables when clicked
            isCmClicked = true
            isInchClicked = false
            isFtClicked = false

            cmClickableBox.setBackgroundResource(R.drawable.select_height_centimeter_after_color)
            inchClickableBox.setBackgroundResource(R.drawable.custom_button_background_pressed)
            ftClickableBox.setBackgroundResource(R.drawable.custom_button_background_pressed)
        }

        inchClickableBox.setOnClickListener {
            // Toggle between background drawables when clicked
            isCmClicked = false
            isInchClicked = true
            isFtClicked = false

            inchClickableBox.setBackgroundResource(R.drawable.select_height_centimeter_after_color)
            cmClickableBox.setBackgroundResource(R.drawable.custom_button_background_pressed)
            ftClickableBox.setBackgroundResource(R.drawable.custom_button_background_pressed)
        }

        ftClickableBox.setOnClickListener {
            // Toggle between background drawables when clicked
            isCmClicked = false
            isInchClicked = false
            isFtClicked =true

            inchClickableBox.setBackgroundResource(R.drawable.custom_button_background_pressed)
            cmClickableBox.setBackgroundResource(R.drawable.custom_button_background_pressed)
           ftClickableBox.setBackgroundResource(R.drawable.select_height_centimeter_after_color)
        }

        // Next button
        nxtHeightClickableBox = findViewById(R.id.nextBtnHeight)
        nxtHeightClickableBox.isClickable = true

        // Set the default background drawable
        nxtHeightClickableBox.setBackgroundResource(R.drawable.custom_button_background_pressed)

        nxtHeightClickableBox.setOnClickListener {
            //Shared preference get Info
            localData()
            val intent = Intent(this, askWeight::class.java)
            // Check if height is entered
            if (inputHeight.text.isEmpty()) {
                Toast.makeText(this, "Please Enter your Height", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else{
                val userUid = FirebaseAuth.getInstance().currentUser?.uid

                if (!userUid.isNullOrEmpty()) {


                    if (isInchClicked) {
                        // Assuming inputHeight is a Double variable representing the height in inches
                        var convertedHeight = convertInchToCm(inputHeight.text.toString().toDouble())
                         var formattedHeight = formatDoubleWithMaxThreeDecimalPlaces(convertedHeight)

                        dbRef.child(userUid).child("height").setValue(formattedHeight)
                    } else if (isCmClicked) {
                        // Assuming inputHeight is a Double variable representing the height in cm
                        var cmHeight = inputHeight.text.toString().toDouble()
                       var formattedHeight = formatDoubleWithMaxThreeDecimalPlaces(cmHeight)
                        var convertedHeight = cmHeight
                        dbRef.child(userUid).child("height").setValue(formattedHeight)
                    }
                    else if (isFtClicked) {
                        // Assuming inputHeight is a Double variable representing the height in cm
                        var convertedHeight = convertFtToCm(inputHeight.text.toString().toDouble())
                        var formattedHeight = formatDoubleWithMaxThreeDecimalPlaces(convertedHeight)

                        dbRef.child(userUid).child("height").setValue(formattedHeight)
                    }










                    else {
                        dbRef.child(userUid).child("height").setValue("None")
                    }

                    // Start the askWeight activity


                    // Call the function to start ProfilePg activity with intent data

                }

                startActivity(intent)
                overridePendingTransition(0, 0)
                finish()
            }

        }

    }

    fun convertInchToCm(inputHeight: Double): Double {
        return inputHeight * 2.54
    }

    fun convertFtToCm(inputHeight: Double): Double {
        return inputHeight * 30.48
    }



    // Function to format a double value with a maximum of 3 decimal places
    private fun formatDoubleWithMaxThreeDecimalPlaces(value: Double): String {
        val decimalFormat = DecimalFormat("#.###")
        return decimalFormat.format(value)
    }

    private fun localData() {
        val heightLocal = inputHeight.text.toString()

        if (heightLocal.isNotEmpty()) {
            if (isInchClicked) {
                val heightDouble = heightLocal.toDouble()
                val heightInchToCm = heightDouble * 2.54
                val heightInchToCmFormat = String.format("%.2f", heightInchToCm)
                editor.putString("height", heightInchToCmFormat)
                editor.commit()
            }
            if (isFtClicked) {
                val heightDouble = heightLocal.toDouble()
                val heightFtToCm = heightDouble * 30.48
                val heightFtToCmFormat = String.format("%.2f", heightFtToCm)
                editor.putString("height", heightFtToCmFormat)
                editor.commit()
                Log.d("Foot Convertion Value", "Feet value is $heightFtToCm.")
            }

            else if (isCmClicked) {
                editor.putString("height", heightLocal)
                editor.commit()
            }
        } else {

        }
    }



}
