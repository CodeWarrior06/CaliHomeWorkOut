package com.example.firebasetutor.workoutModule

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasetutor.Dashboard
import com.example.firebasetutor.R
import com.example.firebasetutor.databinding.ActivityBeginnerAfterSchedBinding

class BeginnerAfterSched : AppCompatActivity() {

    private lateinit var binding: ActivityBeginnerAfterSchedBinding
    private lateinit var buttonQuit: TextView
    private lateinit var buttonBack: TextView
    private lateinit var  sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBeginnerAfterSchedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()


        buttonQuit = binding.buttonQuit
        buttonBack = binding.buttonBack

       /* buttonQuit.setOnClickListener{
            ifQuit()

        }
        buttonBack.setOnClickListener{
            val intent = Intent(this, WorkoutPg::class.java)
            startActivity(intent)
        }*/


        buttonQuit.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Set background color when touched
                    buttonQuit.setBackgroundResource(R.drawable.whitebackground_blackstroke_1px)
                }
                MotionEvent.ACTION_UP -> {
                    // Reset background color after touch is released
                    buttonQuit.setBackgroundResource(R.drawable.dialog_custom_before_press_white)
                    // Handle Yes button click

                    ifQuit()
                }
            }
            true
        }

        buttonBack.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Set background color when touched
                    buttonBack.setBackgroundResource(R.drawable.whitebackground_blackstroke_1px)
                }
                MotionEvent.ACTION_UP -> {
                    // Reset background color after touch is released
                    buttonBack.setBackgroundResource(R.drawable.dialog_custom_before_press_white)
                    // Handle Yes button click
                    val intent = Intent(this, WorkoutPg::class.java)
                    startActivity(intent)
                }
            }
            true
        }

    }

    private fun ifQuit(){
        var updateAskSchedView = ""
        var beginnerAskSched = ""
        var beginnerPushPullPhase = 0
        var beginnerLegCorePhase =  0
        var beginnerProgressBar =  0
        var currentWeek =  0
        var workoutLevel =  0

        editor.putString("isBeginnerSchedView", updateAskSchedView)
        editor.putString("beginnerAskSched", beginnerAskSched)
        editor.putInt("beginnerPushPullPhase", beginnerPushPullPhase)
        editor.putInt("beginnerLegCorePhase", beginnerLegCorePhase)
        editor.putInt("beginnerProgressBar", beginnerProgressBar)
        editor.putInt("currentWeek", currentWeek)
        editor.putInt("workoutLevel", workoutLevel)

        editor.commit()
        Log.d("ValueSaBeginnerAfterSched", "beginnerPushPullPhase is $beginnerPushPullPhase.")
        Log.d("ValueSaBeginnerAfterSched", "beginnerLegCorePhase is $beginnerLegCorePhase.")
        Log.d("ValueSaBeginnerAfterSched", "beginnerProgressBar is $beginnerProgressBar.")
        Log.d("ValueSaBeginnerAfterSched", "currentWeeek is $currentWeek.")

        val intent = Intent(this, Dashboard::class.java)
        startActivity(intent)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        finish()
    }

}