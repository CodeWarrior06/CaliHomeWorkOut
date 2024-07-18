package com.example.firebasetutor.beginner

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasetutor.databinding.ActivityResttimeBinding

@Suppress("DEPRECATION")
class RestTimeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResttimeBinding
    private lateinit var countdownTimer: CountDownTimer
    private lateinit var countdownTextView: TextView
    private lateinit var pauseButton: Button
    private lateinit var nextButton: Button
    private var isPaused = false
    private var timeLeftInMillis: Long = DEFAULT_TIME_MS // Default time: 1 minute
    private var pausedTime: Long = 0

    companion object {
        const val DEFAULT_TIME_MS: Long =  30000//5000 // 1 minute in milliseconds
        const val COUNTDOWN_INTERVAL_MS: Long = 1000 // Countdown interval: 1 second
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResttimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeViews()

        pauseButton.setOnClickListener {
            togglePauseState()
        }
        nextButton.setOnClickListener {
            // Stop the timer and navigate to the next activity
            countdownTimer.cancel()

            nextPg()
        }

        startTimer(timeLeftInMillis)
    }

    private fun initializeViews() {
        countdownTextView = binding.countdownTimerTV
        pauseButton = binding.pauseButton
        nextButton = binding.nextButton
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
                // Countdown finished
                nextPg()
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

    private fun nextPg() {
        val intent = Intent(this, stretchingPg::class.java)
        startActivity(intent)

        finish()
    }
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        // Do nothing
    }

}
