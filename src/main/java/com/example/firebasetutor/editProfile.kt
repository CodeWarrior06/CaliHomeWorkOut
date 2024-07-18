package com.example.firebasetutor

import android.app.ActivityOptions
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasetutor.databinding.ActivityEditProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.DecimalFormat


@Suppress("DEPRECATION")
class editProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var dbRef: DatabaseReference

    private lateinit var ageDetailText: EditText
    private lateinit var spinnerGender: Spinner
    private lateinit var heightDetailText: EditText
    private lateinit var weightDetailText: EditText
    private lateinit var spinnerActFactor: Spinner
    private lateinit var bmiGradeDetailText: TextView
    private lateinit var nameDetailText: TextView
    private lateinit var bmiDetailText: TextView
    private lateinit var saveBtn: LinearLayout
    private lateinit var cancelBtn: LinearLayout
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: Editor
    private var dataFactorValue = 0
    private var selectedDataFactor = ""
    private var heightValue = ""
    private var weightValue = ""

    private var username = ""
    private var ageValueString = ""
    private var activityFactorString = ""
    private var genderValue = ""
    private var nutritionalStatus = ""

    private var bmi = ""
    private var isToastShowing = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        dbRef = FirebaseDatabase.getInstance().getReference("Employee")

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        // Get the UID of the signed-in user


        nameDetailText = binding.nameDetailId
        ageDetailText = binding.ageDetail
        spinnerGender = binding.genderDetail
        heightDetailText = binding.heightDetail
        weightDetailText = binding.weightDetail
        spinnerActFactor = binding.spinnerActFactor
        bmiGradeDetailText = binding.bmiGradeDetail
        bmiDetailText = binding.bmiDetail

        cancelBtn = binding.cancelBtnId
        saveBtn = binding.saveBtnId

        val dataGender = arrayOf("Male", "Female")
        val dataFactor = arrayOf("Sedentary", "Lightly Active", "Moderately Active", "Very Active")

        // Create an ArrayAdapter using the string array and a default spinner layout

        val adapterGender = ArrayAdapter(this, android.R.layout.simple_spinner_item, dataGender)
        val adapterActFactor = ArrayAdapter(this, android.R.layout.simple_spinner_item, dataFactor)

        // Specify the layout to use when the list of choices appears
        adapterGender.setDropDownViewResource(R.layout.dropdown_gender_macros)
        adapterActFactor.setDropDownViewResource(R.layout.dropdown_gender_macros)

        // Apply the adapter to the spinner
        spinnerGender.adapter = adapterGender
        spinnerActFactor.adapter = adapterActFactor

        // Add a listener to spinnerActFactor to detect changes in selection
        spinnerActFactor.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // When selection changes, update dataFactorValue
                dropDownGetValue()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle case when nothing is selected
            }
        }



        saveBtn.setOnClickListener {


            username = nameDetailText.text.toString()
            editor.putString("username", username)


            ageValueString = ageDetailText.text.toString()
            editor.putString("age", ageValueString)


            genderValue = spinnerGender.selectedItem.toString()
            editor.putString("gender", genderValue)


            heightValue = heightDetailText.text.toString()
            editor.putString("height", heightValue)


            weightValue = weightDetailText.text.toString()
            editor.putString("weight", weightValue)


            val nameET = findViewById<EditText>(R.id.nameDetailId)
            val ageET = findViewById<EditText>(R.id.ageDetail)
            val heightET = findViewById<EditText>(R.id.heightDetail)
            val weightET = findViewById<EditText>(R.id.weightDetail)

            if (username.isNullOrEmpty() || ageValueString.isNullOrEmpty() || weightValue.isNullOrEmpty() || heightValue.isNullOrEmpty()) {
                var username = ""
                var ageValueString = ""
                var heightValue = ""
                var weightValue = ""


                if (username.isNullOrEmpty()) {
                    nameET.text.clear()
                }
                if (ageValueString.isNullOrEmpty()) {
                    ageET.text.clear()
                }
                if (heightValue.isNullOrEmpty()) {
                    heightET.text.clear()
                }
                if (weightValue.isNullOrEmpty()) {
                    weightET.text.clear()
                }
                showToast("Please fill in all the blanks.")
                return@setOnClickListener


            }

            activityFactorString = selectedDataFactor
            editor.putString("activityFactor", activityFactorString)
            editor.apply()

            localDataBMI()


            bmiDetailText.text = nutritionalStatus
            editor.putString("nutritionalStatus", nutritionalStatus)

            bmiGradeDetailText.text = bmi
            editor.putString("bmi", bmi)

            dataSendToFirebase()

            val intentSave = Intent(this, ProfilePg::class.java)
            val animation = ActivityOptions.makeCustomAnimation(
                this,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
            startActivity(intentSave, animation.toBundle())



        }


        editProfileOption()

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


    private fun editProfileOption() {
        cancelBtn.setOnClickListener {
            val intentCancel = Intent(this, ProfilePg::class.java)


            val animation = ActivityOptions.makeCustomAnimation(
                this,
                R.anim.slide_in_right,
                R.anim.slide_out_left
            )
            startActivity(intentCancel, animation.toBundle())

        }



    }

    private fun dataUpdate(){


        username = nameDetailText.text.toString()
        editor.putString("username", username)


        ageValueString = ageDetailText.text.toString()
        editor.putString("age", ageValueString)


        genderValue = spinnerGender.selectedItem.toString()
        editor.putString("gender",genderValue)


        heightValue = heightDetailText.text.toString()
        editor.putString("height",heightValue)


        weightValue = weightDetailText.text.toString()
        editor.putString("weight",weightValue)


        activityFactorString = selectedDataFactor
        editor.putString("activityFactor", activityFactorString)
        editor.apply()

        localDataBMI()


        bmiDetailText.text = nutritionalStatus
        editor.putString("nutritionalStatus", nutritionalStatus)

        bmiGradeDetailText.text = bmi
        editor.putString("bmi", bmi)

        dataSendToFirebase()





    }

    private fun dropDownGetValue(){
        // Get the selected value from the spinner
        selectedDataFactor = spinnerActFactor.selectedItem.toString()

        // Define a variable to hold the corresponding value
        dataFactorValue = 0 // Default value

        // Assign values based on the selected dataFactor
        when (selectedDataFactor) {
            "Sedentary" -> dataFactorValue = 30
            "Lightly Active" -> dataFactorValue = 35
            "Moderately Active" -> dataFactorValue = 40
            "Very Active" -> dataFactorValue = 45
        }


    }

    private fun localDataBMI() {
        var weightToDouble = weightValue.toDoubleOrNull() ?: 0.0
        var heightToDouble = heightValue.toDoubleOrNull() ?: 0.0

        var heightToMeter = heightToDouble * 0.01
        var bmiCompute = weightToDouble / (heightToMeter * heightToMeter)
        var bmiComputeFormat  = formatDoubleWithMaxThreeDecimalPlaces(bmiCompute)
        var bmiComputeFormatPref = bmiComputeFormat
        var bmiComputeToDouble = bmiComputeFormatPref.toDouble()
        if(bmiComputeToDouble < 18.5){
            nutritionalStatus = "Underweight"

        }
        else  if(bmiComputeToDouble <= 24.9){
            nutritionalStatus  = "Normal"

        }

        else  if(bmiComputeToDouble <= 29.9){
            nutritionalStatus  = "Pre - Obesity"

        }
        else  if(bmiComputeToDouble <= 34.9){
            nutritionalStatus  = "Obesity Class 1"

        }
        else  if(bmiComputeToDouble <= 39.9){
            nutritionalStatus  = "Obesity Class 2"

        }
        else  if(bmiComputeToDouble >= 40) {
            nutritionalStatus = "Obesity Class 3"

        }
        else{
            nutritionalStatus  = "None"

        }


        editor.putString("nutritionalStatus", nutritionalStatus)
        editor.putString("bmi", bmiComputeFormatPref )
        editor.apply()


    }

    private fun formatDoubleWithMaxThreeDecimalPlaces(value: Double): String {
        val decimalFormat = DecimalFormat("#.##")
        return decimalFormat.format(value)
    }

    private fun dataSendToFirebase() {
        val userUid = FirebaseAuth.getInstance().currentUser?.uid
        val userRef = userUid?.let { dbRef.child(it) }

        val userDataMap = HashMap<String, Any>()
        userDataMap["username"] = username
        userDataMap["age"] = ageValueString.toInt() // Convert ageValueString to Int
        userDataMap["gender"] = genderValue
        userDataMap["height"] = heightValue.toDouble() // Convert heightValue to Double
        userDataMap["weight"] = weightValue.toDouble()

        userDataMap["activity_factor"] = activityFactorString

        userDataMap["nutritional_Status"] = nutritionalStatus
        userDataMap["bmi"] = bmi

        // Send the data to the Realtime Database

        userRef?.updateChildren(userDataMap)
            ?.addOnSuccessListener {
                // Data sent successfully
            }
            ?.addOnFailureListener { e ->
                // Failed to send data
                Log.e(TAG, "Error sending data to Realtime Database: ${e.message}")
            }

    }

}
