package com.example.firebasetutor.beginner

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.firebasetutor.R
import com.example.firebasetutor.databinding.ActivityBeginnerPhase1CardioTimeBinding

class BeginnerPhase1CardioTime : AppCompatActivity() {

    private lateinit var binding: ActivityBeginnerPhase1CardioTimeBinding

    private lateinit var countdownTimer: CountDownTimer
    private lateinit var countdownTextView: TextView
    private lateinit var pauseButton: TextView
    private lateinit var nextButton: TextView
    private var isPaused = false
    private var timeLeftInMillis: Long = DEFAULT_TIME_MS // Default time: 1 minute
    private var pausedTime: Long = 0
    private lateinit var setValueText : TextView
    private lateinit var sharedPreferences: SharedPreferences               //BUGGY WHEN ON WORKOUT PUSH PULL SUDDENLY GET INTENT ALWAYS BACK TO THE STRETCHING DONE
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var gifIV : ImageView
    private var lightCardioGIF: Int = 0

    companion object {
        //const val DEFAULT_TIME_MS: Long = 600000 // 10 minute in milliseconds
        const val DEFAULT_TIME_MS: Long =  60000 // 5 sec in milliseconds //to be changed timer to 10000

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBeginnerPhase1CardioTimeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        binding.lightCardio.workoutNameTV.text = "Light Cardio"
        binding.lightCardio.setTV.text = "10 minutes"
        binding.lightCardio.workoutAmountTV.text = "On Progress..."




        gifIV = binding.lightCardio.gifIV
        var selectedLightCardio = sharedPreferences.getInt("selectedLightCardio", 0)

        when(selectedLightCardio){
            1->lightCardioGIF = R.drawable.gif_butt_kicks
            2->lightCardioGIF = R.drawable.gif_high_knees
            3->lightCardioGIF = R.drawable.gif_jogging
            4->lightCardioGIF = R.drawable.jumping_jacks
            5->lightCardioGIF = R.drawable.gif_jumping_rope
        }

        Glide.with(applicationContext)
            .asGif()
            .load(lightCardioGIF)
            .fitCenter()
            .into(gifIV)

        initializeViews()


        pauseButton.setOnClickListener {
            togglePauseState()
        }
        /*
        nextButton.setOnClickListener {
            // Stop the timer and navigate to the next activity
            countdownTimer.cancel()

            nextPg()
        }
*/
        startTimer(timeLeftInMillis)


    }

    private fun selectedGif(){

    }



    private fun initializeViews() {

        countdownTextView = binding.lightCardio.timeCountdownTV
        pauseButton = binding.lightCardio.pauseBtnTV
        //nextButton =  binding.lightCardio.nextBtnTV
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
        val intent = Intent(this, thankyouStretching::class.java)
        startActivity(intent)
        finish()
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        // Do nothing
    }












}