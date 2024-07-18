package com.example.firebasetutor.beginner

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasetutor.R
import com.example.firebasetutor.databinding.ActivityBeginnerPhase1Binding

@Suppress("DEPRECATION")
class BeginnerPhase1 : AppCompatActivity() {

    private lateinit var binding: ActivityBeginnerPhase1Binding
    private lateinit var startWorkoutBtn: TextView

    private lateinit var workoutTitle : TextView
    private lateinit var categoryText1 : TextView
    private lateinit var categoryText2 : TextView

    private lateinit var pushPullDescription : TextView  //Description of workout category

    private lateinit var warmup1 : TextView  //warm up is up to 1 to 6
    private lateinit var warmup2 : TextView
    private lateinit var warmup3 : TextView
    private lateinit var warmup4 : TextView
    private lateinit var warmup5 : TextView
    private lateinit var warmup6 : TextView
    private lateinit var warmup7 : TextView
    private lateinit var warmup8 : TextView
    private lateinit var warmup9 : TextView


    private lateinit var lc1 : TextView
    private lateinit var lc2 : TextView
    private lateinit var lc3 : TextView
    private lateinit var lc4 : TextView
    private lateinit var lc5 : TextView
    private lateinit var lc6 : TextView

    private lateinit var workout1 : TextView
    private lateinit var workout2 : TextView //Knee Push Up 10 -15 reps ( 3 - set )
    private lateinit var workout3 : TextView
    private lateinit var workout4 : TextView
    private lateinit var workout5 : TextView
    private lateinit var workout6 : TextView



    private var workoutName1 = ""
    private var workoutName2 = ""
    private var workoutName3 = ""
    private var workoutName4 = ""
    private var workoutName5 = ""
    private var workoutName6 = ""
    private var numberOfSet = "( 3 - set )"

    private var isClicked = false
    private var isToastShowing = false

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBeginnerPhase1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()



        startWorkoutBtn = binding.startWorkout
        var selectedLightCardio = sharedPreferences.getInt("selectedLightCardio", 0)

        var beginnerProgressBar = sharedPreferences.getInt("beginnerProgressBar", 0)
        var beginnerPushPullPhase = sharedPreferences.getInt("beginnerPushPullPhase", 0)
        var beginnerLegCorePhase = sharedPreferences.getInt("beginnerLegCorePhase", 0)

        Log.d("HOYYY_ANO_IKAW", "beginnerPushPullPhase is $beginnerPushPullPhase.")
        Log.d("HOYYY_ANO_IKAW", "beginnerProgressBar is $beginnerProgressBar.")
        Log.d("HOYYY_ANO_IKAW", "beginnerLegCorePhase is $beginnerLegCorePhase.")



        workoutTitle = binding.workoutTitle
        categoryText1 = binding.categoryText1
        categoryText2 = binding.categoryText2
        pushPullDescription = binding.pushPullDescription

        lc1 = binding.lightCardio1
        lc2 = binding.lightCardio2
        lc3 = binding.lightCardio3
        lc4 = binding.lightCardio4
        lc5 = binding.lightCardio5




        warmup1 = binding.warmup1
        warmup2= binding.warmup2
        warmup3= binding.warmup3
        warmup4= binding.warmup4
        warmup5= binding.warmup5
        warmup6= binding.warmup6
        warmup7= binding.warmup7
        warmup8= binding.warmup8
        warmup9= binding.warmup9


        workout1 = binding.workout1
        workout2 = binding.workout2
        workout3 = binding.workout3
        workout4 = binding.workout4
        workout5 = binding.workout5
        workout6 = binding.workout6

        hoverLightCardio()  // Cardio Select of Light Cardio Gif





        val isWorkout = sharedPreferences.getString("isWorkout", "")
        val isBeginnerSchedView = sharedPreferences.getString("isBeginnerSchedView", "")
        val isIntermediateSchedView = sharedPreferences.getString("isIntermediateSchedView", "")
        val containerDefault = R.drawable.whitebackground_borderradius

       // val beginnerPushPullPhase = sharedPreferences.getInt("beginnerPushPullPhase", 0)
      //  val beginnerLegCorePhase = sharedPreferences.getInt("beginnerLegCorePhase", 0)

        val intermediatePushPullPhase = sharedPreferences.getInt("intermediatePushPullPhase", 0)
        val intermediateLegCorePhase = sharedPreferences.getInt("intermediateLegCorePhase", 0)

        Log.d("AnongValue", "beginnerPushPullPhase set is $beginnerPushPullPhase.")
        Log.d("AnongValue", "isWorkout is $isWorkout")
        Log.d("AnongValue", "isBeginnerSchedView is $isBeginnerSchedView")
        if(isBeginnerSchedView == "Yes"){

            if(isWorkout == "PushPull"){
                workoutTVLegCoreHider()
                updatedBeginnerPushPullText()

                when(beginnerPushPullPhase){
                    0 -> beginnerPushPullPhase1Text()
                    1 -> beginnerPushPullPhase2Text()
                    2 -> beginnerPushPullPhase3Text()
                    3 -> beginnerPushPullPhase4Text()       //Continuation ng Stretching to add a countdown while happening
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


            }
            else if(isWorkout == "LegCore"){

                workoutTVLegCoreViewer()
                updatedBeginnerLegCoreText()
                when(beginnerLegCorePhase){
                    0 -> beginnerLegCorePhase1Text()
                    1 -> beginnerLegCorePhase2Text()
                    2 -> beginnerLegCorePhase3Text()
                    3 -> beginnerLegCorePhase4Text()       //Continuation ng Stretching to add a countdown while happening
                    4 -> beginnerLegCorePhase5Text()
                    5 -> beginnerLegCorePhase6Text()
                    else -> beginnerLegCorePhase6Text()

                }


            }
            startWorkoutBtn.setOnClickListener {

                if(isClicked == true){
                    var intent1 = Intent(this, stretchingPg::class.java)     //Next should work in the WARM UPS Need to have a countdown then proceed to the next warm up and so on
                    startActivity(intent1)
                    overridePendingTransition(0, 0)
                    finish()
                }else{
                    showToast("Choose one light cardio first  to proceed...")
                }
            }
        }

        else if(isIntermediateSchedView == "Yes"){

            if(isWorkout == "PushPull"){
                updatedIntermediatePushPullText()
                workoutTVLegCoreHider()
                when(intermediatePushPullPhase){
                    0 -> intermediatePushPullPhase1Text()
                    1 -> intermediatePushPullPhase2Text()
                    2 -> intermediatePushPullPhase3Text()
                    3 -> intermediatePushPullPhase4Text() //Continuation ng Stretching to add a countdown while happening
                    4 -> intermediatePushPullPhase5Text()
                    5 -> intermediatePushPullPhase6Text()
                    6 -> intermediatePushPullPhase7Text()
                    7 -> intermediatePushPullPhase8Text()
                    8 -> intermediatePushPullPhase9Text()
                    9 -> intermediatePushPullPhase10Text()       //Continuation ng Stretching to add a countdown while happening
                    10 -> intermediatePushPullPhase11Text()
                    11 -> intermediatePushPullPhase12Text()
                    else -> intermediatePushPullPhase12Text()




                }
            }
            else if(isWorkout == "LegCore"){

                updatedIntermediateLegCoreText()
                workoutTVLegCoreViewer()
                when(intermediateLegCorePhase){
                    0 -> intermediateLegCorePhase1Text()
                    1 -> intermediateLegCorePhase2Text()
                    2 -> intermediateLegCorePhase3Text()
                    3 -> intermediateLegCorePhase4Text()       //Continuation ng Stretching to add a countdown while happening
                    4 -> intermediateLegCorePhase5Text()
                    5 -> intermediateLegCorePhase6Text()
                    else -> intermediateLegCorePhase6Text()

                }

            }
            startWorkoutBtn.setOnClickListener {



                if(isClicked == true){
                    var intent1 = Intent(this, stretchingPg::class.java)     //Next should work in the WARM UPS Need to have a countdown then proceed to the next warm up and so on
                    startActivity(intent1)
                    overridePendingTransition(0, 0)
                    finish()
                }else{
                    showToast("Choose Light Cardio Exercise to Proceed")
                }


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

    private fun hoverLightCardio(){
        var selectedLightCardio = 0

        lc1.setOnClickListener {
           selectedLightCardio = 1
           isClicked = true
           sharedPreferences.edit()
           editor.putInt("selectedLightCardio",selectedLightCardio)
           editor.commit()

           lc1.setBackgroundResource(R.drawable.white_background_borderradius_verysmall)
           lc2.setBackgroundResource(R.drawable.whitebackground_borderradius)
           lc3.setBackgroundResource(R.drawable.whitebackground_borderradius)
           lc4.setBackgroundResource(R.drawable.whitebackground_borderradius)
           lc5.setBackgroundResource(R.drawable.whitebackground_borderradius)
       }
        lc2.setOnClickListener {
            selectedLightCardio = 2
            isClicked = true
            sharedPreferences.edit()
            editor.putInt("selectedLightCardio",selectedLightCardio)
            editor.commit()
            lc2.setBackgroundResource(R.drawable.white_background_borderradius_verysmall)
            lc1.setBackgroundResource(R.drawable.whitebackground_borderradius)
            lc3.setBackgroundResource(R.drawable.whitebackground_borderradius)
            lc4.setBackgroundResource(R.drawable.whitebackground_borderradius)
            lc5.setBackgroundResource(R.drawable.whitebackground_borderradius)
        }
        lc3.setOnClickListener {
            selectedLightCardio =3

            isClicked = true
            sharedPreferences.edit()
            editor.putInt("selectedLightCardio",selectedLightCardio)
            editor.commit()
            lc3.setBackgroundResource(R.drawable.white_background_borderradius_verysmall)
            lc1.setBackgroundResource(R.drawable.whitebackground_borderradius)
            lc2.setBackgroundResource(R.drawable.whitebackground_borderradius)
            lc4.setBackgroundResource(R.drawable.whitebackground_borderradius)
            lc5.setBackgroundResource(R.drawable.whitebackground_borderradius)
        }
        lc4.setOnClickListener{
            selectedLightCardio = 4

            isClicked = true
            sharedPreferences.edit()
            editor.putInt("selectedLightCardio",selectedLightCardio)
            editor.commit()
            lc4.setBackgroundResource(R.drawable.white_background_borderradius_verysmall)
            lc1.setBackgroundResource(R.drawable.whitebackground_borderradius)
            lc2.setBackgroundResource(R.drawable.whitebackground_borderradius)
            lc3.setBackgroundResource(R.drawable.whitebackground_borderradius)
            lc5.setBackgroundResource(R.drawable.whitebackground_borderradius)
        }
        lc5.setOnClickListener{
            selectedLightCardio = 5

            isClicked = true
            sharedPreferences.edit()
            editor.putInt("selectedLightCardio",selectedLightCardio)
            editor.commit()
            lc5.setBackgroundResource(R.drawable.white_background_borderradius_verysmall)
            lc1.setBackgroundResource(R.drawable.whitebackground_borderradius)
            lc2.setBackgroundResource(R.drawable.whitebackground_borderradius)
            lc3.setBackgroundResource(R.drawable.whitebackground_borderradius)
            lc4.setBackgroundResource(R.drawable.whitebackground_borderradius)
        }

        Log.d("Light Cardio Value", "Light CARDIO number set is $selectedLightCardio")







    }



    private fun workoutTVLegCoreHider(){
        workout3.visibility = View.GONE
        workout4.visibility = View.GONE
        workout5.visibility = View.GONE
        workout6.visibility = View.GONE
    }

    private fun workoutTVLegCoreViewer(){
        workout3.visibility = View.VISIBLE
        workout4.visibility = View.VISIBLE
        workout5.visibility = View.VISIBLE
        workout6.visibility = View.VISIBLE
    }

    private fun updatedBeginnerPushPullText(){
        workoutTitle.setText("Beginner Calisthenic")
        categoryText1.text = "Push"
        categoryText2.text = "Pull"
        pushPullDescription.text = " Crush this intense Push/Pull workout using just your bodyweight! Target your entire upper body with pushing and pulling exercises, perfect for building strength and endurance. This quick and efficient routine requires no equipment and can be done anywhere, anytime. Let's get sweating!"
        warmup1.text = "Arm Circle (Forward) 30sec"
        warmup2.text = "Arm Circle (Backward) 30sec"
        warmup3.text = "Arm Raise Stretching 30sec"
        warmup4.text = "Front Wrist Forward 30sec"
        warmup5.text = "Front Wrist Rotation 30sec"
        warmup6.text = "Shoulder Circle (Forward) 30sec"
        warmup7.text = "Shoulder Circle (Backward) 30sec"
        warmup8.text = "Side Arm Stretching (To Right) 30sec"
        warmup9.text = "Side Arm Stretching (To Left) 30sec"
    }

    private fun updatedBeginnerLegCoreText(){
        workoutTitle.setText("Beginner Calisthenic")
        categoryText1.text = "Leg"
        categoryText2.text = "Core"
        pushPullDescription.text = "Calisthenics leg and core workouts build strong legs and core stability using just your bodyweight. This improves athleticism, balance, and everyday strength. No equipment needed, making it perfect for on-the-go workouts."
        warmup1.text = "Arm Circle (Forward) 30sec"
        warmup2.text = "Arm Circle (Backward) 30sec"
        warmup3.text = "Arm Raise Stretching 30sec"
        warmup4.text = "Front Wrist Forward 30sec"
        warmup5.text = "Front Wrist Rotation 30sec"
        warmup6.text = "Shoulder Circle (Forward) 30sec"
        warmup7.text = "Shoulder Circle (Backward) 30sec"
        warmup8.text = "Side Arm Stretching (To Right) 30sec"
        warmup9.text = "Side Arm Stretching (To Left) 30sec"
    }

    private fun updatedIntermediatePushPullText(){
        workoutTitle.setText("Intermediate Calisthenic")
        categoryText1.text = "Push"
        categoryText2.text = "Pull"
        pushPullDescription.text = "Crush this intense Push/Pull workout using just your bodyweight! Target your entire upper body with pushing and pulling exercises, perfect for building strength and endurance. This quick and efficient routine requires no equipment and can be done anywhere, anytime. Let's get sweating!"

        warmup1.text = "Arm Circle (Forward) 30sec"
        warmup2.text = "Arm Circle (Backward) 30sec"
        warmup3.text = "Arm Raise Stretching 30sec"
        warmup4.text = "Front Wrist Forward 30sec"
        warmup5.text = "Front Wrist Rotation 30sec"
        warmup6.text = "Shoulder Circle (Forward) 30sec"
        warmup7.text = "Shoulder Circle (Backward) 30sec"
        warmup8.text = "Side Arm Stretching (To Right) 30sec"
        warmup9.text = "Side Arm Stretching (To Left) 30sec"
    }

    private fun updatedIntermediateLegCoreText(){
        workoutTitle.setText("Intermediate Calisthenic")
        categoryText1.text = "Leg"
        categoryText2.text = "Core"
        pushPullDescription.text = "Calisthenics leg and core workouts build strong legs and core stability using just your bodyweight. This improves athleticism, balance, and everyday strength. No equipment needed, making it perfect for on-the-go workouts."
        warmup1.text = "Arm Circle (Forward) 30sec"
        warmup2.text = "Arm Circle (Backward) 30sec"
        warmup3.text = "Arm Raise Stretching 30sec"
        warmup4.text = "Front Wrist Forward 30sec"
        warmup5.text = "Front Wrist Rotation 30sec"
        warmup6.text = "Shoulder Circle (Forward) 30sec"
        warmup7.text = "Shoulder Circle (Backward) 30sec"
        warmup8.text = "Side Arm Stretching (To Right) 30sec"
        warmup9.text = "Side Arm Stretching (To Left) 30sec"
    }


    private fun beginnerPushPullPhase1Text(){
        workout1.text = "Knee Push Up 10-15 reps  ( 3 - set )"
        workout2.text = "Australian Pull Up 8-12 reps ( 3 - set )"
    }
    private fun beginnerPushPullPhase2Text(){
        workout1.text = "Knee Push Up 15-20 reps ( 3 - set )"
        workout2.text = "Australian Pull Up 12-15 reps ( 3 - set )"
    }
    private fun beginnerPushPullPhase3Text(){
        workout1.text = "Knee Push Up 20-25 reps ( 3 - set )"
        workout2.text = "Australian Pull Up 15-18 reps ( 3 - set )"
    }
    private fun beginnerPushPullPhase4Text(){
        workout1.text = "Knee Push Up 30 reps to failure ( 3 - set )"
        workout2.text = "Australian Pull Up 18-25 reps ( 3 - set )"
    }
    private fun beginnerPushPullPhase5Text(){
        workout1.text = "Inclined Push Up 10-15 reps ( 3 - set )"
        workout2.text = "Dead Hang 20-30 sec ( 3 - set )"
    }
    private fun beginnerPushPullPhase6Text(){
        workout1.text = "Inclined Push Up 15-20 reps ( 3 - set )"
        workout2.text = "Dead Hang 30-40 sec ( 3 - set )"
    }
    private fun beginnerPushPullPhase7Text(){
        workout1.text = "Inclined Push Up 20-25 reps ( 3 - set )"
        workout2.text = "Dead Hang 40-50 sec ( 3 - set )"
    }
    private fun beginnerPushPullPhase8Text(){
        workout1.text = "Inclined Push Up 30 reps to failure ( 3 - set )"
        workout2.text = "Dead Hang 50-60 sec ( 3 - set )"
    }
    private fun beginnerPushPullPhase9Text(){
        workout1.text = "Push Up 10-15 reps ( 3 - set )"
        workout2.text = "Band Assisted Pull Up 8 - 12 reps( 3 - set )"
    }
    private fun beginnerPushPullPhase10Text(){
        workout1.text = "Push Up 15-20 reps ( 3 - set )"
        workout2.text = "Band Assisted Pull Up 12 - 15 reps ( 3 - set )"
    }
    private fun beginnerPushPullPhase11Text(){
        workout1.text = "Push Up 20-25 reps ( 3 - set )"
        workout2.text = "Band Assisted Pull Up 15 - 18 reps ( 3 - set )"
    }
    private fun beginnerPushPullPhase12Text(){
        workout1.text = "Push Up 30 reps to failure ( 3 - set )"
        workout2.text = "Band Assisted Pull Up 20 reps to failure ( 3 - set )"
    }


    // LEG CORE BEGINNNER

    private fun beginnerLegCorePhase1Text(){
        workout1.text = "Plank 20 - 30 sec ( 3 - set )"
        workout2.text = "Bicycle Crunches 8 - 12 reps ( 3 - set )"
        workout3.text = "Russian Twist 8 - 12 reps ( 3 - set )"
        workout4.text = "Squats 8 - 12 reps ( 3 - set )"
        workout5.text = "Wall Sit 20 - 30 sec ( 3 - set )"
        workout6.text = "Lunges 8 - 12 reps ( 3 - set )"
    }

    private fun beginnerLegCorePhase2Text(){
        workout1.text = "Plank 30 - 40 sec ( 3 - set )"
        workout2.text = "Bicycle Crunches 12 - 15 reps ( 3 - set )"
        workout3.text = "Russian Twist 12 - 15 reps ( 3 - set )"
        workout4.text = "Squats 12 - 15 reps ( 3 - set )"
        workout5.text = "Wall Sit 30 - 40 sec ( 3 - set )"
        workout6.text = "Lunges 12 - 15 reps ( 3 - set )"
    }

    private fun beginnerLegCorePhase3Text(){
        workout1.text = "Plank 40 - 60 sec ( 3 - set )"
        workout2.text = "Bicycle Crunches 15 - 18 reps ( 3 - set )"
        workout3.text = "Russian Twist 15 - 18 reps ( 3 - set )"
        workout4.text = "Squats 15 - 18 reps ( 3 - set )"
        workout5.text = "Wall Sit 40 - 50 sec ( 3 - set )"
        workout6.text = "Lunges 15 - 18 reps ( 3 - set )"
    }

    private fun beginnerLegCorePhase4Text(){
        workout1.text = "Plank 60 - 80 sec ( 3 - set )"
        workout2.text = "Bicycle Crunches 18 - 23 reps ( 3 - set )"
        workout3.text = "Russian Twist 18 - 21 reps ( 3 - set )"
        workout4.text = "Squats 18 - 21 reps ( 3 - set )"
        workout5.text = "Wall Sit 50 - 60 sec ( 3 - set )"
        workout6.text = "Lunges 18 - 21 reps ( 3 - set )"
    }

    private fun beginnerLegCorePhase5Text(){
        workout1.text = "Plank 90 - 100 sec ( 3 - set )"
        workout2.text = "Bicycle Crunches 23 - 28 reps ( 3 - set )"
        workout3.text = "Russian Twist 21 - 25 reps ( 3 - set )"
        workout4.text = "Squats 21 - 25 reps ( 3 - set )"
        workout5.text = "Wall Sit 60 - 70 sec ( 3 - set )"
        workout6.text = "Lunges 21 - 25 reps ( 3 - set )"
    }

    private fun beginnerLegCorePhase6Text(){
        workout1.text = "Plank 120 sec to failure ( 3 - set )"
        workout2.text = "Bicycle Crunches 30 reps to failure ( 3 - set )"
        workout3.text = "Russian Twist 25 reps to failure ( 3 - set )"
        workout4.text = "Squats 25 reps to failure ( 3 - set )"
        workout5.text = "Wall Sit 75 sec to failure ( 3 - set )"
        workout6.text = "Lunges 25 reps to failure ( 3 - set )"
    }


    // PUSH PULL INTERMEDIATE
    private fun intermediatePushPullPhase1Text(){
        workout1.text = "Diamond Push Up 8 - 12 reps " + numberOfSet
        workout2.text = "Pull Ups 8 - 12 reps"  + numberOfSet
    }

    private fun intermediatePushPullPhase2Text(){
        workout1.text = "Diamond Push Up 12 - 14 reps " + numberOfSet
        workout2.text = "Pull Ups 12 - 14 reps "  + numberOfSet
    }

    private fun intermediatePushPullPhase3Text(){
        workout1.text = "Diamond Push Up 14 - 16 reps " + numberOfSet
        workout2.text = "Pull Ups 14 - 16 reps "  + numberOfSet
    }

    private fun intermediatePushPullPhase4Text(){
        workout1.text = "Diamond Push Up 16 reps to failure " + numberOfSet
        workout2.text = "Pull Ups 16 reps to failure "  + numberOfSet
    }

    private fun intermediatePushPullPhase5Text(){
        workout1.text = "Archer Push Up 10 - 15 reps " + numberOfSet
        workout2.text = "Wide Grip Pull Ups 8 - 12 reps"  + numberOfSet
    }

    private fun intermediatePushPullPhase6Text(){
        workout1.text = "Archer Push Up 15 - 20 reps " + numberOfSet
        workout2.text = "Wide Grip Pull Ups 12 - 14 reps"  + numberOfSet
    }

    private fun intermediatePushPullPhase7Text(){
        workout1.text = "Archer Push Up 20 - 25 reps " + numberOfSet
        workout2.text = "Wide Grip Pull Ups 14 - 16 reps"  + numberOfSet
    }

    private fun intermediatePushPullPhase8Text(){
        workout1.text = "Archer Push Up 30 reps to failure " + numberOfSet
        workout2.text = "Wide Grip Pull Ups 16 reps to failure "  + numberOfSet
    }

    private fun intermediatePushPullPhase9Text(){
        workout1.text = "Pseudo Planche Push Up 10 - 15 reps " + numberOfSet
        workout2.text = "Muscle Pull Ups 8 - 12 reps "  + numberOfSet
    }

    private fun intermediatePushPullPhase10Text(){
        workout1.text = "Pseudo Planche Push Up 15 - 20 reps " + numberOfSet
        workout2.text = "Muscle Pull Ups 12 - 14 reps "  + numberOfSet
    }


    private fun intermediatePushPullPhase11Text(){
        workout1.text = "Pseudo Planche Push Up 20 - 25 reps " + numberOfSet
        workout2.text = "Muscle Pull Ups 14 - 16 reps "  + numberOfSet
    }

    private fun intermediatePushPullPhase12Text(){
        workout1.text = "Pseudo Planche Push Up 30 reps to failure " + numberOfSet
        workout2.text = "Muscle Pull Ups 16 reps to failure "  + numberOfSet
    }


    //INTERMEDIATE LEG CORE
    //to be fixed in workout
    //not given the workout specific yet
    private fun intermediateLegCorePhase1Text(){
        workout1.text = "Hollow Body Hold 20 - 30 sec "+ numberOfSet
        workout2.text = "Side Plank 10 - 20 sec " + numberOfSet
        workout3.text = "Hanging Leg Raise 5 - 8 reps "+ numberOfSet
        workout4.text = "Pistol Squats 5 - 8 reps "+ numberOfSet
        workout5.text = "Squat Jumps 8 - 12 reps " + numberOfSet
        workout6.text = "Single Leg Romanian Deadlifts 8 - 12 reps "+ numberOfSet
    }

    private fun intermediateLegCorePhase2Text(){
        workout1.text = "Hollow Body Hold 30 - 40 sec "+ numberOfSet
        workout2.text = "Side Plank 20 - 30 sec " + numberOfSet
        workout3.text = "Hanging Leg Raise 8 - 12 reps "+ numberOfSet
        workout4.text = "Pistol Squats 8 - 12 reps "+ numberOfSet
        workout5.text = "Squat Jumps 12 - 15 reps " + numberOfSet
        workout6.text = "Single Leg Romanian Deadlifts 12 - 15 reps "+ numberOfSet
    }

    private fun intermediateLegCorePhase3Text(){
        workout1.text = "Hollow Body Hold 40 - 60 sec "+ numberOfSet
        workout2.text = "Side Plank 30 - 40 sec " + numberOfSet
        workout3.text = "Hanging Leg Raise 12 - 15 reps "+ numberOfSet
        workout4.text = "Pistol Squats 12 - 15 reps "+ numberOfSet
        workout5.text = "Squat Jumps 15 - 18 reps " + numberOfSet
        workout6.text = "Single Leg Romanian Deadlifts 15 - 18 reps "+ numberOfSet
    }

    private fun intermediateLegCorePhase4Text(){
        workout1.text = "Hollow Body Hold 60 - 80 sec "+ numberOfSet
        workout2.text = "Side Plank 40 - 50 sec " + numberOfSet
        workout3.text = "Hanging Leg Raise 15 - 18 reps "+ numberOfSet
        workout4.text = "Pistol Squats 15 - 18 reps "+ numberOfSet
        workout5.text = "Squat Jumps 18 - 21 reps " + numberOfSet
        workout6.text = "Single Leg Romanian Deadlifts 18 - 21 reps "+ numberOfSet
    }

    private fun intermediateLegCorePhase5Text(){
        workout1.text = "Hollow Body Hold 90 - 100 sec "+ numberOfSet
        workout2.text = "Side Plank 50 - 60 sec " + numberOfSet
        workout3.text = "Hanging Leg Raise 18 - 21 reps "+ numberOfSet
        workout4.text = "Pistol Squats 18 - 21 reps "+ numberOfSet
        workout5.text = "Squat Jumps 21 - 25 reps " + numberOfSet
        workout6.text = "Single Leg Romanian Deadlifts 21 - 25 reps "+ numberOfSet
    }

    private fun intermediateLegCorePhase6Text(){
        workout1.text = "Hollow Body Hold 120 sec to failure "+ numberOfSet
        workout2.text = "Side Plank 60 sec to failure " + numberOfSet
        workout3.text = "Hanging Leg Raise 21 - 25 reps "+ numberOfSet
        workout4.text = "Pistol Squats 21 - 25 reps "+ numberOfSet
        workout5.text = "Squat Jumps 25 reps to failure " + numberOfSet
        workout6.text = "Single Leg Romanian Deadlifts 25 reps to failure "+ numberOfSet
    }

}