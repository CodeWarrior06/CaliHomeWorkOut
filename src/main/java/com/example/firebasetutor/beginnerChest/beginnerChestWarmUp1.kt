package com.example.firebasetutor.beginnerChest

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasetutor.R
import com.example.firebasetutor.beginner.BeginnerChestPg
import com.example.firebasetutor.databinding.ActivityBeginnerChestWarmUp1Binding

class beginnerChestWarmUp1 : AppCompatActivity() {

    private lateinit var binding: ActivityBeginnerChestWarmUp1Binding
    private lateinit var timer: MyCountDownTimer // Declare timer as a property of the activity class

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBeginnerChestWarmUp1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.yesorno_window, null)
        val button = findViewById<TextView>(R.id.askQuit)
        val pauseBtn = findViewById<ImageView>(R.id.pauseBtn)
        val yesbtn = popupView.findViewById<TextView>(R.id.yesBtn)
        val nobtn = popupView.findViewById<TextView>(R.id.noBtn)


        val popupWindow = PopupWindow(
            popupView,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            true
        )


        // Initialize timer
        val countdownText = binding.countdownText
        timer = MyCountDownTimer(countdownText, 60000, 1000)

        //QUIT BTN
        button.setOnClickListener {
            popupWindow.showAsDropDown(button)
        }

        //POP UP BTN
        yesbtn.setOnClickListener {
            val intent = Intent(this, BeginnerChestPg::class.java)
            startActivity(intent)
        }
        nobtn.setOnClickListener {
            popupWindow.dismiss()
        }

        binding.nextBtn.setOnClickListener{

        }

        // Start the countdown timer
        timer.start()

        // PAUSE BTN
        pauseBtn.setOnClickListener {
            // Toggle pause state of the timer
            timer.togglePause()
        }
    }


    // Inner class for the countdown timer
    // Inner class for the countdown timer


    // Inner class for the countdown timer
    class MyCountDownTimer(
        private val countdownText: TextView,
        millisInFuture: Long,
        countDownInterval: Long
    ) : CountDownTimer(millisInFuture, countDownInterval) {

        private var isPaused = false
        private var timeRemaining = millisInFuture

        // Toggle pause state
        fun togglePause() {
            isPaused = !isPaused
            if (!isPaused) {
                startTimer(timeRemaining)
            } else {
                cancel()
            }
        }






        private fun startTimer(millisInFuture: Long) {
            start()
        }

        override fun onTick(millisUntilFinished: Long) {
            if (!isPaused) {
                timeRemaining = millisUntilFinished
                val secondsRemaining = millisUntilFinished / 1000
                countdownText.text = secondsRemaining.toString()
            }
        }

        override fun onFinish() {
            // Perform actions when the timer finishes, e.g., start next exercise
        }
    }
}