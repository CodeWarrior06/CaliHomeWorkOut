package com.example.firebasetutor.workoutModule

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MotionEvent
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasetutor.Dashboard
import com.example.firebasetutor.R
import com.example.firebasetutor.databinding.ActivityBeginnerAskSchedBinding

class BeginnerAskSched : AppCompatActivity() {


    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private lateinit var binding: ActivityBeginnerAskSchedBinding
    private lateinit var firstOpt : TextView
    private lateinit var secondOpt : LinearLayout
    private lateinit var thirdOpt : TextView
    private lateinit var nextBtnAskSched : LinearLayout

    private lateinit var backBtn : TextView

    private var isFirstClicked = false
    private var isSecondClicked = false
    private var isThirdClicked = false
    private var isNextBtncClicked = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBeginnerAskSchedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        firstOpt = binding.schedOpt1
        secondOpt = binding.schedOpt2
        thirdOpt = binding.schedOpt3
        nextBtnAskSched = binding.nextBtnAskSched
        backBtn = binding.backarrowlogo

        firstOpt.setBackgroundResource(R.drawable.custom_button_background_pressed)
        secondOpt.setBackgroundResource(R.drawable.custom_button_background_pressed)
        thirdOpt.setBackgroundResource(R.drawable.custom_button_background_pressed)
        nextBtnAskSched.setBackgroundResource(R.drawable.custom_button_background_pressed)



        OnclickEffect()


    }


    private fun OnclickEffect(){
        backBtn.setOnClickListener{

            val intent = Intent(this, WorkoutPg::class.java)
            startActivity(intent)
            finish()


        }
        firstOpt.setOnClickListener{
            isFirstClicked = true
            isSecondClicked = false
            isThirdClicked = false

            firstOpt.setBackgroundResource(R.drawable.whitebackground_blackstroke_2px)
            secondOpt.setBackgroundResource(R.drawable.custom_button_background_pressed)
            thirdOpt.setBackgroundResource(R.drawable.custom_button_background_pressed)


        }

        secondOpt.setOnClickListener{
            isFirstClicked = false
            isSecondClicked = true
            isThirdClicked = false

            firstOpt.setBackgroundResource(R.drawable.custom_button_background_pressed)
            secondOpt.setBackgroundResource(R.drawable.whitebackground_blackstroke_2px)
            thirdOpt.setBackgroundResource(R.drawable.custom_button_background_pressed)


        }

        thirdOpt.setOnClickListener{
            isFirstClicked = false
            isSecondClicked = false
            isThirdClicked = true

            firstOpt.setBackgroundResource(R.drawable.custom_button_background_pressed)
            secondOpt.setBackgroundResource(R.drawable.custom_button_background_pressed)
            thirdOpt.setBackgroundResource(R.drawable.whitebackground_blackstroke_2px)


        }


       nextBtnAskSched.setOnClickListener{




           if(!isFirstClicked && !isSecondClicked && !isThirdClicked){
               isNextBtncClicked = false
               nextBtnAskSched.setBackgroundResource(R.drawable.custom_button_background_pressed)
               Toast.makeText(this, "Please Choose your Workout Schedule", Toast.LENGTH_SHORT).show()
               return@setOnClickListener
           }
           else{
               isNextBtncClicked = true
               nextBtnAskSched.setBackgroundResource(R.drawable.whitebackground_blackstroke_2px)
               Toast.makeText(applicationContext, "Option Selected", Toast.LENGTH_SHORT).show()
               //Ask the button clicked

               if(isFirstClicked){
                   var askSched = "1"
                   editor.putString("beginnerAskSched", askSched)
                   editor.commit()
               }else if(isSecondClicked){
                   var askSched = "2"
                   editor.putString("beginnerAskSched", askSched)
                   editor.commit()
               }else if(isThirdClicked){
                   var askSched = "3"
                   editor.putString("beginnerAskSched", askSched)
                   editor.commit()
               }

               var askSchedView = "Yes"
               editor.putString("isBeginnerSchedView", askSchedView)
               editor.commit()

               showYesNoDialog()




           }






        }
    }

    private fun showYesNoDialog() {
        // Inflate the custom dialog layout
        val dialogView = layoutInflater.inflate(R.layout.dialog_custom, null)

        // Create an AlertDialog builder and set the custom view
        val builder = AlertDialog.Builder(this)
            .setView(dialogView)

        // Create the AlertDialog object
        val alertDialog = builder.create()

        // Find the Yes and No buttons in the dialog layout
        val buttonYes = dialogView.findViewById<TextView>(R.id.buttonYes)
        val buttonNo = dialogView.findViewById<TextView>(R.id.buttonNo)

        // Set background color for the Yes button
        buttonYes.setBackgroundResource(R.drawable.dialog_custom_before_press_white)

        // Set background drawable resource for the No button
        buttonNo.setBackgroundResource(R.drawable.dialog_custom_before_press_white)

        // Set click listeners for the Yes and No buttons
        buttonYes.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Set background color when touched
                    buttonYes.setBackgroundResource(R.drawable.whitebackground_blackstroke_1px)
                }
                MotionEvent.ACTION_UP -> {
                    // Reset background color after touch is released
                    buttonYes.setBackgroundResource(R.drawable.dialog_custom_before_press_white)
                    // Handle Yes button click
                    alertDialog.dismiss()
                    val intent = Intent(this, Dashboard::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            true
        }

        buttonNo.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Set background color when touched
                    buttonNo.setBackgroundResource(R.drawable.whitebackground_blackstroke_1px)
                }
                MotionEvent.ACTION_UP -> {
                    // Reset background color after touch is released
                    buttonNo.setBackgroundResource(R.drawable.dialog_custom_before_press_white)
                    // Handle Yes button click
                    alertDialog.dismiss()

                }
            }
            true
        }

        // Show the AlertDialog
        alertDialog.show()
    }


}