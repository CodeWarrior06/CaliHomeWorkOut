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
import com.example.firebasetutor.databinding.ActivityIntermediateAfterSchedBinding

class intermediateAfterSched : AppCompatActivity() {

    private lateinit var binding: ActivityIntermediateAfterSchedBinding
    private lateinit var buttonQuit: TextView
    private lateinit var buttonBack: TextView
    private lateinit var buttonReset: TextView
    private lateinit var  sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntermediateAfterSchedBinding.inflate(layoutInflater)
        setContentView(binding.root)


        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()


        buttonQuit = binding.buttonQuit
        buttonBack = binding.buttonBack
        buttonReset = binding.resetWorkout

        /*buttonQuit.setOnClickListener{
            ifQuit()

        }
        buttonBack.setOnClickListener{
            val intent = Intent(this, WorkoutPg::class.java)
            startActivity(intent)

        }
        */

        buttonReset.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Set background color when touched
                    buttonReset.setBackgroundResource(R.drawable.whitebackground_blackstroke_1px)
                }
                MotionEvent.ACTION_UP -> {
                    // Reset background color after touch is released
                    buttonReset.setBackgroundResource(R.drawable.dialog_custom_before_press_white)
                    // Handle Yes button click

                    ifReset()
                }
            }
            true
        }



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
       /* val username1 = sharedPreferences.getString("isIntermediateSchedView", "")
        val username2 = sharedPreferences.getString("isBeginnerSchedView", "")
       Log.d("AnongValue", "Intermediate askschedview is $username1.")
        Log.d("AnongValue", "Beginner askschedview is $username2.")
        */
    }



    private fun ifReset(){
        var updateAskSchedView = ""
        var intermediateAskSched = ""
        var intermediatePushPullPhase = 0
        var intermediateLegCorePhase =  0
        var intermediateProgressBar =  0
        var beginnerProgressBar =  0

        var currentWeek =  0
        var workoutLevel =  0
        var isBeginnerSchedView =  ""
        var isIntermediateSchedView =  ""

        editor.putString("isIntermediateSchedView", updateAskSchedView)
        editor.putString("intermediateAskSched", intermediateAskSched)
        editor.putInt("intermediatePushPullPhase", intermediatePushPullPhase)
        editor.putInt("intermediateCorePhase",intermediateLegCorePhase)
        editor.putInt("intermediateProgressBar", intermediateProgressBar)
        editor.putInt("beginnerProgressBar", beginnerProgressBar)
        editor.putInt("currentWeek", currentWeek)
        editor.putInt("workoutLevel", workoutLevel)

        editor.putString("isBeginnerSchedView", "")
        editor.putString("isIntermediateSchedView", "")

        intermediateLegCorePhase = sharedPreferences.getInt("intermediateLegCorePhase", 0)

        editor.commit()
        Log.d("ValueSaBeginnerAfterSched", "beginnerPushPullPhase is $intermediatePushPullPhase.")
        Log.d("ValueSaBeginnerAfterSched", "beginnerLegCorePhase is $intermediateLegCorePhase.")
        Log.d("ValueSaBeginnerAfterSched", "beginnerProgressBar is $intermediateProgressBar.")
        Log.d("ValueSaBeginnerAfterSched", "currentWeeek is $currentWeek.")
        Log.d("ValueSaBeginnerAfterSched", "workoutLevel is $workoutLevel.")
        Log.d("ValueSaBeginnerAfterSched", "isBeginnerSchedView is $workoutLevel.")
        Log.d("ValueSaBeginnerAfterSched", "isIntermediateSchedView is $workoutLevel.")

        Log.d("ValueSaBeginnerAfterSched", "isIntermediateSchedView is $isBeginnerSchedView.")
        Log.d("ValueSaBeginnerAfterSched", "isIntermediateSchedView is $isIntermediateSchedView.")

        val intent = Intent(this, Dashboard::class.java)
        startActivity(intent)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        finish()
    }














    private fun ifQuit(){
        var updateAskSchedView = ""
        var intermediateAskSched = ""
        var intermediatePushPullPhase = 0
        var intermediateLegCorePhase =  0
        var intermediateProgressBar =  0
        var currentWeek =  0
        var workoutLevel =  0

        editor.putString("isIntermediateSchedView", updateAskSchedView)
        editor.putString("intermediateAskSched", intermediateAskSched)
        editor.putInt("intermediatePushPullPhase", intermediatePushPullPhase)
        editor.putInt("intermediateCorePhase",intermediateLegCorePhase)
        editor.putInt("intermediateProgressBar", intermediateProgressBar)
        editor.putInt("currentWeek", currentWeek)
        editor.putInt("workoutLevel", workoutLevel)

        editor.commit()
        Log.d("ValueSaBeginnerAfterSched", "beginnerPushPullPhase is $intermediatePushPullPhase.")
        Log.d("ValueSaBeginnerAfterSched", "beginnerLegCorePhase is $intermediateLegCorePhase.")
        Log.d("ValueSaBeginnerAfterSched", "beginnerProgressBar is $intermediateProgressBar.")
        Log.d("ValueSaBeginnerAfterSched", "currentWeeek is $currentWeek.")
        Log.d("ValueSaBeginnerAfterSched", "workoutLevel is $workoutLevel.")

        val intent = Intent(this, Dashboard::class.java)
        startActivity(intent)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        finish()
    }


}