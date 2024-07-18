package com.example.firebasetutor.beginner

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasetutor.databinding.ActivityRestWorkoutD2Binding

@Suppress("DEPRECATION")
class restWorkoutD2 : AppCompatActivity() {

    private lateinit var binding: ActivityRestWorkoutD2Binding

    private lateinit var countdownTimer: CountDownTimer
    private lateinit var countdownTextView: TextView
    private lateinit var pauseButton: Button
    private lateinit var nextButton: Button
    private var isPaused = false
    private var timeLeftInMillis: Long = DEFAULT_TIME_MS // Default time: 1 minute
    private var pausedTime: Long = 0
    private var setValue = ""

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    companion object {
        const val DEFAULT_TIME_MS: Long = 30000//5000 // 1 minute in milliseconds
        const val COUNTDOWN_INTERVAL_MS: Long = 1000 // Countdown interval: 1 second
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestWorkoutD2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()



        initializeViews()

        pauseButton.setOnClickListener {
            togglePauseState()
        }
        nextButton.setOnClickListener {
            // Stop the timer and navigate to the next activity
            countdownTimer.cancel()
            sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            var workoutNumSet = sharedPreferences.getString("Set", "")

            val intentToBeginnerLegCoreD1W1 = Intent(this@restWorkoutD2, beginnerLegCoreD1W1::class.java)
            val intentToBeginnerLegCoreD1W2 = Intent(this@restWorkoutD2, beginnerLegCoreD1W2::class.java)
            val intentToBeginnerLegCoreD1W3 = Intent(this@restWorkoutD2, beginnerLegCoreD1W3::class.java)
            val intentToBeginnerLegCoreD1W4 = Intent(this@restWorkoutD2, beginnerLegCoreD1W4::class.java)
            val intentToBeginnerLegCoreD1W5= Intent(this@restWorkoutD2, beginnerLegCoreD1W5::class.java)
            val intentToBeginnerLegCoreD1W6= Intent(this@restWorkoutD2, beginnerLegCoreD1W6::class.java)
            val intentToBeginnerDone = Intent(this@restWorkoutD2, beginnerWorkoutDone::class.java)


            when (workoutNumSet) {
                "","1", "2" -> {

                    startActivity(intentToBeginnerLegCoreD1W1 )

                    finish()
                }
                "3", "4", "5" -> {


                    startActivity(intentToBeginnerLegCoreD1W2 )

                    finish()
                }
                "6", "7", "8"-> {


                    startActivity(intentToBeginnerLegCoreD1W3 )

                    finish()
                }
                "9", "10", "11" -> {

                    startActivity(intentToBeginnerLegCoreD1W4 )


                    finish()
                }
                "12", "13", "14" -> {

                    startActivity(intentToBeginnerLegCoreD1W5 )

                    finish()
                }
                "15", "16", "17" -> {

                    startActivity(intentToBeginnerLegCoreD1W6)

                    finish()
                }
                "Done" -> {

                    startActivity(intentToBeginnerDone)

                    finish()
                }
            }

        }

        startTimer(timeLeftInMillis)

    }






    private fun initializeViews() {
        countdownTextView = binding.reusableRestingTimeLegCore1.countdownTimer
        pauseButton = binding.reusableRestingTimeLegCore1.pauseButton
        //nextButton = binding.reusableRestingTimeLegCore1.nextButton
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
                // Countdown finished
                sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                var workoutNumSet = sharedPreferences.getString("Set", "")
                val intentToBeginnerLegCoreD1W1 = Intent(this@restWorkoutD2, beginnerLegCoreD1W1::class.java)
                val intentToBeginnerLegCoreD1W2 = Intent(this@restWorkoutD2, beginnerLegCoreD1W2::class.java)
                val intentToBeginnerLegCoreD1W3 = Intent(this@restWorkoutD2, beginnerLegCoreD1W3::class.java)
                val intentToBeginnerLegCoreD1W4 = Intent(this@restWorkoutD2, beginnerLegCoreD1W4::class.java)
                val intentToBeginnerLegCoreD1W5= Intent(this@restWorkoutD2, beginnerLegCoreD1W5::class.java)
                val intentToBeginnerLegCoreD1W6= Intent(this@restWorkoutD2, beginnerLegCoreD1W6::class.java)
                val intentToBeginnerDone = Intent(this@restWorkoutD2, beginnerWorkoutDone::class.java)


                when (workoutNumSet) {
                    "","1", "2" -> {


                        startActivity(intentToBeginnerLegCoreD1W1 )


                        finish()
                    }
                    "3", "4", "5" -> {

                        startActivity(intentToBeginnerLegCoreD1W2 )

                        finish()
                    }
                    "6", "7", "8"-> {


                        startActivity(intentToBeginnerLegCoreD1W3 )

                        finish()
                    }
                    "9", "10", "11" -> {


                        startActivity(intentToBeginnerLegCoreD1W4 )

                        finish()
                    }
                    "12", "13", "14" -> {


                        startActivity(intentToBeginnerLegCoreD1W5 )

                        finish()
                    }
                    "15", "16", "17" -> {


                        startActivity(intentToBeginnerLegCoreD1W6)

                        finish()
                    }
                    "Done" -> {


                        startActivity(intentToBeginnerDone)

                        finish()
                    }
                }



            }
        }
        countdownTimer.start()
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



    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        // Do nothing
    }




}