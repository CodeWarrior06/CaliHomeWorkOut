

package com.example.firebasetutor.beginner

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.firebasetutor.R
import com.example.firebasetutor.databinding.ActivityBeginnerPushPullD1W1Binding

@Suppress("DEPRECATION")
class beginnerPushPullD1W1 : AppCompatActivity() {

    private lateinit var binding: ActivityBeginnerPushPullD1W1Binding

    private lateinit var countdownTimer: CountDownTimer
    private lateinit var countdownTextView: TextView
    private lateinit var pauseButton: TextView
    //private lateinit var nextButton: TextView
    private var isPaused = false
    private var timeLeftInMillis: Long = DEFAULT_TIME_MS // Default time: 1 minute
    private var pausedTime: Long = 0
    private var sharedPrefValue = ""
    private var isNull = ""
    private lateinit var setValueText : TextView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var gifIV : ImageView
    private lateinit var setTV : TextView
    private lateinit var workoutTV : TextView
    private lateinit var workoutAmountTV : TextView
    private var gif1 : Int = 0
    private var gif2 : Int = 0
    private lateinit var timeCountdownLL: LinearLayout
    private lateinit var pauseBtnTV: TextView
    private lateinit var continueBtnTV: TextView

    companion object {
        const val DEFAULT_TIME_MS: Long =  60000//5000 // 1 minute in milliseconds

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBeginnerPushPullD1W1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        workoutTV = binding.workoutNameTV
        setValueText = binding.setTV
        workoutAmountTV = binding.workoutAmountTV
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)


        setValue() //FIx ung workout dito para mainput ung every phase ng beginner push pull

        gifChangerForWorkout1()



        if (!isDestroyed && !isFinishing) {
            Glide.with(applicationContext)

                .asGif()
                .load(gif1)
                .fitCenter()
                .into(binding.gifIV)
        }





        initializeViews()


        val getWorkoutName = sharedPreferences.getString("workoutName1", "")
        val getRepCount = sharedPreferences.getString("repCount1", "")

        val a1 = binding.workoutNameTV
        val a2 = binding.workoutAmountTV

        if(getWorkoutName != null){
            a1.text =  getWorkoutName
            a2.text = getRepCount
            Log.d("AnongValue", "getWorkout 1number set is $getWorkoutName.")
            Log.d("AnongValue", "getrepcount  1 number set is $getRepCount.") //cant fix the getshared string is not popping up


        }

        pauseButton.setOnClickListener {
            togglePauseState()
        }

        continueBtnTV.setOnClickListener {
            askPrefVisit()
            nextPg()
        }
        /* nextButton.setOnClickListener {
             // Stop the timer and navigate to the next activity
             countdownTimer.cancel()
             askPrefVisit()
             nextPg()
         }*/

        startTimer(timeLeftInMillis)


    }




    override fun onDestroy() {
        super.onDestroy()
        finish() // Call finish() here to close the previous activity
    }

    private fun gifChangerForWorkout1() {
        val isBeginnerSchedView = sharedPreferences.getString("isBeginnerSchedView", "")
        val isIntermediateSchedView = sharedPreferences.getString("isIntermediateSchedView", "")
        val beginnerPushPullPhase = sharedPreferences.getInt("beginnerPushPullPhase", 0)
        val beginnerLegCorePhase = sharedPreferences.getInt("beginnerLegCorePhase", 0)
        val isWorkout = sharedPreferences.getString("isWorkout", "")
        var workoutNumSet = sharedPreferences.getString("Set", "")
        timeCountdownLL = binding.timeCountdownLL
        pauseBtnTV = binding.pauseBtnTV
        continueBtnTV = binding.continueBtnTV
        if (isBeginnerSchedView == "Yes") {

            if (isWorkout == "PushPull") {
                editor = sharedPreferences.edit()
                when (beginnerPushPullPhase) {
                    0, 1, 2, 3 -> {
                        timeCountdownLL.visibility = View.GONE
                        pauseBtnTV.visibility = View.GONE
                        continueBtnTV.visibility = View.VISIBLE
                        when (workoutNumSet) {
                            "" -> {
                                gif1 = R.drawable.gif_knee_push_up

                            }
                            "1" -> {
                                gif1 = R.drawable.gif_knee_push_up

                            }
                            "2" -> {
                                gif1 = R.drawable.gif_knee_push_up

                            }
                        }
                    }

                    4, 5, 6, 7 -> {
                        timeCountdownLL.visibility = View.GONE
                        pauseBtnTV.visibility = View.GONE
                        continueBtnTV.visibility = View.VISIBLE
                        when (workoutNumSet) {
                            "" -> {
                                gif1 = R.drawable.gif_incline_push_up
                            }
                            "1" -> {

                                gif1 = R.drawable.gif_incline_push_up
                            }
                            "2" -> {

                                gif1 = R.drawable.gif_incline_push_up
                            }
                        }
                    }
                    8, 9, 10, 11 -> {
                        timeCountdownLL.visibility = View.GONE
                        pauseBtnTV.visibility = View.GONE
                        continueBtnTV.visibility = View.VISIBLE
                        when (workoutNumSet) {
                            "" -> gif1 = R.drawable.gif_push_up
                            "1" -> gif1 = R.drawable.gif_push_up
                            "2" -> gif1 = R.drawable.gif_push_up
                        }
                    }
                    else -> {
                        timeCountdownLL.visibility = View.GONE
                        pauseBtnTV.visibility = View.GONE
                        continueBtnTV.visibility = View.VISIBLE
                        when (workoutNumSet) {
                            "" -> gif1 = R.drawable.gif_push_up
                            "1" -> gif1 = R.drawable.gif_push_up
                            "2" -> gif1 = R.drawable.gif_push_up
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
                            "" -> gif1 = R.drawable.gif_diamond_push_up
                            "1" -> gif1 = R.drawable.gif_diamond_push_up
                            "2" -> gif1 = R.drawable.gif_diamond_push_up
                        }
                    }
                    4, 5, 6, 7 -> {
                        timeCountdownLL.visibility = View.GONE
                        pauseBtnTV.visibility = View.GONE
                        continueBtnTV.visibility = View.VISIBLE
                        when (workoutNumSet) {
                            "" -> gif1 = R.drawable.gif_archer_push_up
                            "1" -> gif1 = R.drawable.gif_archer_push_up
                            "2" -> gif1 = R.drawable.gif_archer_push_up
                        }
                    }
                    8, 9, 10, 11 -> {
                        timeCountdownLL.visibility = View.GONE
                        pauseBtnTV.visibility = View.GONE
                        continueBtnTV.visibility = View.VISIBLE
                        when (workoutNumSet) {

                            "" -> gif1 = R.drawable.gif_pseudo_planche_push_up
                            "1" -> gif1 = R.drawable.gif_pseudo_planche_push_up
                            "2" -> gif1 = R.drawable.gif_pseudo_planche_push_up
                        }
                    }
                    else  -> {

                        when (workoutNumSet) {
                            "" -> gif1 = R.drawable.gif_pseudo_planche_push_up
                            "1" -> gif1 = R.drawable.gif_pseudo_planche_push_up
                            "2" -> gif1 = R.drawable.gif_pseudo_planche_push_up
                        }
                    }
                }
            }
        }
    }

    private fun initializeViews() {
        countdownTextView = binding.timeCountdownTV
        workoutAmountTV = binding.workoutAmountTV
        pauseButton = binding.pauseBtnTV
        continueBtnTV = binding.continueBtnTV
        timeCountdownLL = binding.timeCountdownLL
        //nextButton = binding.nextBtnTV




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
        if(beginnerPushPullPhase < 4 || beginnerPushPullPhase > 7){
            pauseTimer()
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
        startActivity(intent)
    }


    private fun askPrefVisit(){

        var workoutNumSet = sharedPreferences.getString("Set", "")
        var beginnerPhase = sharedPreferences.getString("beginnerPhase", "")
        // Log.d("AnongValue", "Workout number set is $workoutNumSet1.")
        if(workoutNumSet == ""){
            editor = sharedPreferences.edit()
            editor.putString("Set", "1")
            editor.commit()
        }
        else if(workoutNumSet == "1"){
            editor = sharedPreferences.edit()
            editor.putString("Set", "2")
            editor.commit()

        }
        else if(workoutNumSet == "2"){
            editor = sharedPreferences.edit()
            editor.putString("Set", "3")
            editor.commit()
        }
        else{
        }
    }


    private fun setValue(){
        //for number of set whenever the intent back to this page
        var workoutNumSet = sharedPreferences.getString("Set", "")
        if(workoutNumSet == "1"){
            val setValue = intent.getStringExtra("setValue")
            setValueText.text = setValue
        }
        else if(workoutNumSet == "2"){
            val setValue = intent.getStringExtra("setValue")
            setValueText.text = setValue
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        // Do nothing
    }



}