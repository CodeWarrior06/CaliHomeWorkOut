package com.example.firebasetutor.beginner

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.firebasetutor.R
import com.example.firebasetutor.databinding.ActivityBeginnerPushPullD1W2Binding

class beginnerPushPullD1W2 : AppCompatActivity() {

    private lateinit var binding: ActivityBeginnerPushPullD1W2Binding

    private lateinit var countdownTimer: CountDownTimer
    private lateinit var countdownTextView: TextView
    private lateinit var pauseButton: TextView
    //private lateinit var nextButton: TextView
    private var isPaused = false
    private var timeLeftInMillis: Long = DEFAULT_TIME_MS // Default time: 1 minute
    private var pausedTime: Long = 0
    private lateinit var setValueText : TextView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private var gif1 : Int = 0
    private lateinit var timeCountdownLL: LinearLayout
    private lateinit var pauseBtnTV: TextView
    private lateinit var continueBtnTV: TextView

    companion object {
        const val DEFAULT_TIME_MS: Long =  60000//5000 // 1 minute in milliseconds

    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBeginnerPushPullD1W2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)




        setValueText = binding.includeBeginnerPushPullD1W2.setTV
        setValue()


        var gifIV = binding.includeBeginnerPushPullD1W2.gifIV

        gifChangerForWorkout2()
        Glide.with(applicationContext)


            .asGif()
            .load(gif1)
            .fitCenter()
            .into(gifIV)

        initializeViews()

        val getWorkoutName = sharedPreferences.getString("workoutName2", "")
        val getRepCount = sharedPreferences.getString("repCount2", "")

        val a1 = binding.includeBeginnerPushPullD1W2.workoutNameTV
        val a2 = binding.includeBeginnerPushPullD1W2.workoutAmountTV



        if (getWorkoutName != null) {
            a1.text = getWorkoutName
            a2.text = getRepCount
            Log.d("AnongValue", "getWorkout 2number set is $getWorkoutName.")
            Log.d(
                "AnongValue",
                "getrepcount  2 number set is $getRepCount."
            ) //cant fix the getshared string is not popping up


            pauseButton.setOnClickListener {
                togglePauseState()
            }
            continueBtnTV.setOnClickListener {

                askPrefVisit()
                nextPg()
            }


            /*nextButton.setOnClickListener {
                // Stop the timer and navigate to the next activity
                countdownTimer.cancel()
                askPrefVisit()
                nextPg()
            }
            */
            startTimer(timeLeftInMillis)


        }
    }

    private fun gifChangerForWorkout2() {
        val isBeginnerSchedView = sharedPreferences.getString("isBeginnerSchedView", "")
        val isIntermediateSchedView = sharedPreferences.getString("isIntermediateSchedView", "")
        val beginnerPushPullPhase = sharedPreferences.getInt("beginnerPushPullPhase", 0)
        val beginnerLegCorePhase = sharedPreferences.getInt("beginnerLegCorePhase", 0)
        val isWorkout = sharedPreferences.getString("isWorkout", "")
        var workoutNumSet = sharedPreferences.getString("Set", "")
        timeCountdownLL = binding.includeBeginnerPushPullD1W2.timeCountdownLL
        pauseBtnTV = binding.includeBeginnerPushPullD1W2.pauseBtnTV
        continueBtnTV = binding.includeBeginnerPushPullD1W2.continueBtnTV

        if (isBeginnerSchedView == "Yes") {

            if (isWorkout == "PushPull") {
                editor = sharedPreferences.edit()
                when (beginnerPushPullPhase) {
                    0, 1, 2, 3 -> {
                        timeCountdownLL.visibility = View.GONE
                        pauseBtnTV.visibility = View.GONE
                        continueBtnTV.visibility = View.VISIBLE
                        when (workoutNumSet) {
                            "3" -> gif1 = R.drawable.gif_australian_pull_up
                            "4" -> gif1 = R.drawable.gif_australian_pull_up
                            "5" -> gif1 = R.drawable.gif_australian_pull_up
                        }
                    }

                    4, 5, 6, 7 -> {
                        timeCountdownLL.visibility = View.VISIBLE
                        pauseBtnTV.visibility = View.VISIBLE
                        continueBtnTV.visibility = View.GONE

                        when (workoutNumSet) {
                            "3" -> gif1 = R.drawable.gif_dead_hang
                            "4" -> gif1 = R.drawable.gif_dead_hang
                            "5" -> gif1 = R.drawable.gif_dead_hang
                        }
                    }

                    8, 9, 10, 11 -> {
                        timeCountdownLL.visibility = View.GONE
                        pauseBtnTV.visibility = View.GONE
                        continueBtnTV.visibility = View.VISIBLE
                        when (workoutNumSet) {
                            "3" -> gif1 = R.drawable.gif_band_assisted_pull_ups
                            "4" -> gif1 = R.drawable.gif_band_assisted_pull_ups
                            "5" -> gif1 = R.drawable.gif_band_assisted_pull_ups
                        }
                    }
                    else ->{

                        when (workoutNumSet) {
                            "3" -> gif1 = R.drawable.gif_band_assisted_pull_ups
                            "4" -> gif1 = R.drawable.gif_band_assisted_pull_ups
                            "5" -> gif1 = R.drawable.gif_band_assisted_pull_ups
                        }
                    }

                }
            }
        }
        else if(isIntermediateSchedView == "Yes"){
            if (isWorkout == "PushPull") {
                editor = sharedPreferences.edit()
                when (beginnerPushPullPhase) {
                    0, 1, 2, 3 -> {
                        timeCountdownLL.visibility = View.GONE
                        pauseBtnTV.visibility = View.GONE
                        continueBtnTV.visibility = View.VISIBLE
                        when (workoutNumSet) {
                            "3" -> gif1 = R.drawable.gif_pull_up
                            "4" -> gif1 = R.drawable.gif_pull_up
                            "5" -> gif1 = R.drawable.gif_pull_up
                        }
                    }
                    4, 5, 6, 7 -> {
                        timeCountdownLL.visibility = View.GONE
                        pauseBtnTV.visibility = View.GONE
                        continueBtnTV.visibility = View.VISIBLE
                        when (workoutNumSet) {
                            "3" -> gif1 = R.drawable.gif_wide_grip_pullup
                            "4" -> gif1 = R.drawable.gif_wide_grip_pullup
                            "5" -> gif1 = R.drawable.gif_wide_grip_pullup
                        }

                    }
                    8, 9, 10, 11 -> {
                        timeCountdownLL.visibility = View.GONE
                        pauseBtnTV.visibility = View.GONE
                        continueBtnTV.visibility = View.VISIBLE
                        when (workoutNumSet) {
                            "3" -> gif1 = R.drawable.gif_muscle_up
                            "4" -> gif1 = R.drawable.gif_muscle_up
                            "5" -> gif1 = R.drawable.gif_muscle_up
                        }
                    }
                    else -> {
                        when (workoutNumSet) {
                            "3" -> gif1 = R.drawable.gif_muscle_up
                            "4" -> gif1 = R.drawable.gif_muscle_up
                            "5" -> gif1 = R.drawable.gif_muscle_up
                        }
                    }
                }
            }

        }
    }



    private fun initializeViews() {

        countdownTextView = binding.includeBeginnerPushPullD1W2.timeCountdownTV
        pauseButton = binding.includeBeginnerPushPullD1W2.pauseBtnTV
        pauseButton = binding.includeBeginnerPushPullD1W2.pauseBtnTV
        continueBtnTV = binding.includeBeginnerPushPullD1W2.continueBtnTV
        timeCountdownLL = binding.includeBeginnerPushPullD1W2.timeCountdownLL
        //nextButton =  binding.includeBeginnerPushPullD1W2.nextBtnTV
    }




    private fun togglePauseState() {
        isPaused = !isPaused
        if (isPaused) {
            pauseButton.text = "Resume"
            pauseTimer()
        } else {
            pauseButton.text = "Pause"
            resumeTimer()
        }
    }

    private fun startTimer(millisUntilFinished: Long) {
        countdownTimer = object : CountDownTimer(millisUntilFinished,
            RestTimeActivity.COUNTDOWN_INTERVAL_MS
        ) {
            override fun onTick(millisUntilFinished: Long) {
                if (!isPaused) {
                    timeLeftInMillis = millisUntilFinished
                    updateCountdownText()
                }
            }

            override fun onFinish() {
                askPrefVisit()
                nextPg()
            }
        }
        countdownTimer.start()
        val beginnerPushPullPhase = sharedPreferences.getInt("beginnerPushPullPhase", 0)
      //  if(beginnerPushPullPhase < 4 || beginnerPushPullPhase > 7){
      //      pauseTimer()
      //  }
        when(beginnerPushPullPhase){
            4, 5, 6, 7 ->{

            }
            else->{
                togglePauseState()
            }

        }
    }

    private fun pauseTimer() {
        countdownTimer.cancel()
        pausedTime = timeLeftInMillis
    }

    private fun resumeTimer() {
        startTimer(pausedTime)
    }

    private fun updateCountdownText() {
        val minutes = (timeLeftInMillis / 1000) / 60
        val seconds = (timeLeftInMillis / 1000) % 60

        val timeLeftFormatted = String.format("%02d:%02d", minutes, seconds)
        countdownTextView.text = timeLeftFormatted
    }

    private fun nextPg() {
        val intent = Intent(this, restWorkoutD1::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        startActivity(intent)
        finish()
    }

    // In the restWorkoutD1 activity
    override fun onDestroy() {
        super.onDestroy()
        finish() // Call finish() here to close the previous activity
    }



    private fun askPrefVisit(){

        var workoutNumSet = sharedPreferences.getString("Set", "")

        // Log.d("AnongValue", "Workout number set is $workoutNumSet1.")
        if(workoutNumSet == "3"){
            editor = sharedPreferences.edit()
            editor.putString("Set", "4")
            editor.commit()


        }
        else if(workoutNumSet == "4"){
            editor = sharedPreferences.edit()
            editor.putString("Set", "5")
            editor.commit()

        }
        else if(workoutNumSet == "5"){
            editor = sharedPreferences.edit()
            editor.putString("Set", "Done")    //last set of 2nd workout
            editor.commit()
        }
        else{

        }


    }



    private fun setValue() {
        var workoutNumSet = sharedPreferences.getString("Set", "")

        if (workoutNumSet == "3") {  //This is 1/3 set that countdown page put in string
            val setValue = intent.getStringExtra("setValue")
            setValueText.text = setValue
        } else if (workoutNumSet == "4") { //This is 2/3 set that countdown page put in string
            val setValue = intent.getStringExtra("setValue")
            setValueText.text = setValue
        }
        else if (workoutNumSet == "5") { //This is 3/3 set that countdown page put in string
            val setValue = intent.getStringExtra("setValue")
            setValueText.text = setValue
        }
    }


    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        // Do nothing
    }
}