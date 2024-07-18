package com.example.firebasetutor.inputDetails

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasetutor.Dashboard
import com.example.firebasetutor.R
import com.example.firebasetutor.databinding.ActivityThankyouBinding
import java.text.DecimalFormat

@Suppress("DEPRECATION")
class Thankyou : AppCompatActivity() {

    private lateinit var binding: ActivityThankyouBinding
    private lateinit var nextBtnThankyou : LinearLayout
    private lateinit var  sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private  var nutritionalStatus = ""
    private var bmiComputeFormatPref = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThankyouBinding.inflate(layoutInflater)
        setContentView(binding.root)

        nextBtnThankyou = findViewById(R.id.continueBtnThankyou)
        nextBtnThankyou.setBackgroundResource(R.drawable.custom_button_background_pressed)



        nextBtnThankyou.setOnClickListener{

            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)

        }

    }
    private fun localDataBMI(){
        val weightString = sharedPreferences.getString("weight", "") ?: ""
        val heightString = sharedPreferences.getString("height", "") ?: ""
        var weightToDouble = weightString.toDoubleOrNull() ?: 0.0
        var heightToDouble = heightString.toDoubleOrNull() ?: 0.0
        var heightToMeter = heightToDouble * 0.01
        var bmiCompute = weightToDouble / (heightToMeter * heightToMeter)
        var bmiComputeFormat  = formatDoubleWithMaxThreeDecimalPlaces(bmiCompute)
        bmiComputeFormatPref = bmiComputeFormat
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

        editor = sharedPreferences.edit()
        editor.putString("nutritionalStatus", nutritionalStatus)
        editor.putString("bmi", bmiComputeFormatPref )
        editor.commit()

    }

    private fun formatDoubleWithMaxThreeDecimalPlaces(value: Double): String {
        val decimalFormat = DecimalFormat("#.##")
        return decimalFormat.format(value)
    }
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {


    }
}



