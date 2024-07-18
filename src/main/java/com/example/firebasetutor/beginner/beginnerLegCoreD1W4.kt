package com.example.firebasetutor.beginner

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.firebasetutor.R
import com.example.firebasetutor.databinding.ActivityBeginnerLegCoreD1W4Binding

class beginnerLegCoreD1W4 : AppCompatActivity() {

    private lateinit var binding: ActivityBeginnerLegCoreD1W4Binding

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

    companion object {
        const val DEFAULT_TIME_MS: Long = 60000//5000 // 1 minute in milliseconds

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBeginnerLegCoreD1W4Binding.inflate(layoutInflater)
        setContentView(binding.root)

        workoutTV = binding.includedBeginnerLegCoreD1W4.workoutNameTV
        setValueText = binding.includedBeginnerLegCoreD1W4.setTV
        workoutAmountTV = binding.includedBeginnerLegCoreD1W4.workoutAmountTV
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)


        setValue() //FIx ung workout dito para mainput ung every phase ng beginner push pull

        gifChangerForWorkout1()

        Glide.with(this)
            .asGif()
            .load(gif1)
            .fitCenter()
            .into(binding.includedBeginnerLegCoreD1W4.gifIV)


        initializeViews()


        val getWorkoutName = sharedPreferences.getString("workoutName4", "")
        val getRepCount = sharedPreferences.getString("repCount4", "")

        val a1 = binding.includedBeginnerLegCoreD1W4.workoutNameTV
        val a2 = binding.includedBeginnerLegCoreD1W4.workoutAmountTV

        if(getWorkoutName != null){
            a1.text =  getWorkoutName
            a2.text = getRepCount
            Log.d("AnongValue", "getWorkout 1number set is $getWorkoutName.")
            Log.d("AnongValue", "getrepcount  1 number set is $getRepCount.") //cant fix the getshared string is not popping up


        }

        pauseButton.setOnClickListener {
            togglePauseState()
        }
        /*nextButton.setOnClickListener {
            // Stop the timer and navigate to the next activity
            countdownTimer.cancel()
            askPrefVisit()
            nextPg()
        }*/

        startTimer(timeLeftInMillis)


    }


    private fun gifChangerForWorkout1() {
        val isBeginnerSchedView = sharedPreferences.getString("isBeginnerSchedView", "")
        val isIntermediateSchedView = sharedPreferences.getString("isIntermediateSchedView", "")

        val beginnerLegCorePhase = sharedPreferences.getInt("beginnerLegCorePhase", 0)
        val intermediateLegCorePhase = sharedPreferences.getInt("intermediateLegCorePhase", 0)

        val isWorkout = sharedPreferences.getString("isWorkout", "")
        var workoutNumSet = sharedPreferences.getString("Set", "")

        if (isBeginnerSchedView == "Yes") {

            if (isWorkout == "LegCore") {
                editor = sharedPreferences.edit()
                when(beginnerLegCorePhase){
                    0, 1, 2, 3, 4, 5 ->{
                        when(workoutNumSet){
                            "9" -> gif1 = R.drawable.gif_squat
                            "10" -> gif1 = R.drawable.gif_squat
                            "11" -> gif1 = R.drawable.gif_squat
                        }
                    }
                   else ->{
                        when(workoutNumSet){
                            "9" -> gif1 = R.drawable.gif_squat
                            "10" -> gif1 = R.drawable.gif_squat
                            "11" -> gif1 = R.drawable.gif_squat
                        }
                    }
                }

            }
        }
        else if(isIntermediateSchedView == "Yes"){
            if (isWorkout == "LegCore") {
                editor = sharedPreferences.edit()
                when(intermediateLegCorePhase){
                    0, 1, 2, 3, 4, 5 ->{
                        when(workoutNumSet){
                            "9" -> gif1 = R.drawable.gif_pistol_squat

                            "10" -> gif1 = R.drawable.gif_pistol_squat
                            "11" -> gif1 = R.drawable.gif_pistol_squat
                        }
                    }
                    else ->{
                        when(workoutNumSet){
                            "9" -> gif1 = R.drawable.gif_pistol_squat

                            "10" -> gif1 = R.drawable.gif_pistol_squat
                            "11" -> gif1 = R.drawable.gif_pistol_squat
                        }
                    }
                }








            }
        }
    }

    private fun initializeViews() {
        countdownTextView = binding.includedBeginnerLegCoreD1W4.timeCountdownTV
        workoutAmountTV = binding.includedBeginnerLegCoreD1W4.workoutAmountTV
        pauseButton = binding.includedBeginnerLegCoreD1W4.pauseBtnTV
        //nextButton = binding.includedBeginnerLegCoreD1W4.nextBtnTV




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
        val intent = Intent(this, restWorkoutD2::class.java)
        startActivity(intent)
        finish()
    }


    private fun askPrefVisit(){

        var workoutNumSet = sharedPreferences.getString("Set", "")
        var beginnerPhase = sharedPreferences.getString("beginnerPhase", "")
        // Log.d("AnongValue", "Workout number set is $workoutNumSet1.")
        if(workoutNumSet == "9"){
            editor = sharedPreferences.edit()
            editor.putString("Set", "10")
            editor.commit()
        }
        else if(workoutNumSet == "10"){
            editor = sharedPreferences.edit()
            editor.putString("Set", "11")
            editor.commit()

        }
        else if(workoutNumSet == "11"){
            editor = sharedPreferences.edit()
            editor.putString("Set", "12")
            editor.commit()
        }
        else{
        }
    }


    private fun setValue(){
        //for number of set whenever the intent back to this page
        var workoutNumSet = sharedPreferences.getString("Set", "")
        when(workoutNumSet){
            "", "3", "6", "9", "12", "15" -> setValueText.setText(R.string.firstSet)
            "1", "4", "7", "10", "13", "16" -> setValueText.setText(R.string.secondSet)
            "2", "5", "8", "11", "14", "17" -> setValueText.setText(R.string.thirdSet)
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        // Do nothing
    }



}