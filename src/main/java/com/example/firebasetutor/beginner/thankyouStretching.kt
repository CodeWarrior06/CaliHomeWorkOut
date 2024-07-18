package com.example.firebasetutor.beginner

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasetutor.databinding.ThankyouStretchingBinding

private lateinit var binding: ThankyouStretchingBinding

private lateinit var sharedPreferences: SharedPreferences
private lateinit var editor: SharedPreferences.Editor
private lateinit var intentTV : TextView
private  var workoutName1 :String? = ""
private  var repCount1 :String? = ""

private  var workoutName2 :String? = ""
private  var repCount2 :String? = ""

private  var workoutName3 :String? = ""
private  var repCount3 :String? = ""

private  var workoutName4 :String? = ""
private  var repCount4 :String? = ""

private  var workoutName5 :String? = ""
private  var repCount5 :String? = ""

private  var workoutName6 :String? = ""
private  var repCount6 :String? = ""
private var gif1 : Int = 0
private var gif2 : Int = 0

class thankyouStretching : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ThankyouStretchingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()






        intentTV = binding.nextBtnStretching
        textChanger()

        NextWorkoutPg()






    }

    private fun NextWorkoutPg() {

        val isWorkout = sharedPreferences.getString("isWorkout", "")
        val beginnerPushPullPhase = sharedPreferences.getInt("beginnerPushPullPhase", 0)
        intentTV.setOnClickListener {

            editor.putString("workoutVisit", "No")
            editor.commit()
            Log.d("FuckingTYStretchingPage", "what is Workout pref is $isWorkout.")
            Log.d("FuckingTYStretchingPage", "what is beginner Workout pref is $beginnerPushPullPhase.")
            if (isWorkout == "PushPull"){
                val intent = Intent(this, beginnerPushPullD1W1::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                finish()

            }
            else if(isWorkout == "LegCore"){
                val intent = Intent(this, beginnerLegCoreD1W1::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                finish()

            }






        }

    }


    private fun textChanger(){
        val isBeginnerSchedView = sharedPreferences.getString("isBeginnerSchedView", "")
        val isIntermediateSchedView = sharedPreferences.getString("isIntermediateSchedView", "")
        val beginnerPushPullPhase = sharedPreferences.getInt("beginnerPushPullPhase", 0)
        val beginnerLegCorePhase = sharedPreferences.getInt("beginnerLegCorePhase", 0)
        val intermediatePushPullPhase = sharedPreferences.getInt("intermediatePushPullPhase", 0)
        val intermediateLegCorePhase = sharedPreferences.getInt("intermediateLegCorePhase", 0)
        val isWorkout = sharedPreferences.getString("isWorkout", "")
        if(isBeginnerSchedView == "Yes"){

            if(isWorkout == "PushPull"){
                editor = sharedPreferences.edit()
                when(beginnerPushPullPhase){
                    0 -> beginnerPushPullPhase1Text()
                    1 -> beginnerPushPullPhase2Text()
                    2 -> beginnerPushPullPhase3Text()
                    3 -> beginnerPushPullPhase4Text()
                    4 -> beginnerPushPullPhase5Text()
                    5 -> beginnerPushPullPhase6Text()
                    6 -> beginnerPushPullPhase7Text()
                    7 -> beginnerPushPullPhase8Text()
                    8 -> beginnerPushPullPhase9Text()
                    9 -> beginnerPushPullPhase10Text()
                    10 -> beginnerPushPullPhase11Text()
                    11 -> beginnerPushPullPhase12Text()
                    else -> beginnerPushPullPhase12Text()
                }
                editor.putString("workoutName1", workoutName1)
                editor.putString("workoutName2", workoutName2)

                editor.putString("repCount1", repCount1)
                editor.putString("repCount2", repCount2)
                editor.commit()



                Log.d("ThankyouStretching", "isBeginnerSched set is $isBeginnerSchedView.")
                Log.d("ThankyouStretching", "sWorkout set is $isWorkout.")
                Log.d("ThankyouStretching", "beginnerPushPullPhase number set is $beginnerPushPullPhase.")

            } else if(isWorkout == "LegCore"){

                    editor = sharedPreferences.edit()
                    when(beginnerLegCorePhase){
                        0 -> beginnerLegCorePhase1Text()
                        1 -> beginnerLegCorePhase2Text()
                        2 -> beginnerLegCorePhase3Text()
                        3 -> beginnerLegCorePhase4Text()
                        4 -> beginnerLegCorePhase5Text()
                        5 -> beginnerLegCorePhase6Text()
                        else -> beginnerLegCorePhase6Text()

                    }
                    editor.putString("workoutName1", workoutName1)
                    editor.putString("repCount1", repCount1)


                    editor.putString("workoutName2", workoutName2)
                    editor.putString("repCount2", repCount2)

                    editor.putString("workoutName3", workoutName3)
                    editor.putString("repCount3", repCount3)

                    editor.putString("workoutName4", workoutName4)
                   editor.putString("repCount4", repCount4) //to be continees

                    editor.putString("workoutName5", workoutName5)
                    editor.putString("repCount5", repCount5)

                    editor.putString("workoutName6", workoutName6)
                    editor.putString("repCount6", repCount6)


                    editor.commit()
                    // Intent to next page



            }

        }
        else if(isIntermediateSchedView == "Yes") {

            if (isWorkout == "PushPull") {
                editor = sharedPreferences.edit()

                when (intermediatePushPullPhase) {
                    0 -> intermediatePushPullPhase1Text()
                    1 -> intermediatePushPullPhase2Text()
                    2 -> intermediatePushPullPhase3Text()
                    3 -> intermediatePushPullPhase4Text()
                    4 -> intermediatePushPullPhase5Text()
                    5 -> intermediatePushPullPhase6Text()
                    6 -> intermediatePushPullPhase7Text()
                    7 -> intermediatePushPullPhase8Text()
                    8 -> intermediatePushPullPhase9Text()
                    9 -> intermediatePushPullPhase10Text()
                    10 -> intermediatePushPullPhase11Text()
                    11 -> intermediatePushPullPhase12Text()
                    else -> intermediatePushPullPhase12Text()
                }
                editor.putString("workoutName1", workoutName1)
                editor.putString("workoutName2", workoutName2)

                editor.putString("repCount1", repCount1)
                editor.putString("repCount2", repCount2)
                editor.commit()
                // Intent to next page
            }

            else if(isWorkout == "LegCore"){
                editor = sharedPreferences.edit()
                when(intermediateLegCorePhase){
                    0 -> intermediateLegCorePhase1Text()
                    1 -> intermediateLegCorePhase2Text()
                    2 -> intermediateLegCorePhase3Text()
                    3 -> intermediateLegCorePhase4Text()
                    4 -> intermediateLegCorePhase5Text()
                    5 -> intermediateLegCorePhase6Text()
                    else -> intermediateLegCorePhase6Text()}
                editor.putString("workoutName1", workoutName1)
                editor.putString("repCount1", repCount1)


                editor.putString("workoutName2", workoutName2)
                editor.putString("repCount2", repCount2)

                editor.putString("workoutName3", workoutName3)
                editor.putString("repCount3", repCount3)

                editor.putString("workoutName4", workoutName4)
                editor.putString("repCount4", repCount4)

                editor.putString("workoutName5", workoutName5)
                editor.putString("repCount5", repCount5)

                editor.putString("workoutName6", workoutName6)
                editor.putString("repCount6", repCount6)


                editor.commit()

                 // Intent to next page




            }


        }



    }


    private fun beginnerPushPullPhase1Text(){


        workoutName1 = "Knee Push Up"
        repCount1 = "10 - 15 reps"
        workoutName2 = "Australian Pull Ups"
        repCount2 = "8 - 12 reps"

        Log.d("ThankyouStretching", "WprkoutName 1 set is $workoutName1.")
        Log.d("ThankyouStretching", "Repcount number 1 set is $repCount1.")
        Log.d("ThankyouStretching", "WprkoutName 2  set is $workoutName2.")
        Log.d("ThankyouStretching", "Repcount number 2 set is $repCount2.")

    }
    private fun beginnerPushPullPhase2Text(){
        workoutName1 = "Knee Push Up"
        repCount1 = "15 - 20 reps"
        workoutName2 = "Australian Pull Ups"
        repCount2 = "12 - 15 reps reps"

    }
    private fun beginnerPushPullPhase3Text(){
        workoutName1 = "Knee Push Up"
        repCount1 = "20 - 25 reps"
        workoutName2 = "Australian Pull Ups"
        repCount2 = "15 - 18 reps"

    }
    private fun beginnerPushPullPhase4Text(){
        workoutName1 = "Knee Push Up"
        repCount1 = "30 reps to failure"
        workoutName2 = "Australian Pull Ups"
        repCount2 = "18 - 25 reps"

    }
    private fun beginnerPushPullPhase5Text(){
        //Second Phase
        workoutName1 = "Inclined Push Up"
        repCount1 = "10 - 15 reps"
        workoutName2 = "Dead Hangs"
        repCount2 = "20 - 30 sec"

    }
    private fun beginnerPushPullPhase6Text(){
        workoutName1 = "Inclined Push Up"
        repCount1 = "15 - 20 reps"
        workoutName2 = "Dead Hangs"
        repCount2 = "30 - 40 sec"

    }
    private fun beginnerPushPullPhase7Text(){
        workoutName1 = "Inclined Push Up"
        repCount1 = "20 - 25 reps"
        workoutName2 = "Dead Hangs"
        repCount2 = "40 - 50 sec"

    }
    private fun beginnerPushPullPhase8Text(){
        workoutName1 = "Inclined Push Up"
        repCount1 = "30 reps to failure"
        workoutName2 = "Dead Hangs"
        repCount2 = "50 - 60 sec"

    }
    private fun beginnerPushPullPhase9Text(){
        //#3rd Phase
        workoutName1 = "Push Up"
        repCount1 = "10 - 15 reps"
        workoutName2 = "Band Assisted Pull Ups"
        repCount2 = "8 - 12 reps"


    }
    private fun beginnerPushPullPhase10Text(){
        workoutName1 = "Push Up"
        repCount1 = "15 - 20 reps"
        workoutName2 = "Band Assisted Pull Ups"
        repCount2 = "12 - 15 reps"

    }
    private fun beginnerPushPullPhase11Text(){
        workoutName1 = "Push Up"
        repCount1 = "20 - 25 reps"
        workoutName2 = "Band Assisted Pull Ups"
        repCount2 = "15 - 18 reps"

    }
    private fun beginnerPushPullPhase12Text(){
        workoutName1 = "Push Up"
        repCount1 = "30 reps to failure"
        workoutName2 = "Band Assisted Pull Ups"
        repCount2 = "20 reps to failure"

    }


    //INTERMEDIATE PUSH PULL


    private fun intermediatePushPullPhase1Text(){


        workoutName1 = "Diamond Push Up"
        repCount1 = "8 - 12 reps"
        workoutName2 = "Pull Ups"
        repCount2 = "8 - 12 reps"

        Log.d("ThankyouStretching", "WprkoutName 1 set is $workoutName1.")
        Log.d("ThankyouStretching", "Repcount number 1 set is $repCount1.")
        Log.d("ThankyouStretching", "WprkoutName 2  set is $workoutName2.")
        Log.d("ThankyouStretching", "Repcount number 2 set is $repCount2.")

    }
    private fun intermediatePushPullPhase2Text(){
        workoutName1 = "Diamond Push Up"
        repCount1 = "12 - 14 reps"
        workoutName2 = "Pull Ups"
        repCount2 = "12 - 14 reps"

    }
    private fun intermediatePushPullPhase3Text(){
        workoutName1 = "Diamond Push Up"
        repCount1 = "14 - 16 reps"
        workoutName2 = "Pull Ups"
        repCount2 = "14 - 16 reps"

    }
    private fun intermediatePushPullPhase4Text(){
        workoutName1 = "Diamond Push Up"
        repCount1 = "16 reps to failure"
        workoutName2 = "Pull Ups"
        repCount2 = "16 reps to failure"

    }
    private fun intermediatePushPullPhase5Text(){
        //Second Phase
        workoutName1 = "Archer Push Up"
        repCount1 = "10 - 15 reps"
        workoutName2 = "Wide Grip Pull Ups"
        repCount2 = "8 - 12 reps"

    }
    private fun intermediatePushPullPhase6Text(){
        workoutName1 = "Archer Push Up"
        repCount1 = "15 - 20 reps"
        workoutName2 = "Wide Grip Pull Ups"
        repCount2 = "12 - 14 reps"

    }
    private fun intermediatePushPullPhase7Text(){
        workoutName1 = "Archer Push Up"
        repCount1 = "20 - 25 reps"
        workoutName2 = "Wide Grip Pull Ups"
        repCount2 = "14 - 16 reps"

    }
    private fun intermediatePushPullPhase8Text(){
        workoutName1 = "Archer Push Up"
        repCount1 = "30 reps to failure"
        workoutName2 = "Wide Grip Pull Ups"
        repCount2 = "16 reps to failure"

    }
    private fun intermediatePushPullPhase9Text(){
        //#3rd Phase
        workoutName1 = "Pseudo Planche Push Up"
        repCount1 = "10 - 15 reps"
        workoutName2 = "Muscle Pull Ups"
        repCount2 = "8 - 12 reps"


    }
    private fun intermediatePushPullPhase10Text(){
        workoutName1 = "Pseudo Planche Push Up"
        repCount1 = "15 - 20 reps"
        workoutName2 = "Muscle Pull Ups"
        repCount2 = "12 - 14 reps"

    }
    private fun intermediatePushPullPhase11Text(){
        workoutName1 = "Pseudo Planche Push Up"
        repCount1 = "20 - 25 reps"
        workoutName2 = "Muscle Pull Ups"
        repCount2 = "14 - 16 reps"

    }
    private fun intermediatePushPullPhase12Text(){
        workoutName1 = "Pseudo Planche Push Up"
        repCount1 = "30 reps to failure"
        workoutName2 = "Muscle Pull Ups"
        repCount2 = "16 reps to failure"

    }

    //BEGINNER LEG CORE
    private fun beginnerLegCorePhase1Text(){
        workoutName1 = "Plank"
        repCount1 = "20 - 30 sec"
        workoutName2 = "Bicycle Crunches"
        repCount2 = "8 - 12 reps"
        workoutName3 = "Russian Twists"
        repCount3 = "8 - 12 reps"

        workoutName4 = "Squats"
        repCount4 = "8 - 12 reps"
        workoutName5 = "Wall Sits"
        repCount5 = "20 - 30 sec"
        workoutName6= "Lunges"
        repCount6 = "8 - 12 reps"
    }

    private fun beginnerLegCorePhase2Text(){
        workoutName1 = "Plank"
        repCount1 = "30 - 40 sec"
        workoutName2 = "Bicycle Crunches"
        repCount2 = "12 - 15 reps"
        workoutName3 = "Russian Twists"
        repCount3 = "12 - 15 reps"

        workoutName4 = "Squats"
        repCount4 = "12 - 15 reps"
        workoutName5 = "Wall Sits"
        repCount5 = "30 - 40 sec"
        workoutName6= "Lunges"
        repCount6 = "12 - 15 reps"
    }

    private fun beginnerLegCorePhase3Text(){
        workoutName1 = "Plank"
        repCount1 = "40 - 60 sec"
        workoutName2 = "Bicycle Crunches"
        repCount2 = "15 - 18 reps"
        workoutName3 = "Russian Twists"
        repCount3 = "15 - 18 reps"

        workoutName4 = "Squats"
        repCount4 = "15 - 18 reps"
        workoutName5 = "Wall Sits"
        repCount5 = "40 - 50 sec"
        workoutName6= "Lunges"
        repCount6 = "15 - 18 reps"
    }

    private fun beginnerLegCorePhase4Text(){
        workoutName1 = "Plank"
        repCount1 = "90 - 100 sec"
        workoutName2 = "Bicycle Crunches"
        repCount2 = "23 - 28 reps"
        workoutName3 = "Russian Twists"
        repCount3 = "21 - 25 reps"

        workoutName4 = "Squats"
        repCount4 = "21 - 25 reps"
        workoutName5 = "Wall Sits"
        repCount5 = "60 - 70 sec"
        workoutName6= "Lunges"
        repCount6 = "21 - 25 reps"
    }


    private fun beginnerLegCorePhase5Text(){
        workoutName1 = "Plank"
        repCount1 = "90 - 100 sec"
        workoutName2 = "Bicycle Crunches"
        repCount2 = "23 - 28 reps"
        workoutName3 = "Russian Twists"
        repCount3 = "21 - 25 reps"

        workoutName4 = "Squats"
        repCount4 = "21 - 25 reps"
        workoutName5 = "Wall Sits"
        repCount5 = "60 - 70 sec"
        workoutName6= "Lunges"
        repCount6 = "21 - 25 reps"
    }

    private fun beginnerLegCorePhase6Text(){
        workoutName1 = "Plank"
        repCount1 = "120 sec to failure"
        workoutName2 = "Bicycle Crunches"
        repCount2 = "30 reps to failure"
        workoutName3 = "Russian Twists"
        repCount3 = "25 reps to failure"
        //LEGCORE 5 to be fixed and chang workout
        workoutName4 = "Squats"
        repCount4 = "25 reps to failure"
        workoutName5 = "Wall Sits"
        repCount5 = "75 sec to failure"
        workoutName6= "Lunges"
        repCount6 = "25 reps to failure"
    }


    //Intermediate LEG CORE


    private fun intermediateLegCorePhase1Text(){
        workoutName1 = "Hollow Body Hold"
        repCount1 = "20 - 30 sec"
        workoutName2 = "Side Plank"
        repCount2 = "10 - 20 sec"
        workoutName3 = "Hanging Leg Raise"
        repCount3 = "6 - 8 reps"

        workoutName4 = "Pistol Squats"
        repCount4 = "5 - 8 reps"
        workoutName5 = "Squat Jumps"
        repCount5 = "8 - 12 reps"
        workoutName6= "Single Leg Romanian Dead Lift"
        repCount6 = "9 - 12 reps"
    }

    private fun intermediateLegCorePhase2Text(){
        workoutName1 = "Hollow Body Hold"
        repCount1 = "30 - 40 sec"
        workoutName2 = "Side Plank"
        repCount2 = "20 - 30 sec"
        workoutName3 = "Hanging Leg Raise"
        repCount3 = "8 - 12 reps"

        workoutName4 = "Pistol Squats"
        repCount4 = "8 - 12 reps"
        workoutName5 = "Squat Jumps"
        repCount5 = "12 - 15 reps"
        workoutName6= "Single Leg Romanian Dead Lift"
        repCount6 = "12 - 15 reps"
    }

    private fun intermediateLegCorePhase3Text(){
        workoutName1 = "Hollow Body Hold"
        repCount1 = "40 - 60 sec"
        workoutName2 = "Side Plank"
        repCount2 = "30 - 40 sec"
        workoutName3 = "Hanging Leg Raise"
        repCount3 = "12 - 15 reps"

        workoutName4 = "Pistol Squats"
        repCount4 = "8 - 12 reps"
        workoutName5 = "Squat Jumps"
        repCount5 = "15 - 18 reps"
        workoutName6= "Single Leg Romanian Dead Lift"
        repCount6 = "15 - 18 reps"
    }

    private fun intermediateLegCorePhase4Text(){
        workoutName1 = "Hollow Body Hold"
        repCount1 = "60 - 80 sec"
        workoutName2 = "Side Plank"
        repCount2 = "40 - 50 sec"
        workoutName3 = "Hanging Leg Raise"
        repCount3 = "15 - 18 reps"

        workoutName4 = "Pistol Squats"
        repCount4 = "15 - 18 reps"
        workoutName5 = "Squat Jumps"
        repCount5 = "18 - 21 reps"
        workoutName6= "Single Leg Romanian Dead Lift"
        repCount6 = "18 - 21 reps"
    }


    private fun intermediateLegCorePhase5Text(){
        workoutName1 = "Hollow Body Hold"
        repCount1 = "90 - 100 sec"
        workoutName2 = "Side Plank"
        repCount2 = "50 - 60 sec"
        workoutName3 = "Hanging Leg Raise"
        repCount3 = "18 - 21 reps"

        workoutName4 = "Pistol Squats"
        repCount4 = "18 - 21 reps"
        workoutName5 = "Squat Jumps"
        repCount5 = "21 - 25 reps"
        workoutName6= "Single Leg Romanian Dead Lift"
        repCount6 = "21 - 25 reps"
    }

    private fun intermediateLegCorePhase6Text(){
        workoutName1 = "Hollow Body Hold"
        repCount1 = "120 sec to failure"
        workoutName2 = "Side Plank"
        repCount2 = "60 sec to failure"
        workoutName3 = "Hanging Leg Raise"
        repCount3 = "21 - 25 reps"

        workoutName4 = "Pistol Squats"
        repCount4 = "21 - 25 reps"
        workoutName5 = "Squat Jumps"
        repCount5 = "25 reps to failure"
        workoutName6= "Single Leg Romanian Dead Lift"
        repCount6 = "25 reps to failure"
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        // Do nothing
    }






}