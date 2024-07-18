package com.example.firebasetutor.beginner

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
import com.example.firebasetutor.R
import com.example.firebasetutor.databinding.ActivityAskDisease3Binding
import com.example.firebasetutor.inputDetails.AskDisease2

private lateinit var binding: ActivityAskDisease3Binding
private var selectedRadioButtonText: String? = null
private var selectedRadioButtonId: Int? = null
private lateinit var nextBtnDisease: LinearLayout
private var isToastShowing = false
private lateinit var buttonContinue: TextView
private lateinit var diseaseBackBtn: LinearLayout
private lateinit var yesBtn: TextView
private lateinit var noBtn: TextView
private lateinit var editor: SharedPreferences.Editor
private lateinit var sharedPreferences: SharedPreferences
private var answer = ""
@Suppress("DEPRECATION")
class AskDisease3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAskDisease3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

                //THIS FILE HAS ERROS TO BE FIXED lATURRRRRRR
        yesBtn = binding.yesBtn
        noBtn = binding.noBtn
        diseaseBackBtn = binding.diseaseBckBtn
        nextBtnDisease = binding.nextBtnDisease

        diseaseBackBtn.setOnClickListener {
            editor = sharedPreferences.edit()
            editor.putString("askDisease3","None")
           editor.putString("askDisease2","None")
            editor.commit()
            val intentBack = Intent(this, AskDisease2::class.java)
            startActivity(intentBack)
            this.overridePendingTransition(0, 0)

        }

        yesBtn.setOnClickListener {
            editor.putString("askDisease3","Yes")
            editor.commit()
            yesBtn.setBackgroundResource(R.drawable.white_custom_button_background)
            noBtn.setBackgroundResource(R.drawable.white_custombg_grayradius)
        }

        noBtn.setOnClickListener {
            answer = "No"

            sharedPreferences.edit()
            editor.putString("askDisease3","No")
            editor.commit()
            noBtn.setBackgroundResource(R.drawable.white_custom_button_background)
            yesBtn.setBackgroundResource(R.drawable.white_custombg_grayradius)
        }

        nextBtnDisease.setOnClickListener {
            val askDisease = sharedPreferences.getString("askDisease3", "None") // Get the updated value

            when (askDisease) {
                "Yes", "No" -> {
                    val intent = Intent(this, AskDisease4::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    finish()
                }
                else -> {
                    showToast("Please choose your option to proceed.")
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
            val intent = Intent(this, AskDisease4::class.java)
            alertDialog.dismiss()
            startActivity(intent)
            this.overridePendingTransition(0, 0)

        }

        // Show the AlertDialog
        alertDialog.show()
    }
}


