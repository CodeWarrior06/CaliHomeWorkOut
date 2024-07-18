package com.example.firebasetutor.beginner

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasetutor.databinding.ActivityRestWorkoutD1Binding

@Suppress("DEPRECATION")
class restWorkoutD1 : AppCompatActivity() {

    private lateinit var binding: ActivityRestWorkoutD1Binding

    private lateinit var countdownTimer: CountDownTimer
    private lateinit var countdownTextView: TextView
    private lateinit var pauseButton: Button
    private lateinit var addBtn: Button
    private lateinit var nextButton: Button
    private var isPaused = false
    private var timeLeftInMillis: Long = DEFAULT_TIME_MS // Default time: 1 minute
    private var pausedTime: Long = 0
    private var setValue = ""
    private var isToastShowing = false
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private var hasNavigated = false

    companion object {
        const val DEFAULT_TIME_MS: Long = 30000//5000 // 1 minute in milliseconds
        const val COUNTDOWN_INTERVAL_MS: Long = 1000 // Countdown interval: 1 second
        const val ADDITIONAL_TIME_MS: Long = 15000 // 15 seconds
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestWorkoutD1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()



        initializeViews()

        pauseButton.setOnClickListener {
            togglePauseState()
        }
        nextButton.setOnClickListener {
            // Stop the timer
            countdownTimer.cancel()

            // Navigate to the next activity
            sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            var workoutNumSet = sharedPreferences.getString("Set", "")

            when (workoutNumSet) {
                "1" -> navigateToNextScreen(beginnerPushPullD1W1::class.java, "2 of 3 sets")
                "2" -> navigateToNextScreen(beginnerPushPullD1W1::class.java, "3 of 3 sets")
                "3" -> navigateToNextScreen(beginnerPushPullD1W2::class.java, "1 of 3 sets")
                "4" -> navigateToNextScreen(beginnerPushPullD1W2::class.java, "2 of 3 sets")
                "5" -> navigateToNextScreen(beginnerPushPullD1W2::class.java, "3 of 3 sets")
                "Done" -> navigateToNextScreen(beginnerWorkoutDone::class.java, "")
            }
        }

        addBtn.setOnClickListener {
            if (isPaused) {
             showToast("Cannot add rest time while paused")
            } else {
                addTime()
            }
        }


        startTimer(timeLeftInMillis)

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

    private fun setValueToNextScreen(className: Class<*>, setValue: String) {
        val intent = Intent(this@restWorkoutD1, className).apply {
            putExtra("setValue", setValue)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)  //Must not remove to not have bug in stretchingpage not counting timer
        startActivity(intent)

        finish()
    }

    private fun navigateToNextScreen(className: Class<*>, setValue: String) {
        val intent = Intent(this@restWorkoutD1, className).apply {
            putExtra("setValue", setValue)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) //Must not remove to not have bug in stretchingpage not counting timer
        startActivity(intent)

        finish()
    }
    private fun addTime() {
        countdownTimer.cancel()
        timeLeftInMillis += ADDITIONAL_TIME_MS
        if (isPaused) {
            pausedTime = timeLeftInMillis // Update pausedTime when adding time in paused state
        }
        updateCountdownText()
        startTimer(timeLeftInMillis)
    }


    private fun initializeViews() {
        countdownTextView = binding.reusableRestingTime.countdownTimer
        pauseButton = binding.reusableRestingTime.pauseButton
        nextButton = binding.reusableRestingTime.nextButton
        addBtn = binding.reusableRestingTime.addButton
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
        countdownTimer = object : CountDownTimer(millisUntilFinished, COUNTDOWN_INTERVAL_MS) {
            override fun onTick(millisUntilFinished: Long) {
                if (!isPaused) {
                    timeLeftInMillis = millisUntilFinished
                    updateCountdownText()
                }
            }

            override fun onFinish() {
                navigateToNextActivity()
            }
        }
        countdownTimer.start()
    }

    private fun navigateToNextActivity() {
        if (hasNavigated) return // Prevent multiple navigations
        hasNavigated = true

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val workoutNumSet = sharedPreferences.getString("Set", "")

        when (workoutNumSet) {
            "1" -> navigateToNextScreen(beginnerPushPullD1W1::class.java, "2 of 3 sets")
            "2" -> navigateToNextScreen(beginnerPushPullD1W1::class.java, "3 of 3 sets")
            "3" -> navigateToNextScreen(beginnerPushPullD1W2::class.java, "1 of 3 sets")
            "4" -> navigateToNextScreen(beginnerPushPullD1W2::class.java, "2 of 3 sets")
            "5" -> navigateToNextScreen(beginnerPushPullD1W2::class.java, "3 of 3 sets")
            "Done" -> navigateToNextScreen(beginnerWorkoutDone::class.java, "")
            else -> showToast("Unknown workout number set.")
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



    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        // Do nothing
    }




}