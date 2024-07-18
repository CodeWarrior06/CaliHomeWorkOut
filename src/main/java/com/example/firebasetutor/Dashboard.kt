package com.example.firebasetutor

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.firebasetutor.beginner.BeginnerPhase1
import com.example.firebasetutor.databinding.ActivityDashboardBinding
import com.example.firebasetutor.workoutModule.WorkoutPg
import com.google.firebase.database.DatabaseReference
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@Suppress("DEPRECATION")
class Dashboard : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var routineModule: GridLayout
    private lateinit var currentDateCircle: TextView
    private lateinit var calendar: Calendar
    private var dateToString = ""
    private var currentDayOfWeek: Int = 0
    private var isClicked = true


    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var workoutDays: Array<String>


    private lateinit var overallLayout: View
    private lateinit var beginnerSched3Time: View
    private lateinit var beginnerSched4Time: View
    private lateinit var beginnerSched5Time: View

    private lateinit var intermediateSched3Time: View
    private lateinit var intermediateSched4Time: View
    private lateinit var intermediateSched5Time: View
    private lateinit var beginnerSchedMonday: TextView

    private lateinit var scrollViewBeginner: ScrollView

    private lateinit var mondayTextView: TextView

    //For Workout Container Beginner
    private lateinit var beginnerSched3Mon: LinearLayout
    private lateinit var beginnerSched3Tue: LinearLayout
    private lateinit var beginnerSched3Wed: LinearLayout
    private lateinit var beginnerSched3Thu: LinearLayout
    private lateinit var beginnerSched3Fri: LinearLayout
    private lateinit var beginnerSched3Sat: LinearLayout
    private lateinit var beginnerSched3Sun: LinearLayout

    private lateinit var beginnerSched4Mon: LinearLayout
    private lateinit var beginnerSched4Tue: LinearLayout
    private lateinit var beginnerSched4Wed: LinearLayout
    private lateinit var beginnerSched4Thu: LinearLayout
    private lateinit var beginnerSched4Fri: LinearLayout
    private lateinit var beginnerSched4Sat: LinearLayout
    private lateinit var beginnerSched4Sun: LinearLayout

    private lateinit var beginnerSched5Mon: LinearLayout
    private lateinit var beginnerSched5Tue: LinearLayout
    private lateinit var beginnerSched5Wed: LinearLayout
    private lateinit var beginnerSched5Thu: LinearLayout
    private lateinit var beginnerSched5Fri: LinearLayout
    private lateinit var beginnerSched5Sat: LinearLayout
    private lateinit var beginnerSched5Sun: LinearLayout

    //For Workout Container Intermediate
    private lateinit var intermediateSched3Mon: LinearLayout
    private lateinit var intermediateSched3Tue: LinearLayout
    private lateinit var intermediateSched3Wed: LinearLayout
    private lateinit var intermediateSched3Thu: LinearLayout
    private lateinit var intermediateSched3Fri: LinearLayout
    private lateinit var intermediateSched3Sat: LinearLayout
    private lateinit var intermediateSched3Sun: LinearLayout

    private lateinit var intermediateSched4Mon: LinearLayout
    private lateinit var intermediateSched4Tue: LinearLayout
    private lateinit var intermediateSched4Wed: LinearLayout
    private lateinit var intermediateSched4Thu: LinearLayout
    private lateinit var intermediateSched4Fri: LinearLayout
    private lateinit var intermediateSched4Sat: LinearLayout
    private lateinit var intermediateSched4Sun: LinearLayout

    private lateinit var intermediateSched5Mon: LinearLayout
    private lateinit var intermediateSched5Tue: LinearLayout
    private lateinit var intermediateSched5Wed: LinearLayout
    private lateinit var intermediateSched5Thu: LinearLayout
    private lateinit var intermediateSched5Fri: LinearLayout
    private lateinit var intermediateSched5Sat: LinearLayout
    private lateinit var intermediateSched5Sun: LinearLayout

    private lateinit var colorMonday: Array<View>
    private lateinit var colorTuesday: Array<View>
    private lateinit var colorWednesday: Array<View>
    private lateinit var colorThursday: Array<View>
    private lateinit var colorFriday: Array<View>
    private lateinit var colorSaturday: Array<View>
    private lateinit var colorSunday: Array<View>

    private var isSaturdayClickable: Boolean = false


    private lateinit var beginnerSched3Days: Array<View>


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
    private var currentToast: Toast? = null
    private var isToastShowing: Boolean = false
    private lateinit var currentLevelTV : TextView


    private lateinit var dbRef: DatabaseReference

    //For Workout Container Beginner


    private val dayOfWeek: Int by lazy {
        val calendar = Calendar.getInstance()
        calendar.get(Calendar.DAY_OF_WEEK)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        calendar = Calendar.getInstance()


        // Get the current day of the week (Sunday is 1, Monday is 2, etc.)
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()


        val dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        currentLevelTV = binding.currentLevelTV

        /*
        hideSpecificContainer()


        */
        currentLevelTV()
        footbar()

        currentDate()

        weekColorChange()
        workoutListHider()

        changeWorkoutColor(dayOfWeek)

        progressBarCompleted()
        /*
        hideWholeWorkout()
        dayOfWeekFunc()


        beginnerWork1.setOnClickListener {
            val intent = Intent(this, BeginnerPhase1::class.java)
            startActivity(intent)
        }
        // workoutSelectorDate()
         */


    }


    private fun progressBarCompleted() {
        val beginnerProgressBar = sharedPreferences.getInt("beginnerProgressBar", 0)
        val intermediateProgressBar = sharedPreferences.getInt("intermediateProgressBar", 0)
        val allWorkoutDone = sharedPreferences.getInt("allWorkoutDone", 0)
        if(beginnerProgressBar >= 100 && intermediateProgressBar >= 100){
            if(allWorkoutDone == 0){
                showCompletionDialog()
            }
            else{
                showToast("All Workout is Complete, Workout Progress wont change anymore...")
            }


        }

    }

    private fun showCompletionDialog() {
        // Inflate the custom layout
        val inflater: LayoutInflater = layoutInflater
        val dialogView: View = inflater.inflate(R.layout.progress_bar_completed, null)
        val allWorkoutDone = sharedPreferences.getInt("allWorkoutDone", 0)

        // Create the AlertDialog
        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        dialogBuilder.setView(dialogView)

        // Get the buttons from the custom layout

        val btnContinue: Button = dialogView.findViewById(R.id.btnContinue)

        // Create and show the dialog
        val alertDialog: AlertDialog = dialogBuilder.create()
        alertDialog.show()

        sharedPreferences.edit()
        editor.putInt("allWorkoutDone", 1)
        editor.commit()

        // Set button listeners


        btnContinue.setOnClickListener {
            // Handle Continue button click

            alertDialog.dismiss()

            // Call your continue task method or logic here
        }
    }

    private fun currentLevelTV(){
        val isBeginnerSchedView = sharedPreferences.getString("isBeginnerSchedView", "")
        val isIntermediateSchedView = sharedPreferences.getString("isIntermediateSchedView", "")

        if(isBeginnerSchedView == "Yes"){
            currentLevelTV.text = "Beginner Workout Routine"
        }
        else if (isIntermediateSchedView == "Yes"){
            currentLevelTV.text = "Intermediate Workout Routine"
        }
        else
        {
            currentLevelTV.text = "Not Selected Routine"
        }

    }

    private fun workoutListHider() {
        workoutList()
        val isBeginnerSchedView = sharedPreferences.getString("isBeginnerSchedView", "")
        val isIntermediateSchedView = sharedPreferences.getString("isIntermediateSchedView", "")

        val beginnerAskSched = sharedPreferences.getString("beginnerAskSched", "")
        val intermediateAskSched = sharedPreferences.getString("intermediateAskSched", "")



        if (isBeginnerSchedView == "" && isIntermediateSchedView == "") {
            overallLayout.visibility = View.GONE
        } else {
            Log.d("AnongValue", "BeginnerSched  is $beginnerAskSched")
            Log.d("AnongValue", "IntermediateSched is $intermediateAskSched")
            if (isBeginnerSchedView == "Yes") {
                when (beginnerAskSched) {
                    "1" -> {
                        beginnerSched3Time.visibility = View.VISIBLE
                        beginnerSched4Time.visibility = View.GONE
                        beginnerSched5Time.visibility = View.GONE
                        intermediateHider()
                    }

                    "2" -> {
                        beginnerSched4Time.visibility = View.VISIBLE
                        beginnerSched3Time.visibility = View.GONE
                        beginnerSched5Time.visibility = View.GONE
                        intermediateHider()
                    }

                    "3" -> {
                        beginnerSched5Time.visibility = View.VISIBLE
                        beginnerSched4Time.visibility = View.GONE
                        beginnerSched3Time.visibility = View.GONE
                        // beginnerSchedMonday.text = "hello"
                        intermediateHider()
                    }
                }
            } else if (isIntermediateSchedView == "Yes") {

                when (intermediateAskSched) {
                    "1" -> {
                        intermediateSched3Time.visibility = View.VISIBLE
                        intermediateSched4Time.visibility = View.GONE
                        intermediateSched5Time.visibility = View.GONE
                        beginnerHider()
                    }

                    "2" -> {
                        intermediateSched4Time.visibility = View.VISIBLE
                        intermediateSched3Time.visibility = View.GONE
                        intermediateSched5Time.visibility = View.GONE
                        beginnerHider()
                    }

                    "3" -> {
                        intermediateSched5Time.visibility = View.VISIBLE
                        intermediateSched4Time.visibility = View.GONE
                        intermediateSched3Time.visibility = View.GONE
                        beginnerHider()
                    }
                }

            }
        }


    }

    private fun workoutClickListeners() { //TO be fixed since if i make a start activity here the Onclick event that are false will be redundant and make some to be clickable even if they are not
        var beginnerSchedPushPull = arrayOf(
            binding.beginnerWorkoutList3times.tuesdayWorkout,
            binding.beginnerWorkoutList3times.saturdayWorkout,

            binding.beginnerWorkoutList4times.mondayWorkout,
            binding.beginnerWorkoutList4times.fridayWorkout,
            binding.beginnerWorkoutList4times.sundayWorkout,

            binding.beginnerWorkoutList5times.mondayWorkout,
            binding.beginnerWorkoutList5times.wednesdayWorkout,
            binding.beginnerWorkoutList5times.fridayWorkout,
            binding.beginnerWorkoutList5times.sundayWorkout
        )

        beginnerSchedPushPull.forEach { view ->
            view.setOnClickListener {
                sharedPreferences.edit()
                editor.putString("isWorkout", "beginnerPushPull")
                editor.commit()

                startActivity(Intent(this, BeginnerPhase1::class.java))
            }
        }
        var beginnerSchedLegCore = arrayOf(
            binding.beginnerWorkoutList3times.thursdayWorkout,

            binding.beginnerWorkoutList4times.wednesdayWorkout,

            binding.beginnerWorkoutList5times.thursdayWorkout

        )
        beginnerSchedLegCore.forEach { view ->
            view.setOnClickListener {
                sharedPreferences.edit()
                editor.putString("isWorkout", "beginnerLegCore")
                editor.commit()
                startActivity(Intent(this, BeginnerPhase1::class.java))
            }
        }
        var beginnerSchedRest = arrayOf(
            binding.beginnerWorkoutList3times.mondayWorkout,
            binding.beginnerWorkoutList3times.wednesdayWorkout,
            binding.beginnerWorkoutList3times.fridayWorkout,
            binding.beginnerWorkoutList3times.sundayWorkout,

            binding.beginnerWorkoutList4times.tuesdayWorkout,
            binding.beginnerWorkoutList4times.thursdayWorkout,
            binding.beginnerWorkoutList4times.saturdayWorkout,

            binding.beginnerWorkoutList5times.tuesdayWorkout,
            binding.beginnerWorkoutList5times.saturdayWorkout
        )
        beginnerSchedRest.forEach { view ->
            view.setOnClickListener {
                Toast.makeText(
                    applicationContext,
                    "You are on Rest Day, Please Comeback tomorrow.",
                    Toast.LENGTH_LONG
                )

            }
        }


        //INTERMEDIATE  CLICK LISTENERS


        var intermediateSchedPushPull = arrayOf(
            binding.intermediateWorkoutList3times.tuesdayWorkout,
            binding.intermediateWorkoutList3times.saturdayWorkout,

            binding.intermediateWorkoutList4times.mondayWorkout,
            binding.intermediateWorkoutList4times.fridayWorkout,
            binding.intermediateWorkoutList4times.sundayWorkout,

            binding.intermediateWorkoutList5times.mondayWorkout,
            binding.intermediateWorkoutList5times.wednesdayWorkout,
            binding.intermediateWorkoutList5times.fridayWorkout,
            binding.intermediateWorkoutList5times.sundayWorkout
        )

        intermediateSchedPushPull.forEach { view ->
            view.setOnClickListener {
                sharedPreferences.edit()
                editor.putString("isWorkout", "intermediatePushPull")
                editor.commit()
                startActivity(Intent(this, BeginnerPhase1::class.java))
            }
        }
        var intermediateSchedLegCore = arrayOf(
            binding.intermediateWorkoutList3times.thursdayWorkout,

            binding.intermediateWorkoutList4times.wednesdayWorkout,

            binding.intermediateWorkoutList5times.thursdayWorkout

        )
        intermediateSchedLegCore.forEach { view ->
            view.setOnClickListener {
                sharedPreferences.edit()
                editor.putString("isWorkout", "intermediateLegCore")
                editor.commit()
                startActivity(Intent(this, BeginnerPhase1::class.java))
            }
        }
        var intermediateSchedRest = arrayOf(
            binding.intermediateWorkoutList3times.mondayWorkout,
            binding.intermediateWorkoutList3times.wednesdayWorkout,
            binding.intermediateWorkoutList3times.fridayWorkout,
            binding.intermediateWorkoutList3times.sundayWorkout,

            binding.intermediateWorkoutList4times.tuesdayWorkout,
            binding.intermediateWorkoutList4times.thursdayWorkout,
            binding.intermediateWorkoutList4times.saturdayWorkout,

            binding.intermediateWorkoutList5times.thursdayWorkout,
            binding.intermediateWorkoutList5times.saturdayWorkout

        )

        intermediateSchedRest.forEach { view ->
            view.setOnClickListener {
                Toast.makeText(
                    applicationContext,
                    "You are on Rest Day, Please Comeback tomorrow.",
                    Toast.LENGTH_LONG
                )

            }
        }
    }


    private fun workoutList() {
        //Calisthenic Beginner

        overallLayout = binding.overallLayout

        beginnerSched3Time = binding.beginnerWorkoutList3times.workoutList
        beginnerSched4Time = binding.beginnerWorkoutList4times.workoutList
        beginnerSched5Time = binding.beginnerWorkoutList5times.workoutList
        beginnerSchedMonday = binding.beginnerWorkoutList5times.mondayWorkoutText

        //Calisthenic Beginner Week Container
        beginnerSched3Mon = binding.beginnerWorkoutList3times.mondayWorkout
        beginnerSched3Tue = binding.beginnerWorkoutList3times.tuesdayWorkout
        beginnerSched3Wed = binding.beginnerWorkoutList3times.wednesdayWorkout
        beginnerSched3Thu = binding.beginnerWorkoutList3times.thursdayWorkout
        beginnerSched3Fri = binding.beginnerWorkoutList3times.fridayWorkout
        beginnerSched3Sat = binding.beginnerWorkoutList3times.saturdayWorkout
        beginnerSched3Sun = binding.beginnerWorkoutList3times.sundayWorkout

        beginnerSched4Mon = binding.beginnerWorkoutList4times.mondayWorkout
        beginnerSched4Tue = binding.beginnerWorkoutList4times.tuesdayWorkout
        beginnerSched4Wed = binding.beginnerWorkoutList4times.wednesdayWorkout
        beginnerSched4Thu = binding.beginnerWorkoutList4times.thursdayWorkout
        beginnerSched4Fri = binding.beginnerWorkoutList4times.fridayWorkout
        beginnerSched4Sat = binding.beginnerWorkoutList4times.saturdayWorkout
        beginnerSched4Sun = binding.beginnerWorkoutList4times.sundayWorkout

        beginnerSched5Mon = binding.beginnerWorkoutList5times.mondayWorkout
        beginnerSched5Tue = binding.beginnerWorkoutList5times.tuesdayWorkout
        beginnerSched5Wed = binding.beginnerWorkoutList5times.wednesdayWorkout
        beginnerSched5Thu = binding.beginnerWorkoutList5times.thursdayWorkout
        beginnerSched5Fri = binding.beginnerWorkoutList5times.fridayWorkout
        beginnerSched5Sat = binding.beginnerWorkoutList5times.saturdayWorkout
        beginnerSched5Sun = binding.beginnerWorkoutList5times.sundayWorkout


        //Calisthenic Intermediate
        intermediateSched3Time = binding.intermediateWorkoutList3times.workoutList
        intermediateSched4Time = binding.intermediateWorkoutList4times.workoutList
        intermediateSched5Time = binding.intermediateWorkoutList5times.workoutList


        //Calisthenic Beginner Week Container
        intermediateSched3Mon = binding.intermediateWorkoutList3times.mondayWorkout
        intermediateSched3Tue = binding.intermediateWorkoutList3times.tuesdayWorkout
        intermediateSched3Wed = binding.intermediateWorkoutList3times.wednesdayWorkout
        intermediateSched3Thu = binding.intermediateWorkoutList3times.thursdayWorkout
        intermediateSched3Fri = binding.intermediateWorkoutList3times.fridayWorkout
        intermediateSched3Sat = binding.intermediateWorkoutList3times.saturdayWorkout
        intermediateSched3Sun = binding.intermediateWorkoutList3times.sundayWorkout

        intermediateSched4Mon = binding.intermediateWorkoutList4times.mondayWorkout
        intermediateSched4Tue = binding.intermediateWorkoutList4times.tuesdayWorkout
        intermediateSched4Wed = binding.intermediateWorkoutList4times.wednesdayWorkout
        intermediateSched4Thu = binding.intermediateWorkoutList4times.thursdayWorkout
        intermediateSched4Fri = binding.intermediateWorkoutList4times.fridayWorkout
        intermediateSched4Sat = binding.intermediateWorkoutList4times.saturdayWorkout
        intermediateSched4Sun = binding.intermediateWorkoutList4times.sundayWorkout

        intermediateSched5Mon = binding.intermediateWorkoutList5times.mondayWorkout
        intermediateSched5Tue = binding.intermediateWorkoutList5times.tuesdayWorkout
        intermediateSched5Wed = binding.intermediateWorkoutList5times.wednesdayWorkout
        intermediateSched5Thu = binding.intermediateWorkoutList5times.thursdayWorkout
        intermediateSched5Fri = binding.intermediateWorkoutList5times.fridayWorkout
        intermediateSched5Sat = binding.intermediateWorkoutList5times.saturdayWorkout
        intermediateSched5Sun = binding.intermediateWorkoutList5times.sundayWorkout


    }

    private fun intermediateHider() {
        intermediateSched3Time.visibility = View.GONE
        intermediateSched4Time.visibility = View.GONE
        intermediateSched5Time.visibility = View.GONE
    }

    private fun beginnerHider() {
        beginnerSched3Time.visibility = View.GONE
        beginnerSched4Time.visibility = View.GONE
        beginnerSched5Time.visibility = View.GONE
    }


    private fun currentDate() {
        currentDateCircle = binding.currentDateTV
        val year = calendar.get(Calendar.YEAR)
        val month = SimpleDateFormat("MMMM", Locale.getDefault()).format(calendar.time)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        dateToString = "$month $day, $year"
        currentDateCircle.text = dateToString
    }


    private fun weekColorChange() {
        val textViewIds = arrayOf(
            R.id.mondayTV,
            R.id.tuesdayTV,
            R.id.wednesdayTV,
            R.id.thursdayTV,
            R.id.fridayTV,
            R.id.saturdayTV,
            R.id.sundayTV
        )

        val currentDayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        val correctedDayIndex = if (currentDayOfWeek == Calendar.SUNDAY) 6 else currentDayOfWeek - 2

        for (i in textViewIds.indices) {
            val textView = findViewById<TextView>(textViewIds[i])

            if (i == correctedDayIndex) {
                textView.setBackgroundResource(R.drawable.circle_radius)
                textView.setTextSize(17f)
            } else {
                textView.setBackgroundResource(R.drawable.circle)


            }
        }
    }


    private fun changeWorkoutColor(dayOfWeek: Int) {


        val beginnerPushPullPhase = sharedPreferences.getInt("beginnerPushPullPhase", 0)
        val beginnerLegCorePhase = sharedPreferences.getInt("beginnerLegCorePhase", 0)

        val intermediatePushPullPhase = sharedPreferences.getInt("intermediatePushPullPhase", 0)
        val intermediateLegCorePhase = sharedPreferences.getInt("intermediateLegCorePhase", 0)

        val isBeginnerSchedView = sharedPreferences.getString("isBeginnerSchedView", "")
        val isIntermediateSchedView = sharedPreferences.getString("isIntermediateSchedView", "")
        var currentWeek = sharedPreferences.getInt("currentWeek", 0)
        when (dayOfWeek) {
            Calendar.MONDAY -> {
                mondayLayout()   // Color Changer for Current Workout to do
                //make a click lisener for all the monday workout and all of them will go to specific layout
                var Monday = arrayOf(
                    binding.beginnerWorkoutList3times.mondayWorkout,
                    binding.beginnerWorkoutList4times.mondayWorkout,
                    binding.beginnerWorkoutList5times.mondayWorkout,

                    binding.intermediateWorkoutList3times.mondayWorkout,
                    binding.intermediateWorkoutList4times.mondayWorkout,
                    binding.intermediateWorkoutList5times.mondayWorkout,
                )

                Monday.forEach { view ->

                    if (view.visibility == View.VISIBLE) {
                        for (layout in Monday) {
                            val textView = layout.findViewById<TextView>(R.id.mondayWorkoutText)
                            // Set onClickListener to navigate to the next activity
                            if (textView.text == "Push & Pull Day Calisthenics") {


                                //starting point of obese unclickable
                                val nutritionalStatus = sharedPreferences.getString("nutritionalStatus", "")
                                val nutritionalStatusOnline = sharedPreferences.getString("nutritionalStatusData", "")

                                if(nutritionalStatus == "Obesity Class 1" || nutritionalStatusOnline  == "Obesity Class 1" ){
                                    view.isClickable = true
                                    showToast("This workout is currently outside your recommended fitness level")
                                    view.isClickable = false
                                }
                                else if(nutritionalStatus == "Obesity Class 2" || nutritionalStatusOnline  == "Obesity Class 2" ){
                                    view.isClickable = true
                                    showToast("This workout is currently outside your recommended fitness level")
                                    view.isClickable = false
                                }
                                else if(nutritionalStatus == "Obesity Class 3" || nutritionalStatusOnline  == "Obesity Class 3" ){
                                    view.isClickable = true
                                    showToast("This workout is currently outside your recommended fitness level")
                                    view.isClickable = false
                                }
                                else{
                                    view.isClickable = true
                                    layout.setOnClickListener {
                                        if (currentWeek == 1) {
                                            showToast("You finished your workout for today, Comeback Later.")

                                        } else {
                                            val intent = Intent(this, BeginnerPhase1::class.java)
                                            sharedPreferences.edit()
                                            editor.putString("isWorkout", "PushPull")
                                            editor.commit()
                                            startActivity(intent)
                                        }


                                    }
                                }

                                //ending point of obese unclickable




                            } else if (textView.text == "Rest Day Calisthenics") {

                                //starting point of obese unclickable
                                val nutritionalStatus = sharedPreferences.getString("nutritionalStatus", "")
                                val nutritionalStatusOnline = sharedPreferences.getString("nutritionalStatusData", "")

                                if(nutritionalStatus == "Obesity Class 1" || nutritionalStatusOnline  == "Obesity Class 1" ){
                                    view.isClickable = true
                                    showToast("This workout is currently outside your recommended fitness level")
                                    view.isClickable = false
                                }
                                else if(nutritionalStatus == "Obesity Class 2" || nutritionalStatusOnline  == "Obesity Class 2" ){
                                    view.isClickable = true
                                    showToast("This workout is currently outside your recommended fitness level")
                                    view.isClickable = false
                                }
                                else if(nutritionalStatus == "Obesity Class 3" || nutritionalStatusOnline  == "Obesity Class 3" ){
                                    view.isClickable = true
                                    showToast("This workout is currently outside your recommended fitness level")
                                    view.isClickable = false
                                }
                                else{
                                    view.isClickable = true
                                    // Set onClickListener to navigate to the next activity
                                    layout.setOnClickListener {


                                        showToast("Your next scheduled workout is tommorow, Please Comeback Later")



                                    }
                                }

                                //ending point of obese unclickable

                            }
                        }

                    } else {

                    }


                }
            }

            Calendar.TUESDAY -> {

                tuesdayLayout() // Color Changer for Current Workout to do
                var Tuesday = arrayOf(
                    binding.beginnerWorkoutList3times.tuesdayWorkout,
                    binding.beginnerWorkoutList4times.tuesdayWorkout,
                    binding.beginnerWorkoutList5times.tuesdayWorkout,

                    binding.intermediateWorkoutList3times.tuesdayWorkout,
                    binding.intermediateWorkoutList4times.tuesdayWorkout,
                    binding.intermediateWorkoutList5times.tuesdayWorkout
                )
                Tuesday.forEach { view ->

                    if (view.visibility == View.VISIBLE) {
                        for (layout in Tuesday) {
                            val textView = layout.findViewById<TextView>(R.id.tuesdayWorkoutText)
                            // Set onClickListener to navigate to the next activity
                            if (textView.text == "Push & Pull Day Calisthenics") {

                                //starting point of obese unclickable
                                val nutritionalStatus = sharedPreferences.getString("nutritionalStatus", "")
                                val nutritionalStatusOnline = sharedPreferences.getString("nutritionalStatusData", "")

                                if(nutritionalStatus == "Obesity Class 1" || nutritionalStatusOnline  == "Obesity Class 1" ){
                                    view.isClickable = true
                                    showToast("This workout is currently outside your recommended fitness level")
                                    view.isClickable = false
                                }
                                else if(nutritionalStatus == "Obesity Class 2" || nutritionalStatusOnline  == "Obesity Class 2" ){
                                    view.isClickable = true
                                    showToast("This workout is currently outside your recommended fitness level")
                                    view.isClickable = false
                                }
                                else if(nutritionalStatus == "Obesity Class 3" || nutritionalStatusOnline  == "Obesity Class 3" ){
                                    view.isClickable = true
                                    showToast("This workout is currently outside your recommended fitness level")
                                    view.isClickable = false
                                }
                                else{
                                    view.isClickable = true
                                    layout.setOnClickListener {
                                        if (currentWeek == 2) {

                                            showToast("You finished your workout for today, Comeback Later.")

                                        } else {
                                            val intent = Intent(this, BeginnerPhase1::class.java)
                                            sharedPreferences.edit()
                                            editor.putString("isWorkout", "PushPull")
                                            editor.commit()
                                            startActivity(intent)
                                        }


                                    }
                                }

                                //ending point of obese unclickable


                            } else if (textView.text == "Rest Day Calisthenics") {

                                //starting point of obese unclickable
                                val nutritionalStatus = sharedPreferences.getString("nutritionalStatus", "")
                                val nutritionalStatusOnline = sharedPreferences.getString("nutritionalStatusData", "")

                                if(nutritionalStatus == "Obesity Class 1" || nutritionalStatusOnline  == "Obesity Class 1" ){
                                    view.isClickable = true
                                    showToast("This workout is currently outside your recommended fitness level")
                                    view.isClickable = false
                                }
                                else if(nutritionalStatus == "Obesity Class 2" || nutritionalStatusOnline  == "Obesity Class 2" ){
                                    view.isClickable = true
                                    showToast("This workout is currently outside your recommended fitness level")
                                    view.isClickable = false
                                }
                                else if(nutritionalStatus == "Obesity Class 3" || nutritionalStatusOnline  == "Obesity Class 3" ){
                                    view.isClickable = true
                                    showToast("This workout is currently outside your recommended fitness level")
                                    view.isClickable = false
                                }
                                else{
                                    view.isClickable = true
                                    layout.setOnClickListener {

                                        showToast("Your next scheduled workout is tommorow, Please Comeback Later")

                                    }
                                }

                                //ending point of obese unclickable

                                // Set onClickListener to navigate to the next activity



                            }
                        }

                    } else {

                    }


                }

            }

            Calendar.WEDNESDAY -> {
                wednesdayLayout() // Color Changer for Current Workout to do
                var Wednesday = arrayOf(
                    binding.beginnerWorkoutList3times.wednesdayWorkout,
                    binding.beginnerWorkoutList4times.wednesdayWorkout,
                    binding.beginnerWorkoutList5times.wednesdayWorkout,

                    binding.intermediateWorkoutList3times.wednesdayWorkout,
                    binding.intermediateWorkoutList4times.wednesdayWorkout,
                    binding.intermediateWorkoutList5times.wednesdayWorkout
                )
                Wednesday.forEach { view ->

                    if (view.visibility == View.VISIBLE) {
                        for (layout in Wednesday) {
                            val textView = layout.findViewById<TextView>(R.id.wednesdayWorkoutText)
                            // Set onClickListener to navigate to the next activity
                            if (textView.text == "Push & Pull Day Calisthenics") {

                                //starting point of obese unclickable
                                val nutritionalStatus = sharedPreferences.getString("nutritionalStatus", "")
                                val nutritionalStatusOnline = sharedPreferences.getString("nutritionalStatusData", "")

                                if(nutritionalStatus == "Obesity Class 1" || nutritionalStatusOnline  == "Obesity Class 1" ){
                                    view.isClickable = true
                                    showToast("This workout is currently outside your recommended fitness level")
                                    view.isClickable = false
                                }
                                else if(nutritionalStatus == "Obesity Class 2" || nutritionalStatusOnline  == "Obesity Class 2" ){
                                    view.isClickable = true
                                    showToast("This workout is currently outside your recommended fitness level")
                                    view.isClickable = false
                                }
                                else if(nutritionalStatus == "Obesity Class 3" || nutritionalStatusOnline  == "Obesity Class 3" ){
                                    view.isClickable = true
                                    showToast("This workout is currently outside your recommended fitness level")
                                    view.isClickable = false
                                }
                                else{
                                    view.isClickable = true
                                    layout.setOnClickListener {

                                        if (currentWeek == 3) {
                                            showToast("You finished your workout for today, Comeback Later.")

                                        } else{

                                            val intent = Intent(this, BeginnerPhase1::class.java)
                                            sharedPreferences.edit()
                                            editor.putString("isWorkout", "PushPull")
                                            editor.commit()
                                            startActivity(intent)

                                        }


                                    }
                                }

                                //ending point of obese unclickable


                            } else if (textView.text == "Rest Day Calisthenics") {

                                //starting point of obese unclickable
                                val nutritionalStatus = sharedPreferences.getString("nutritionalStatus", "")
                                val nutritionalStatusOnline = sharedPreferences.getString("nutritionalStatusData", "")

                                if(nutritionalStatus == "Obesity Class 1" || nutritionalStatusOnline  == "Obesity Class 1" ){
                                    view.isClickable = true
                                    showToast("This workout is currently outside your recommended fitness level")
                                    view.isClickable = false
                                }
                                else if(nutritionalStatus == "Obesity Class 2" || nutritionalStatusOnline  == "Obesity Class 2" ){
                                    view.isClickable = true
                                    showToast("This workout is currently outside your recommended fitness level")
                                    view.isClickable = false
                                }
                                else if(nutritionalStatus == "Obesity Class 3" || nutritionalStatusOnline  == "Obesity Class 3" ){
                                    view.isClickable = true
                                    showToast("This workout is currently outside your recommended fitness level")
                                    view.isClickable = false
                                }
                                else{
                                    view.isClickable = true
                                    layout.setOnClickListener {

                                        showToast("Your next scheduled workout is tommorow, Please Comeback Later")

                                    }
                                }

                                //ending point of obese unclickable



                            } else if (textView.text == "Legs & Core Day Calisthenics") {

                                //starting point of obese unclickable
                                val nutritionalStatus = sharedPreferences.getString("nutritionalStatus", "")
                                val nutritionalStatusOnline = sharedPreferences.getString("nutritionalStatusData", "")

                                if(nutritionalStatus == "Obesity Class 1" || nutritionalStatusOnline  == "Obesity Class 1" ){
                                    view.isClickable = true
                                    showToast("This workout is currently outside your recommended fitness level")
                                    view.isClickable = false
                                }
                                else if(nutritionalStatus == "Obesity Class 2" || nutritionalStatusOnline  == "Obesity Class 2" ){
                                    view.isClickable = true
                                    showToast("This workout is currently outside your recommended fitness level")
                                    view.isClickable = false
                                }
                                else if(nutritionalStatus == "Obesity Class 3" || nutritionalStatusOnline  == "Obesity Class 3" ){
                                    view.isClickable = true
                                    showToast("This workout is currently outside your recommended fitness level")
                                    view.isClickable = false
                                }
                                else{
                                    view.isClickable = true
                                    layout.setOnClickListener {
                                        if (currentWeek == 3) {

                                            showToast("You finished your workout for today, Comeback Later.")

                                        } else{

                                            val intent = Intent(this, BeginnerPhase1::class.java)
                                            sharedPreferences.edit()
                                            editor.putString("isWorkout", "LegCore")
                                            editor.commit()
                                            startActivity(intent)

                                        }


                                    }
                                }

                                //ending point of obese unclickable



                            }
                        }

                    } else {

                    }
                }
            }

            Calendar.THURSDAY -> {
                //Thursdayblah
                thursdayLayout() // Color Changer for Current Workout to do
                var Thursday = arrayOf(
                    binding.beginnerWorkoutList3times.thursdayWorkout,
                    binding.beginnerWorkoutList4times.thursdayWorkout,
                    binding.beginnerWorkoutList5times.thursdayWorkout,

                    binding.intermediateWorkoutList3times.thursdayWorkout,
                    binding.intermediateWorkoutList4times.thursdayWorkout,
                    binding.intermediateWorkoutList5times.thursdayWorkout
                )

                Thursday.forEach { view ->

                    if (view.visibility == View.VISIBLE) {

                        for (layout in Thursday) {

                            val textView = layout.findViewById<TextView>(R.id.thursdayWorkoutText)

                            // Set onClickListener to navigate to the next activity
                            if (textView.text == "Legs & Core Day Calisthenics") {

                                //starting point of obese unclickable
                                val nutritionalStatus = sharedPreferences.getString("nutritionalStatus", "")
                                val nutritionalStatusOnline = sharedPreferences.getString("nutritionalStatusData", "")

                                if(nutritionalStatus == "Obesity Class 1" || nutritionalStatusOnline  == "Obesity Class 1" ){
                                    view.isClickable = true
                                    showToast("This workout is currently outside your recommended fitness level")
                                    view.isClickable = false
                                }
                                else if(nutritionalStatus == "Obesity Class 2" || nutritionalStatusOnline  == "Obesity Class 2" ){
                                    view.isClickable = true
                                    showToast("This workout is currently outside your recommended fitness level")
                                    view.isClickable = false
                                }
                                else if(nutritionalStatus == "Obesity Class 3" || nutritionalStatusOnline  == "Obesity Class 3" ){
                                    view.isClickable = true
                                    showToast("This workout is currently outside your recommended fitness level")
                                    view.isClickable = false
                                }
                                else{
                                    view.isClickable = true
                                    layout.setOnClickListener {

                                        if (currentWeek == 4) {

                                            showToast("You finished your workout for today, Comeback Later.")

                                        } else{

                                            val intent = Intent(this, BeginnerPhase1::class.java)
                                            sharedPreferences.edit()
                                            editor.putString("isWorkout", "LegCore")
                                            editor.commit()
                                            startActivity(intent)

                                        }

                                    }
                                }

                                //ending point of obese unclickable


                            } else if (textView.text == "Rest Day Calisthenics") {

                                //starting point of obese unclickable
                                val nutritionalStatus = sharedPreferences.getString("nutritionalStatus", "")
                                val nutritionalStatusOnline = sharedPreferences.getString("nutritionalStatusData", "")

                                if(nutritionalStatus == "Obesity Class 1" || nutritionalStatusOnline  == "Obesity Class 1" ){
                                    view.isClickable = true
                                    showToast("This workout is currently outside your recommended fitness level")
                                    view.isClickable = false
                                }
                                else if(nutritionalStatus == "Obesity Class 2" || nutritionalStatusOnline  == "Obesity Class 2" ){
                                    view.isClickable = true
                                    showToast("This workout is currently outside your recommended fitness level")
                                    view.isClickable = false
                                }
                                else if(nutritionalStatus == "Obesity Class 3" || nutritionalStatusOnline  == "Obesity Class 3" ){
                                    view.isClickable = true
                                    showToast("This workout is currently outside your recommended fitness level")
                                    view.isClickable = false
                                }
                                else{
                                    view.isClickable = true
                                    layout.setOnClickListener {

                                        showToast("Your next scheduled workout is tommorow, Please Comeback Later")

                                    }
                                }

                                //ending point of obese unclickable



                            }
                        }

                    } else {

                    }
                }
            }

            Calendar.FRIDAY -> {
                //fridayblah
                fridayLayout() // Color Changer for Current Workout to do
                var Friday = arrayOf(
                    binding.beginnerWorkoutList3times.fridayWorkout,
                    binding.beginnerWorkoutList4times.fridayWorkout,
                    binding.beginnerWorkoutList5times.fridayWorkout,

                    binding.intermediateWorkoutList3times.fridayWorkout,
                    binding.intermediateWorkoutList4times.fridayWorkout,
                    binding.intermediateWorkoutList5times.fridayWorkout
                )
                Friday.forEach { view ->

                    if (view.visibility == View.VISIBLE) {
                        for (layout in Friday) {
                            val textView = layout.findViewById<TextView>(R.id.fridayWorkoutText)
                            // Set onClickListener to navigate to the next activity
                            if (textView.text == "Push & Pull Day Calisthenics") {

                                //starting point of obese unclickable
                                val nutritionalStatus = sharedPreferences.getString("nutritionalStatus", "")
                                val nutritionalStatusOnline = sharedPreferences.getString("nutritionalStatusData", "")

                                if(nutritionalStatus == "Obesity Class 1" || nutritionalStatusOnline  == "Obesity Class 1" ){
                                    view.isClickable = true
                                    showToast("This workout is currently outside your recommended fitness level")
                                    view.isClickable = false
                                }
                                else if(nutritionalStatus == "Obesity Class 2" || nutritionalStatusOnline  == "Obesity Class 2" ){
                                    view.isClickable = true
                                    showToast("This workout is currently outside your recommended fitness level")
                                    view.isClickable = false
                                }
                                else if(nutritionalStatus == "Obesity Class 3" || nutritionalStatusOnline  == "Obesity Class 3" ){
                                    view.isClickable = true
                                    showToast("This workout is currently outside your recommended fitness level")
                                    view.isClickable = false
                                }
                                else{
                                    view.isClickable = true
                                    layout.setOnClickListener {

                                        if (currentWeek == 5) {
                                            showToast("You finished your workout for today, Comeback Later.")

                                        } else{

                                            val intent = Intent(this, BeginnerPhase1::class.java)
                                            sharedPreferences.edit()
                                            editor.putString("isWorkout", "PushPull")
                                            editor.commit()
                                            startActivity(intent)

                                        }


                                    }
                                }

                                //ending point of obese unclickable


                            } else if (textView.text == "Rest Day Calisthenics") {

                                //starting point of obese unclickable
                                val nutritionalStatus = sharedPreferences.getString("nutritionalStatus", "")
                                val nutritionalStatusOnline = sharedPreferences.getString("nutritionalStatusData", "")

                                if(nutritionalStatus == "Obesity Class 1" || nutritionalStatusOnline  == "Obesity Class 1" ){
                                    view.isClickable = true
                                    showToast("This workout is currently outside your recommended fitness level")
                                    view.isClickable = false
                                }
                                else if(nutritionalStatus == "Obesity Class 2" || nutritionalStatusOnline  == "Obesity Class 2" ){
                                    view.isClickable = true
                                    showToast("This workout is currently outside your recommended fitness level")
                                    view.isClickable = false
                                }
                                else if(nutritionalStatus == "Obesity Class 3" || nutritionalStatusOnline  == "Obesity Class 3" ){
                                    view.isClickable = true
                                    showToast("This workout is currently outside your recommended fitness level")
                                    view.isClickable = false
                                }
                                else{
                                    view.isClickable = true
                                    layout.setOnClickListener {

                                        showToast("Your next scheduled workout is tommorow, Please Comeback Later")

                                    }
                                }

                                //ending point of obese unclickable

                                // Set onClickListener to navigate to the next activity

                            }
                        }

                    } else {

                    }
                }
            }

            Calendar.SATURDAY -> {
                //saturdayblah
                saturdayLayout() // Color Changer for Current Workout to do
                var Saturday = arrayOf(
                    binding.beginnerWorkoutList3times.saturdayWorkout,
                    binding.beginnerWorkoutList4times.saturdayWorkout,
                    binding.beginnerWorkoutList5times.saturdayWorkout,

                    binding.intermediateWorkoutList3times.saturdayWorkout,
                    binding.intermediateWorkoutList4times.saturdayWorkout,
                    binding.intermediateWorkoutList5times.saturdayWorkout
                )
                Saturday.forEach { view ->

                    if (view.visibility == View.VISIBLE) {
                        for (layout in Saturday) {

                            val textView = layout.findViewById<TextView>(R.id.saturdayWorkoutText)
                            // Set onClickListener to navigate to the next activity
                            if (textView.text == "Push & Pull Day Calisthenics") {

                                //starting point of obese unclickable
                                val nutritionalStatus = sharedPreferences.getString("nutritionalStatus", "")
                                val nutritionalStatusOnline = sharedPreferences.getString("nutritionalStatusData", "")

                                if(nutritionalStatus == "Obesity Class 1" || nutritionalStatusOnline  == "Obesity Class 1" ){
                                    view.isClickable = true
                                    showToast("This workout is currently outside your recommended fitness level")
                                    view.isClickable = false
                                }
                                else if(nutritionalStatus == "Obesity Class 2" || nutritionalStatusOnline  == "Obesity Class 2" ){
                                    view.isClickable = true
                                    showToast("This workout is currently outside your recommended fitness level")
                                    view.isClickable = false
                                }
                                else if(nutritionalStatus == "Obesity Class 3" || nutritionalStatusOnline  == "Obesity Class 3" ){
                                    view.isClickable = true
                                    showToast("This workout is currently outside your recommended fitness level")
                                    view.isClickable = false
                                }
                                else{
                                    view.isClickable = true
                                    layout.setOnClickListener {

                                        if (currentWeek == 6) {
                                            showToast("You finished your workout for today, Comeback Later.")

                                        } else{

                                            val intent = Intent(this, BeginnerPhase1::class.java)
                                            sharedPreferences.edit()
                                            editor.putString("isWorkout", "PushPull")
                                            editor.commit()
                                            startActivity(intent)

                                        }


                                    }
                                }

                                //ending point of obese unclickable


                            } else if (textView.text == "Rest Day Calisthenics") {

                                //starting point of obese unclickable
                                val nutritionalStatus = sharedPreferences.getString("nutritionalStatus", "")
                                val nutritionalStatusOnline = sharedPreferences.getString("nutritionalStatusData", "")

                                if(nutritionalStatus == "Obesity Class 1" || nutritionalStatusOnline  == "Obesity Class 1" ){
                                    view.isClickable = true
                                    showToast("This workout is currently outside your recommended fitness level")
                                    view.isClickable = false
                                }
                                else if(nutritionalStatus == "Obesity Class 2" || nutritionalStatusOnline  == "Obesity Class 2" ){
                                    view.isClickable = true
                                    showToast("This workout is currently outside your recommended fitness level")
                                    view.isClickable = false
                                }
                                else if(nutritionalStatus == "Obesity Class 3" || nutritionalStatusOnline  == "Obesity Class 3" ){
                                    view.isClickable = true
                                    showToast("This workout is currently outside your recommended fitness level")
                                    view.isClickable = false
                                }
                                else{
                                    view.isClickable = true
                                    layout.setOnClickListener {

                                        showToast("Your next scheduled workout is tommorow, Please Comeback Later")

                                    }
                                }

                                //ending point of obese unclickable

                                // Set onClickListener to navigate to the next activity

                            }
                        }

                    } else {

                    }

                }
            }

            Calendar.SUNDAY -> {

                sundayLayout() // Color Changer for Current Workout to do
                var Sunday = arrayOf(
                    binding.beginnerWorkoutList3times.sundayWorkout,
                    binding.beginnerWorkoutList4times.sundayWorkout,
                    binding.beginnerWorkoutList5times.sundayWorkout,

                    binding.intermediateWorkoutList3times.sundayWorkout,
                    binding.intermediateWorkoutList4times.sundayWorkout,
                    binding.intermediateWorkoutList5times.sundayWorkout
                )
                Sunday.forEach { view ->

                    if (view.visibility == View.VISIBLE) {
                        for (layout in Sunday) {
                            val textView = layout.findViewById<TextView>(R.id.sundayWorkoutText)
                            // Set onClickListener to navigate to the next activity
                            if (textView.text == "Push & Pull Day Calisthenics") {

                                //starting point of obese unclickable
                                val nutritionalStatus = sharedPreferences.getString("nutritionalStatus", "")
                                val nutritionalStatusOnline = sharedPreferences.getString("nutritionalStatusData", "")

                                if(nutritionalStatus == "Obesity Class 1" || nutritionalStatusOnline  == "Obesity Class 1" ){
                                    view.isClickable = true
                                    showToast("This workout is currently outside your recommended fitness level")
                                    view.isClickable = false
                                }
                                else if(nutritionalStatus == "Obesity Class 2" || nutritionalStatusOnline  == "Obesity Class 2" ){
                                    view.isClickable = true
                                    showToast("This workout is currently outside your recommended fitness level")
                                    view.isClickable = false
                                }
                                else if(nutritionalStatus == "Obesity Class 3" || nutritionalStatusOnline  == "Obesity Class 3" ){
                                    view.isClickable = true
                                    showToast("This workout is currently outside your recommended fitness level")
                                    view.isClickable = false
                                }
                                else{
                                    view.isClickable = true
                                    layout.setOnClickListener {
                                        if (currentWeek == 7) {

                                            showToast("You finished your workout for today, Comeback Later.")

                                        } else{
                                            val intent = Intent(this, BeginnerPhase1::class.java)
                                            sharedPreferences.edit()
                                            editor.putString("isWorkout", "PushPull")
                                            editor.commit()
                                            startActivity(intent)
                                        }

                                    }
                                }

                                //ending point of obese unclickable


                            } else if (textView.text == "Rest Day Calisthenics") {

                                //starting point of obese unclickable
                                val nutritionalStatus = sharedPreferences.getString("nutritionalStatus", "")
                                val nutritionalStatusOnline = sharedPreferences.getString("nutritionalStatusData", "")

                                if(nutritionalStatus == "Obesity Class 1" || nutritionalStatusOnline  == "Obesity Class 1" ){
                                    view.isClickable = true
                                    showToast("This workout is currently outside your recommended fitness level")
                                    view.isClickable = false
                                }
                                else if(nutritionalStatus == "Obesity Class 2" || nutritionalStatusOnline  == "Obesity Class 2" ){
                                    view.isClickable = true
                                    showToast("This workout is currently outside your recommended fitness level")
                                    view.isClickable = false
                                }
                                else if(nutritionalStatus == "Obesity Class 3" || nutritionalStatusOnline  == "Obesity Class 3" ){
                                    view.isClickable = true
                                    showToast("This workout is currently outside your recommended fitness level")
                                    view.isClickable = false
                                }
                                else{
                                    view.isClickable = true
                                    layout.setOnClickListener {

                                        showToast("Your next scheduled workout is tommorow, Please Comeback Later")

                                    }
                                }

                                //ending point of obese unclickable

                                // Set onClickListener to navigate to the next activity

                            }
                        }

                    } else {

                    }
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



    private fun mondayIntent() {
        var Monday = arrayOf(
            binding.beginnerWorkoutList3times.mondayWorkout,
            binding.beginnerWorkoutList4times.mondayWorkout,
            binding.beginnerWorkoutList5times.mondayWorkout,

            binding.intermediateWorkoutList3times.mondayWorkout,
            binding.intermediateWorkoutList4times.mondayWorkout,
            binding.intermediateWorkoutList5times.mondayWorkout
        )
    }

    private fun setViewsBackgroundToGreen(vararg views: View) {
        for (view in views) {
            view.setBackgroundResource(R.drawable.whitebackground_borderradius_px) // Assuming you have a color resource named "green"
        }

    }

    private fun mondayLayout() {
        colorMonday = arrayOf(
            binding.beginnerWorkoutList3times.mondayWorkout,
            binding.beginnerWorkoutList4times.mondayWorkout,
            binding.beginnerWorkoutList5times.mondayWorkout,

            binding.intermediateWorkoutList3times.mondayWorkout,
            binding.intermediateWorkoutList4times.mondayWorkout,
            binding.intermediateWorkoutList5times.mondayWorkout
        )
        setViewsBackgroundToGreen(*colorMonday)
    }

    private fun tuesdayLayout() {
        colorTuesday = arrayOf(
            binding.beginnerWorkoutList3times.tuesdayWorkout,
            binding.beginnerWorkoutList4times.tuesdayWorkout,
            binding.beginnerWorkoutList5times.tuesdayWorkout,

            binding.intermediateWorkoutList3times.tuesdayWorkout,
            binding.intermediateWorkoutList4times.tuesdayWorkout,
            binding.intermediateWorkoutList5times.tuesdayWorkout
        )
        setViewsBackgroundToGreen(*colorTuesday)
    }

    private fun wednesdayLayout() {
        colorWednesday = arrayOf(
            binding.beginnerWorkoutList3times.wednesdayWorkout,
            binding.beginnerWorkoutList4times.wednesdayWorkout,
            binding.beginnerWorkoutList5times.wednesdayWorkout,

            binding.intermediateWorkoutList3times.wednesdayWorkout,
            binding.intermediateWorkoutList4times.wednesdayWorkout,
            binding.intermediateWorkoutList5times.wednesdayWorkout
        )
        setViewsBackgroundToGreen(*colorWednesday)
    }

    private fun thursdayLayout() {
        colorThursday = arrayOf(
            binding.beginnerWorkoutList3times.thursdayWorkout,
            binding.beginnerWorkoutList4times.thursdayWorkout,
            binding.beginnerWorkoutList5times.thursdayWorkout,

            binding.intermediateWorkoutList3times.thursdayWorkout,
            binding.intermediateWorkoutList4times.thursdayWorkout,
            binding.intermediateWorkoutList5times.thursdayWorkout
        )
        setViewsBackgroundToGreen(*colorThursday)
    }

    private fun fridayLayout() {
        colorFriday = arrayOf(
            binding.beginnerWorkoutList3times.fridayWorkout,
            binding.beginnerWorkoutList4times.fridayWorkout,
            binding.beginnerWorkoutList5times.fridayWorkout,

            binding.intermediateWorkoutList3times.fridayWorkout,
            binding.intermediateWorkoutList4times.fridayWorkout,
            binding.intermediateWorkoutList5times.fridayWorkout
        )
        setViewsBackgroundToGreen(*colorFriday)
    }

    private fun sundayLayout() {
        colorSunday = arrayOf(
            binding.beginnerWorkoutList3times.sundayWorkout,
            binding.beginnerWorkoutList4times.sundayWorkout,
            binding.beginnerWorkoutList5times.sundayWorkout,

            binding.intermediateWorkoutList3times.sundayWorkout,
            binding.intermediateWorkoutList4times.sundayWorkout,
            binding.intermediateWorkoutList5times.sundayWorkout
        )
        setViewsBackgroundToGreen(*colorSunday)
    }

    private fun saturdayLayout() {
        colorSaturday = arrayOf(
            binding.beginnerWorkoutList3times.saturdayWorkout,
            binding.beginnerWorkoutList4times.saturdayWorkout,
            binding.beginnerWorkoutList5times.saturdayWorkout,

            binding.intermediateWorkoutList3times.saturdayWorkout,
            binding.intermediateWorkoutList4times.saturdayWorkout,
            binding.intermediateWorkoutList5times.saturdayWorkout
        )
        setViewsBackgroundToGreen(*colorSaturday)
    }


    private fun workoutOverview() {
        var isBeginnerSchedView = sharedPreferences.getString("isBeginnerSchedView", "")
        var isIntermediateSchedView = sharedPreferences.getString("isIntermediateSchedView", "")

        if (isBeginnerSchedView == "Yes") {
            workoutClickListeners()
        } else if (isIntermediateSchedView == "Yes") {
            workoutClickListeners()
        }
    }




    @SuppressLint("ClickableViewAccessibility")
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

                }
            }
            true // Consume the touch event
        }



        workoutImg.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    val workout1 = ContextCompat.getDrawable(
                        this,
                        R.mipmap.workout_white_dumbbell100_foreground
                    )
                    workoutTV.setTextColor(ContextCompat.getColor(this, R.color.white))
                    workoutImg.setBackgroundResource(R.color.skyblue)
                    workoutImg.setImageDrawable(workout1)
                }

                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    val workout2 = ContextCompat.getDrawable(
                        this,
                        R.mipmap.workout_black_dumbbell100_foreground
                    )
                    workoutTV.setTextColor(ContextCompat.getColor(this, R.color.black))
                    workoutImg.setBackgroundResource(R.color.skyblue)
                    workoutImg.setImageDrawable(workout2)

                    // Create the intent and animation options
                    val intent = Intent(this, WorkoutPg::class.java)
                    val animation = ActivityOptions.makeCustomAnimation(
                        this,
                        R.anim.slide_in_left,
                        R.anim.slide_out_right
                    )

                    // Set the click listener for workoutTV
                    workoutTV.setOnClickListener {
                        startActivity(intent, animation.toBundle())
                    }

                    // Start the activity with animation and finish the current activity
                    startActivity(intent, animation.toBundle())
                    finish()
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


