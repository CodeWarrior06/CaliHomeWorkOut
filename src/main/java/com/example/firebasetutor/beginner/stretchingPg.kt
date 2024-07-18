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
import com.example.firebasetutor.databinding.ActivityStretchingPgBinding

private lateinit var binding: ActivityStretchingPgBinding
private lateinit var sharedPreferences: SharedPreferences
private lateinit var editor: SharedPreferences.Editor
private lateinit var countdownTimer: CountDownTimer
private lateinit var countdownTextView: TextView
private lateinit var pauseButton: TextView
private lateinit var nextButton: TextView
private lateinit var gifIV : ImageView
private lateinit var workoutTV :TextView
private lateinit var durationTV :TextView
private lateinit var workoutProgressTV :TextView
private var isPaused = false
//private var timeLeftInMillis: Long = 30000 //30sec
private var timeLeftInMillis: Long = 30000//5000 //5sec
private var pausedTime: Long = 0
private var isNextButton = false

class stretchingPg : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStretchingPgBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        workoutTV = binding.stretchingPg.workoutNameTV
       durationTV = binding.stretchingPg.setTV
        workoutProgressTV = binding.stretchingPg.workoutAmountTV

        workoutTV.text = "Arm Circle (Forward)"
        durationTV.text = "30 sec"
        workoutProgressTV.text = "On Progress..."





        initializeViews()

        pauseButton.setOnClickListener {
            togglePauseState()
        }
        /*
        nextButton.setOnClickListener {
            // Stop the timer and navigate to the next activity

            countdownTimer.cancel()
            if(isNextButton == false){

                val toast = Toast.makeText(applicationContext, "Click resume to be clickable", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 200) // Adjust Y offset to ensure it's at the bottom
                toast.show()
            }
            else{
                nextPg()
            }

        }

         */

        startTimer(timeLeftInMillis)

        gifUpdate()


    }


    private fun initializeViews() {

        countdownTextView = binding.stretchingPg.timeCountdownTV
        pauseButton = binding.stretchingPg.pauseBtnTV
        //nextButton =  binding.stretchingPg.nextBtnTV
    }

    private fun togglePauseState() {
        isPaused = !isPaused

        if (isPaused) {
            pauseButton.text = "Resume"
            pauseTimer()
            isNextButton = false

            // Show toast with custom Y offset (100 pixels lower)

        } else {
            pauseButton.text = "Pause"
            resumeTimer()
            isNextButton = true

            // Show toast with custom Y offset (100 pixels lower)

        }
    }



    /*
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
    */
    private fun startTimer(millisUntilFinished: Long) {
        val stretchingPhase = sharedPreferences.getInt("stretchingPhase", 0)
        countdownTimer = object : CountDownTimer(millisUntilFinished, 1000) {
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
        val stretchingPhase = sharedPreferences.getInt("stretchingPhase", 0)

        if(stretchingPhase < 9 ){

            // Reset the timer to 30 seconds


            // Increment the stretching phase
            val nextPhase = stretchingPhase + 1
            editor.putInt("stretchingPhase", nextPhase)
            editor.commit()

            // Update UI
            updateCountdownText()
            gifUpdate()
            startTimer(30000) //countdown to be changed to 30s

                    if(stretchingPhase >= 8){
                        countdownTimer.cancel()
                        sharedPrefReset()
                        val intent = Intent(this@stretchingPg, BeginnerPhase1CardioTime ::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                        finish()
                    }






        }
        else{
            // Navigate to the next activity



        }
    }

    private fun sharedPrefReset(){
        var sphase = 0
        editor = sharedPreferences.edit()
        editor.putInt("stretchingPhase", sphase)
        editor.commit()
    }


    private fun gifUpdate(){
        val stretchingPhase1 = sharedPreferences.getInt("stretchingPhase", 0)   //Added the cant skip next if paused, only if you are resume can be clicked.. need to fix is when always skip in workout it suddenly pop up the activity trhat is done already
        when(stretchingPhase1){
            0 ->{
                workoutTV.text = "Arm Circle (Forward)"
                durationTV.text = "30 sec"
                Glide.with(applicationContext)

                    .load(R.drawable.gif_arm_circle_forward)
                    .into(binding.stretchingPg.gifIV)
            }

            1 ->  {
                workoutTV.text = "Arm Circle (Backward)"
                durationTV.text = "30 sec"
                Glide.with(applicationContext)

                    .load(R.drawable.gif_arm_circle_backward)
                    .into(binding.stretchingPg.gifIV)
            }
            2 ->  {
                workoutTV.text = "Arm Raise Stretching"
                durationTV.text = "30 sec"
                Glide.with(applicationContext)

                    .load(R.drawable.gif_arm_raise_stretching)
                    .into(binding.stretchingPg.gifIV)
            }
            3 ->  {
                workoutTV.text = "Front Wrist Forward"
                durationTV.text = "30 sec"
                Glide.with(applicationContext)

                    .load(R.drawable.gif_front_wrist_forward)
                    .into(binding.stretchingPg.gifIV)
            }
            4 ->  {
                workoutTV.text = "Front Wrist Rotation"
                durationTV.text = "30 sec"
                Glide.with(applicationContext)

                    .load(R.drawable.gif_front_wrist_rotation)
                    .into(binding.stretchingPg.gifIV)
            }

            5 ->  {
                workoutTV.text = "Shoulder Circle (Forward)"
                durationTV.text = "30 sec"
                Glide.with(applicationContext)

                    .asGif()
                    .load(R.drawable.gif_shoulder_circle_forward)
                    .fitCenter()
                    .into(binding.stretchingPg.gifIV)
            }
            6 ->  {
                workoutTV.text = "Shoulder Circle (Backward)"
                durationTV.text = "30 sec"
                Glide.with(applicationContext)
                    .asGif()

                    .load(R.drawable.gif_shoulder_circle_backward)
                    .fitCenter()
                    .into(binding.stretchingPg.gifIV)
            }
            7 ->  {
                workoutTV.text = "Side Arm Stretching (To Right)"
                durationTV.text = "30 sec"
                Glide.with(applicationContext)

                    .load(R.drawable.gif_side_arm_stretching_right)
                    .into(binding.stretchingPg.gifIV)
            }
            8 ->  {
                workoutTV.text = "Side Arm Stretching (To Left)"
                durationTV.text = "30 sec"
                if (!isDestroyed && !isFinishing) {
                    Glide.with(applicationContext)

                        .load(R.drawable.gif_side_arm_stretching_left)
                        .into(binding.stretchingPg.gifIV)
                }


            }
            else -> {


            }

        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        // Do nothing
    }

}