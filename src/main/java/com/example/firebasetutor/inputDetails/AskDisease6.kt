package com.example.firebasetutor.inputDetails

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasetutor.AskDisease5
import com.example.firebasetutor.R
import com.example.firebasetutor.databinding.ActivityAskDisease6Binding

private lateinit var binding: ActivityAskDisease6Binding
private var selectedRadioButtonText: String? = null
private var selectedRadioButtonId: Int? = null
private lateinit var nextBtnDisease: LinearLayout
private var isToastShowing = false
private lateinit var buttonContinue: TextView
private lateinit var diseaseBackBtn: LinearLayout
private lateinit var yesBtn: TextView
private lateinit var noBtn: TextView
private var ds1 = ""
private var ds2 = ""
private var ds3 = ""
private var ds4 = ""
private var ds5 = ""
private var ds6 = ""
private lateinit var editor: SharedPreferences.Editor
private lateinit var sharedPreferences: SharedPreferences
private var answer = ""
@Suppress("DEPRECATION")
class AskDisease6 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAskDisease6Binding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        yesBtn = binding.yesBtn
        noBtn = binding.noBtn
        diseaseBackBtn = binding.diseaseBckBtn
        nextBtnDisease = binding.nextBtnDisease

        diseaseBackBtn.setOnClickListener {
            editor = sharedPreferences.edit()
            editor.putString("askDisease6","None")
            editor.putString("askDisease5","None")
            editor.commit()
            val intentBack = Intent(this, AskDisease5::class.java)
            startActivity(intentBack)
            this.overridePendingTransition(0, 0)
        }

        yesBtn.setOnClickListener {
            sharedPreferences.edit()
            editor.putString("askDisease6","Yes")
            editor.commit()
            yesBtn.setBackgroundResource(R.drawable.white_custom_button_background)
            noBtn.setBackgroundResource(R.drawable.white_custombg_grayradius)
        }

        noBtn.setOnClickListener {
            sharedPreferences.edit()
            editor.putString("askDisease6","No")
            editor.commit()
            noBtn.setBackgroundResource(R.drawable.white_custom_button_background)
            yesBtn.setBackgroundResource(R.drawable.white_custombg_grayradius)

        }

        nextBtnDisease.setOnClickListener {
            val askDisease6 = sharedPreferences.getString("askDisease6", "None") // Get the updated value

            var diseaseSP1 = sharedPreferences.getString("askDisease1", "")
            var diseaseSP2 = sharedPreferences.getString("askDisease2", "")
            var diseaseSP3 = sharedPreferences.getString("askDisease3", "")
            var diseaseSP4 = sharedPreferences.getString("askDisease4", "")
            var diseaseSP5 = sharedPreferences.getString("askDisease5", "")
            var diseaseSP6 = sharedPreferences.getString("askDisease6", "")

            if(diseaseSP1 == "Yes" || diseaseSP2 == "Yes" || diseaseSP3 == "Yes" || diseaseSP4 == "Yes" || diseaseSP5 == "Yes"){
                    if(diseaseSP6 == "Yes") {
                        val intentBack1 = Intent(this, Thankyou::class.java)
                        startActivity(intentBack1)
                    }
                    else if(diseaseSP6 == "No") {
                        diseaseDialog()

                    }
                    else if(diseaseSP6 == "None") {
                        showToast("Please choose your option to proceed.")
                    }
            }
            else if(diseaseSP1 == "No" || diseaseSP2 == "No" || diseaseSP3 == "No" || diseaseSP4 == "No" || diseaseSP5 == "No"){
                    if(diseaseSP6 == "No") {
                        val intentBack3 = Intent(this, Thankyou::class.java)
                        startActivity(intentBack3)
                    }
                    else if(diseaseSP6 == "No") {
                        val intentBack3 = Intent(this, Thankyou::class.java)
                        startActivity(intentBack3)
                    }
            }
        }
    }


    private fun showToast(message: String) {
        val toastDuration: Long
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
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        // Do nothing
    }

    private fun diseaseDialog() {
        // Inflate the custom dialog layout
        val dialogDisease = layoutInflater.inflate(R.layout.dialog_disease, null)

        // Create an AlertDialog builder and set the custom view
        val builder = AlertDialog.Builder(this)
            .setView(dialogDisease)

        // Create the AlertDialog object
        val alertDialog = builder.create()

        // Find the Yes and No buttons in the dialog layout
        buttonContinue = dialogDisease.findViewById(R.id.buttonContinue)

        // Set background color for the Yes button
        buttonContinue.setBackgroundResource(R.drawable.dialog_custom_before_press_red)

        // Set click listeners for the Yes and No buttons
        buttonContinue.setOnClickListener {
            // Handle Yes button click
            val intent = Intent(this, Thankyou::class.java)
            alertDialog.dismiss()
            startActivity(intent)
            this.overridePendingTransition(0, 0)

        }

        // Show the AlertDialog
        alertDialog.show()
    }
}


