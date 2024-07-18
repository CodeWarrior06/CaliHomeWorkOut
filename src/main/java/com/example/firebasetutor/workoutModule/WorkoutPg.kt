package com.example.firebasetutor.workoutModule

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.firebasetutor.Dashboard
import com.example.firebasetutor.MacrosPg
import com.example.firebasetutor.NutritionPg
import com.example.firebasetutor.ProfilePg
import com.example.firebasetutor.R
import com.example.firebasetutor.databinding.ActivityWorkoutPgBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

@Suppress("DEPRECATION")
class WorkoutPg : AppCompatActivity() {

    private lateinit var binding: ActivityWorkoutPgBinding
    private var isBeginnerClicked = false
    private var isIntermediateClicked = false
    private var isAdvancedClicked = false
    private lateinit var text1: EditText

    private lateinit var circleProgressBar: ProgressBar
    private lateinit var progressBtn : TextView

    private lateinit var beginnerOpt:LinearLayout
    private lateinit var intermediateOpt:LinearLayout

    private lateinit var auth: FirebaseAuth
    private lateinit var dbRef: DatabaseReference
    private lateinit var  sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private var isClickable = false
    private var isIntermediateSchedNotClickable = false
    private var isBeginnerSchedClickable = false
    private lateinit var progressText1: TextView
    private lateinit var progressText2: TextView

    private var beginnerPushPullPhase: Int = 0
    private var beginnerLegCorePhase: Int = 0
    private var beginnerProgressBar: Int = 0
    private var intermediateProgressBar: Int = 0

    private lateinit var routineImg: ImageView
    private lateinit var workoutImg: ImageView
    private lateinit var nutritionImg: ImageView
    private lateinit var macrosImg: ImageView
    private lateinit var profileImg: ImageView

    private lateinit var routineTV: TextView
    private lateinit var workoutTV: TextView
    private lateinit var macrosTV: TextView
    private lateinit var nutritionTV: TextView
    private lateinit var profileTV: TextView

    private var getBeginnerProgressBar: Int = 0
    private var isToastShowing: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutPgBinding.inflate(layoutInflater)
        setContentView(binding.root)


        auth = FirebaseAuth.getInstance()

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        var workoutLevel  = sharedPreferences.getInt("workoutLevel", 0)





        workoutProgress()



        beginnerOpt.setOnClickListener {
            if(beginnerProgressBar > 99 && beginnerProgressBar < 101){
                showToast("This workout Difficulty is Done, Please proceed to Intermediate Difficulty")
            }

            else{
                val nutritionalStatus = sharedPreferences.getString("nutritionalStatus", "")
                val nutritionalStatusOnline = sharedPreferences.getString("nutritionalStatusData", "")


                if(nutritionalStatus == "Obesity Class 1" || nutritionalStatusOnline  == "Obesity Class 1" ){
                    ifObese()
                }
                else if(nutritionalStatus == "Obesity Class 2" || nutritionalStatusOnline  == "Obesity Class 2" ){
                    ifObese()
                }
                else if(nutritionalStatus == "Obesity Class 3" || nutritionalStatusOnline  == "Obesity Class 3" ){
                    ifObese()
                }
                else{
                    val isBeginnerSchedView = sharedPreferences.getString("isBeginnerSchedView", "" )
                    val beginnerSched = isBeginnerSchedView.toString()
                    val intent = if (beginnerSched == "Yes") {
                        Intent(this, BeginnerAfterSched::class.java)
                    } else {
                        Intent(this, BeginnerAskSched::class.java)
                    }
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    overridePendingTransition(0, 0)

                }


            }



        }
        //Not Clickable if beginner sched on progress or not finish
        //if False


            intermediateOpt.setOnClickListener {


               if(beginnerProgressBar > 99 && beginnerProgressBar < 101){

                   editor = sharedPreferences.edit()
                   editor.putString("isBeginnerSchedView", "")//To disable Beginner Workout showed container since now its supppose tto be intermediate
                   editor.commit()

                      val nutritionalStatus = sharedPreferences.getString("nutritionalStatus", "")
                      val nutritionalStatusOnline = sharedPreferences.getString("nutritionalStatusData", "")

                       if(nutritionalStatus == "Obesity Class 1" || nutritionalStatusOnline  == "Obesity Class 1" ){
                           ifObese()
                       }

                       else if(nutritionalStatus == "Obesity Class 2" || nutritionalStatusOnline  == "Obesity Class 2" ){
                           ifObese()
                       }

                       else if(nutritionalStatus == "Obesity Class 3" || nutritionalStatusOnline  == "Obesity Class 3" ){
                           ifObese()
                       }
                   else{

                       val isIntermediateSchedView = sharedPreferences.getString("isIntermediateSchedView", "" )
                       val intermediateSched = isIntermediateSchedView.toString()
                        val intent = if (intermediateSched == "Yes") {
                            Intent(this, intermediateAfterSched::class.java)
                        } else {
                            Intent(this, IntermediateAskSched::class.java)
                        }

                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                           overridePendingTransition(0, 0)
                        finish()

                       }

                }

               else if(beginnerProgressBar < 100){

                   showToast("Please Finish Beginner Workout Calisthenic to Unlock...")

               }







            }






        footbar()
        // Initialize ProgressBar after the layout is inflated





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
    private fun ifBeginnerWorkoutDone(){
        beginnerProgressBar = sharedPreferences.getInt("beginnerProgressBar", 0)
        intermediateProgressBar = sharedPreferences.getInt("intermediateProgressBar", 0)

        if(beginnerProgressBar > 99 || beginnerProgressBar < 101){
            //To be continued, fix also the stretching gif since they are overflowing
        }
    }

    private fun workoutProgress(){

        beginnerOpt = binding.workoutContainer1
        intermediateOpt =binding.workoutContainer2

        beginnerProgressBar = sharedPreferences.getInt("beginnerProgressBar", 0)
        intermediateProgressBar = sharedPreferences.getInt("intermediateProgressBar", 0)

        /*

        var beginnerPushPullPhase = sharedPreferences.getInt("beginnerPushPullPhase", 0)
        var beginnerLegCorePhase = sharedPreferences.getInt("beginnerLegCorePhase", 0)
        var intermediatePushPullPhase = sharedPreferences.getInt("intermediatePushPullPhase", 0)
        var intermediateLegCorePhase = sharedPreferences.getInt("intermediateLegCorePhase", 0)
        Log.d("Ilan_Ang_Beginner_Progress_Bar", "beginnerProgress_Bar is $beginnerProgressBar.")
        Log.d("lan_Ang_Intermediate_Progress_Bar", "intermediateProgress_Bar is $intermediateProgressBar.")

        */

        var beginnerPBString = beginnerProgressBar.toString() + "%"
        var intermediatePBString = intermediateProgressBar.toString() + "%"



        progressText1 = binding.progressText1
        progressText2 = binding.progressText2


        progressText1.text = beginnerPBString
        progressText2.text = intermediatePBString



      //var getBeginnerAskSched1 = sharedPreferences.getString("getBeginnerAskSched", "")
    //  var getBeginnerProgressBar = sharedPreferences.getInt("BeginnerProgressBar", 0)
     // Log.d("ANONG_LAMAN_NG _SHAREDPREF!!!!!!!!", "BeginnerProgressBar is $getBeginnerProgressBar.")
      //Log.d("ANONG_LAMAN_NG _SHAREDPREF!!!!!!!!", "BeginnerAskSched is $getBeginnerAskSched1.")


        if(beginnerProgressBar != null){

            progressText1.text = beginnerPBString

            binding.circleProgressBar1.apply {
                progressMax = 100f
                setProgressWithAnimation(beginnerProgressBar.toFloat(), 1000)
                progressBarWidth = 6f
                backgroundProgressBarWidth = 6f
                progressBarColor = Color.RED
            }
        } else {

            progressText1.text = beginnerPBString

            binding.circleProgressBar1.apply {
                progressMax = 100f
                setProgressWithAnimation(beginnerProgressBar.toFloat(), 1000)
                progressBarWidth = 6f
                backgroundProgressBarWidth = 6f
                progressBarColor = Color.RED
            }
        }

        binding.circleProgressBar2.apply {
            progressMax = 100f
            setProgressWithAnimation(intermediateProgressBar.toFloat(), 1000)
            progressBarWidth = 6f
            backgroundProgressBarWidth = 6f
            progressBarColor = Color.RED
        }

    }








    private fun showProceedBackDialog() {
        // Inflate the custom dialog layout
        val ifObeseDialog = layoutInflater.inflate(R.layout.workout_if_obese_dialog, null)

        // Create an AlertDialog builder and set the custom view
        val builder = AlertDialog.Builder(this)
            .setView(ifObeseDialog)

        // Create the AlertDialog object
        val alertDialog = builder.create()

        // Find the Yes and No buttons in the dialog layout
        val buttonProceed = ifObeseDialog.findViewById<TextView>(R.id.buttonProceed)
        val buttonBack = ifObeseDialog.findViewById<TextView>(R.id.buttonBack)

        // Set background color for the Yes button
        buttonProceed.setBackgroundResource(R.drawable.warmup_greenbackground_borderadius20)

        // Set background drawable resource for the No button
        buttonBack.setBackgroundResource(R.drawable.warmup_yellowbackground_borderadius20)

        // Set click listeners for the Yes and No buttons
        buttonProceed.setOnClickListener {
            // Handle Yes button click

            alertDialog.dismiss()
            val intent = Intent(this, MacrosPg::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
            finish()

        }

        buttonBack.setOnClickListener {
            // Handle No button click

            alertDialog.dismiss()
        }

        // Show the AlertDialog
        alertDialog.show()
    }





    private fun ifObese(){
        val userUid = FirebaseAuth.getInstance().currentUser?.uid
        // Go to Routine page of Beginner
        val intent1 = Intent(this, BeginnerAskSched::class.java)
        val nutritionalStatus = sharedPreferences.getString("nutritionalStatus", "")
        val nutritionalStatusOnline = sharedPreferences.getString("nutritionalStatusData", "")
        val bmi = sharedPreferences.getString("bmi", "" )

        val bmiString = bmi.toString()
        val bmiFloat = bmiString?.toFloatOrNull() ?: 0.0f


        if(nutritionalStatus == "Obesity Class 1" || nutritionalStatusOnline == "Obesity Class 1"){
            showProceedBackDialog()
        } else if (nutritionalStatus =="Obesity Class 2" || nutritionalStatusOnline == "Obesity Class 2"){
            showProceedBackDialog()
        } else if(nutritionalStatus =="Obesity Class 3" || nutritionalStatusOnline == "Obesity Class 3"){
            showProceedBackDialog()
        } else if(bmiFloat >= 30){
            showProceedBackDialog()
        }


        else{
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent1)
            overridePendingTransition(0, 0)
            finish()
        }

    }




    private fun isClickable(){
        val isBeginnerSchedView = sharedPreferences.getString("isBeginnerSchedView", "" )
        val beginnerSched = isBeginnerSchedView.toString()
        if(beginnerSched == "Yes"){      //Not working or redirecting to the next page
            Toast.makeText(applicationContext, "This is already Clicked", Toast.LENGTH_SHORT).show()
            beginnerOpt.isClickable = false // This makes it not respond to click events
            beginnerOpt.isEnabled = false
            val intent = Intent(this, BeginnerAfterSched::class.java)
            overridePendingTransition(0, 0)
            startActivity(intent)
        }
    }


    private fun footbar() {
        routineImg = binding.routineImg
        workoutImg = binding.workoutImg
        nutritionImg = binding.nutritionImg
        macrosImg = binding.macrosImg
        profileImg = binding.profileImg

        routineTV = binding.routineTV
        workoutTV = binding.workoutTV
        nutritionTV = binding.nutritionTV
        macrosTV = binding.macrosTV
        profileTV = binding.profileTV




        routineImg.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Handle touch down event
                    // Load the colored drawable
                    val coloredDrawable1 =
                        ContextCompat.getDrawable(this, R.mipmap.routine_white_100dp)
                    // Change image to colored version when touched
                    routineTV.setTextColor(ContextCompat.getColor(this, R.color.white))
                    routineImg.setBackgroundResource(R.color.skyblue)
                    routineImg.setImageDrawable(coloredDrawable1)
                }

                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    // Handle touch up or cancel event
                    // Revert to default image when touch is released or cancelled
                    val coloredDrawable2 =
                        ContextCompat.getDrawable(this, R.mipmap.routine_white_100dp)
                    // Change image to colored version when touched
                    routineTV.setTextColor(ContextCompat.getColor(this, R.color.white))
                    routineImg.setBackgroundResource(R.color.skyblue)
                    routineImg.setImageDrawable(coloredDrawable2)
                    val intent = Intent(this, Dashboard::class.java)
                    val animation = ActivityOptions.makeCustomAnimation(
                        this,
                        R.anim.slide_in_right,
                        R.anim.slide_out_left

                    )

                   routineTV.setOnClickListener {
                        startActivity(intent, animation.toBundle())
                    }
                    startActivity(intent, animation.toBundle())
                    finish()


                }
            }
            true // Consume the touch event
        }



        workoutImg.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    val workout1 = ContextCompat.getDrawable(this, R.mipmap.workout_white_dumbbell100_foreground
                    )
                    workoutTV.setTextColor(ContextCompat.getColor(this, R.color.white))
                    workoutImg.setBackgroundResource(R.color.skyblue)
                    workoutImg.setImageDrawable(workout1)
                }

                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    val workout2 = ContextCompat.getDrawable(
                        this,
                        R.mipmap.workout_white_dumbbell100_foreground
                    )
                    workoutTV.setTextColor(ContextCompat.getColor(this, R.color.white))
                    workoutImg.setBackgroundResource(R.color.skyblue)
                    workoutImg.setImageDrawable(workout2)
                }
            }
            true // Consume the touch event
        }




        nutritionImg.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Handle touch down event
                    // Load the colored drawable
                    val nutrition1 = ContextCompat.getDrawable(this, R.mipmap.nutrition_white_100dp)
                    // Change image to colored version when touched
                    nutritionTV.setTextColor(ContextCompat.getColor(this, R.color.white))
                    nutritionImg.setBackgroundResource(R.color.skyblue)
                    nutritionImg.setImageDrawable(nutrition1)
                }

                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    // Handle touch up or cancel event
                    // Revert to default image when touch is released or cancelled
                    val nutrition2 = ContextCompat.getDrawable(this, R.mipmap.nutrition_white_100dp)
                    // Change image to colored version when touched
                    nutritionTV.setTextColor(ContextCompat.getColor(this, R.color.white))
                    nutritionImg.setBackgroundResource(R.color.skyblue)
                    nutritionImg.setImageDrawable(nutrition2)
                    val intent = Intent(this, NutritionPg::class.java)
                    val animation = ActivityOptions.makeCustomAnimation(
                        this,
                        R.anim.slide_in_left,
                        R.anim.slide_out_right
                    )


                    nutritionTV.setOnClickListener {
                        startActivity(intent, animation.toBundle())
                    }
                    startActivity(intent, animation.toBundle())
                    finish()
                }
            }
            true // Consume the touch event
        }




        macrosImg.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Handle touch down event
                    // Load the colored drawable
                    val macros1 = ContextCompat.getDrawable(this, R.mipmap.macros_white_100dp)
                    // Change image to colored version when touched
                    macrosTV.setTextColor(ContextCompat.getColor(this, R.color.white))
                    macrosImg.setBackgroundResource(R.color.skyblue)
                    macrosImg.setImageDrawable(macros1)
                }

                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    // Handle touch up or cancel event
                    // Revert to default image when touch is released or cancelled
                    val macros2 = ContextCompat.getDrawable(this, R.mipmap.macros_black_100dp)
                    // Change image to colored version when touched
                    macrosTV.setTextColor(ContextCompat.getColor(this, R.color.black))
                    macrosImg.setBackgroundResource(R.color.skyblue)
                    macrosImg.setImageDrawable(macros2)
                    val intent = Intent(this, MacrosPg::class.java)
                    val animation = ActivityOptions.makeCustomAnimation(
                        this,
                        R.anim.slide_in_left,
                        R.anim.slide_out_right
                    )
                    macrosTV.setOnClickListener {
                        startActivity(intent, animation.toBundle())
                    }
                    startActivity(intent, animation.toBundle())
                    finish()
                    // You can optionally trigger click event here if needed
                    // view.performClick()
                }
            }
            true // Consume the touch event
        }



        profileImg.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    val profile1 = ContextCompat.getDrawable(this, R.mipmap.profile_white_100dp)
                    profileTV.setTextColor(ContextCompat.getColor(this, R.color.white))
                    profileImg.setBackgroundResource(R.color.skyblue)
                    profileImg.setImageDrawable(profile1)
                }

                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    val profile2 = ContextCompat.getDrawable(this, R.mipmap.profile_black_100dp)
                    profileTV.setTextColor(ContextCompat.getColor(this, R.color.black))
                    profileImg.setBackgroundResource(R.color.skyblue)
                    profileImg.setImageDrawable(profile2)
                    val intent = Intent(this, ProfilePg::class.java)
                    val animation = ActivityOptions.makeCustomAnimation(
                        this,
                        R.anim.slide_in_left,
                        R.anim.slide_out_right
                    )

                    workoutTV.setOnClickListener {
                        startActivity(intent, animation.toBundle())
                    }
                    startActivity(intent, animation.toBundle())
                    finish()
                }
            }
            true // Consume the touch event
        }

    }


}
