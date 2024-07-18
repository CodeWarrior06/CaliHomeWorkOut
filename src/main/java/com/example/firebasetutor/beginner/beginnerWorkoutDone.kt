package com.example.firebasetutor.beginner

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasetutor.Dashboard
import com.example.firebasetutor.databinding.ActivityBeginnerWorkoutDoneBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class beginnerWorkoutDone : AppCompatActivity() {

    private lateinit var binding: ActivityBeginnerWorkoutDoneBinding
    private lateinit var YesBtn : Button
    private lateinit var NoBtn : Button
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private var isBeginnerSchedView: String? = null
    private var isIntermediateSchedView: String? = null
    private var beginnerPushPullPhase: Int = 0
    private var beginnerLegCorePhase: Int = 0
    private var intermediatePushPullPhase: Int = 0
    private var intermediateLegCorePhase: Int = 0

    private var isWorkout: String? = null
    private var workoutVisit: String? = null
    private var  stretchingPhase: Int = 0
    private var pushPullPhaseUpdate = 0
    private var legCorePhaseUpdate = 0
    private var workoutDifficulty = 0
    private var beginnerProgressBar = 0
    private var intermediateProgressBar = 0
    private var  isToastShowing = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBeginnerWorkoutDoneBinding.inflate(layoutInflater)
        setContentView(binding.root)


        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        workoutOptResetPref() //Reset Week Pref to update

        isBeginnerSchedView = sharedPreferences.getString("isBeginnerSchedView", "")
        isIntermediateSchedView = sharedPreferences.getString("isIntermediateSchedView", "")

        intermediatePushPullPhase = sharedPreferences.getInt("intermediatePushPullPhase", 0)
        intermediateLegCorePhase = sharedPreferences.getInt("intermediateLegCorePhase", 0)
        isWorkout = sharedPreferences.getString("isWorkout", "")
        stretchingPhase = sharedPreferences.getInt("stretchingPhase", 0)
        workoutVisit = sharedPreferences.getString("workoutVisit", "")
        beginnerPushPullPhase = sharedPreferences.getInt("beginnerPushPullPhase", 0)
        beginnerLegCorePhase = sharedPreferences.getInt("beginnerLegCorePhase", 0)
        intermediatePushPullPhase = sharedPreferences.getInt("intermediatePushPullPhase", 0)
        intermediateLegCorePhase = sharedPreferences.getInt("intermediateLegCorePhase", 0)
        beginnerProgressBar = sharedPreferences.getInt("beginnerProgressBar", 0)
        intermediateProgressBar = sharedPreferences.getInt("intermediateProgressBar", 0)



        YesBtn = binding.yesBtn
        NoBtn = binding.noBtn

        YesBtn.setOnClickListener{
            workoutOptSharedPref() // Current Week Shared pref
            if(beginnerPushPullPhase >= 12 || beginnerLegCorePhase >= 6){
                workoutPhaseReached()
                changeWorkoutPhase()
                progressBarComputation()
                sharePrefReset()


            }
            else{
                changeWorkoutPhase()
                progressBarComputation()
                sharePrefReset()
            }

                val intent = Intent(this, Dashboard::class.java)
                startActivity(intent)
                finish()



        }

        NoBtn.setOnClickListener {
            workoutOptSharedPref()
            progressBarComputation()
            sharePrefReset()
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
        }
    }
    private fun workoutOptResetPref(){
        var currentWeek = sharedPreferences.getInt("currentWeek", 0)
        editor.putInt("currentWeek", 0)
        editor.commit()
        var week1 = sharedPreferences.getInt("currentWeek", 0)
        Log.d("ResetCurrentWeek", "Current Week  is $week1.")
    }

    private fun workoutOptSharedPref(){
        var currentWeek = sharedPreferences.getInt("currentWeek", 0)
        val today = SimpleDateFormat("EEEE", Locale.getDefault()).format(Date())
        editor = sharedPreferences.edit()

        when (today) {
            "Monday" ->   editor.putInt("currentWeek", 1)
            "Tuesday" ->  editor.putInt("currentWeek", 2)
            "Wednesday" ->editor.putInt("currentWeek", 3)
            "Thursday" -> editor.putInt("currentWeek", 4)
            "Friday" ->   editor.putInt("currentWeek", 5)
            "Saturday" -> editor.putInt("currentWeek", 6)
            "Sunday" ->   editor.putInt("currentWeek", 7)
        }
        editor.commit()
        var week = sharedPreferences.getInt("currentWeek", 0)
        Log.d("whatIsCurrentWeek", "Current Week  is $week.")


    }

    private fun workoutPhaseReached(){
        var toastLong: String = ""





        //When intermediate Workout still not Unlocked
        if(isBeginnerSchedView == "Yes")
        {
            if(beginnerPushPullPhase >= 12 && beginnerLegCorePhase >= 6){

                unlockIntermediate() //notr done

            }

            else if(beginnerPushPullPhase >= 12){
                toastLong = "Push & Pull Workout Finished. Complete Leg & Core to Unlock Intermediate Level"
                Toast.makeText(applicationContext, toastLong, Toast.LENGTH_LONG).show()
            }
            else if(beginnerLegCorePhase >= 6){
                toastLong = "Leg & Core Workout Finished. Complete Push & Pull to Unlock Intermediate Level"
                Toast.makeText(applicationContext, toastLong, Toast.LENGTH_LONG).show()
            }

        }

        else if(isIntermediateSchedView == "Yes"){
            if(beginnerProgressBar >= 100 && intermediateProgressBar >= 100){
               // showToast("Congratulation, You clear all the Worko   ut Difficulty")

            }

        }



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

    private fun unlockIntermediate(){


            workoutDifficulty = 2
            editor = sharedPreferences.edit()
            editor.putInt("workoutLevel", workoutDifficulty)
            editor.commit()

            var workoutLevel  = sharedPreferences.getInt("workoutLevel", 0)

            Log.d("Workout_Difficulty", "Value is $workoutLevel.")


    }



    private fun progressBarComputation(){

        beginnerPushPullPhase = sharedPreferences.getInt("beginnerPushPullPhase", 0)
        beginnerLegCorePhase = sharedPreferences.getInt("beginnerLegCorePhase", 0)
        intermediatePushPullPhase = sharedPreferences.getInt("intermediatePushPullPhase", 0)
        intermediateLegCorePhase = sharedPreferences.getInt("intermediateLegCorePhase", 0)

        if(isBeginnerSchedView == "Yes"){
            editor = sharedPreferences.edit() //bwisit kang editor ka ikaw lang palaaaaaa!!!!!!!
            val beginnerValue = (beginnerPushPullPhase +  beginnerLegCorePhase)
            val beginnerPercentage = (beginnerValue.toFloat() / 18) * 100

            editor.putInt("beginnerProgressBar", beginnerPercentage.toInt())
            editor.commit()


        }
        else if(isIntermediateSchedView == "Yes"){
            editor = sharedPreferences.edit()
            val intermediateValue = (intermediatePushPullPhase +  intermediateLegCorePhase)
            val intermediatePercentage = (intermediateValue.toFloat() / 18) * 100

            editor.putInt("intermediateProgressBar", intermediatePercentage.toInt())
            editor.commit()


        }


    }


    private fun changeWorkoutPhase(){


        if(isBeginnerSchedView == "Yes"){
            beginnerPushPullPhase = sharedPreferences.getInt("beginnerPushPullPhase", 0)

             if(isWorkout == "PushPull"){
                    if(beginnerPushPullPhase <= 11){
                        editor = sharedPreferences.edit()
                        pushPullPhaseUpdate = (beginnerPushPullPhase + 1)
                        editor.putInt("beginnerPushPullPhase", pushPullPhaseUpdate)
                        editor.commit()
                       //edited cause i started pushpull  phase at 0 not 1
                        //none


                    }
             }

             else if(isWorkout == "LegCore"){

                 if(beginnerLegCorePhase <= 5){
                    editor = sharedPreferences.edit()
                     legCorePhaseUpdate = (beginnerLegCorePhase + 1)
                     editor.putInt("beginnerLegCorePhase", legCorePhaseUpdate)
                     editor.commit()

                 } else{
                     //none
                 }
             }

        }

        if(isIntermediateSchedView == "Yes"){

            if(isWorkout == "PushPull"){
                if(intermediatePushPullPhase <= 11){
                    editor = sharedPreferences.edit()
                    pushPullPhaseUpdate = (intermediatePushPullPhase + 1)
                    editor.putInt("intermediatePushPullPhase", pushPullPhaseUpdate)
                    editor.commit()

                }
            }

            else if(isWorkout == "LegCore"){

                if(intermediateLegCorePhase <= 5){
                    editor = sharedPreferences.edit()
                    legCorePhaseUpdate = (intermediateLegCorePhase + 1)
                    editor.putInt("intermediateLegCorePhase", legCorePhaseUpdate)
                    editor.commit()

                } else{
                    //none
                }
            }



        }

    }
    private fun sharePrefReset(){
        editor = sharedPreferences.edit()
        editor.putString("Set", "")

        //Reset on what workout the user pick if leg or push
        editor.putString("isWorkout", "")
        editor.commit()
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        // Do nothing
    }

}