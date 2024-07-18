package com.example.firebasetutor.beginner

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasetutor.R
import com.example.firebasetutor.databinding.ActivityBeginnerBackPgBinding


class BeginnerBackPg : AppCompatActivity() {


    private lateinit var binding: ActivityBeginnerBackPgBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBeginnerBackPgBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val titleText = findViewById<TextView>(R.id.titleText)
        val categoryText1 = findViewById<TextView>(R.id.categoryText1)
        val categoryText2 = findViewById<TextView>(R.id.categoryText2)
        val workoutDescription = findViewById<TextView>(R.id.workoutDescription)

        val warmup1 = findViewById<TextView>(R.id.warmup1)
        val warmup2 = findViewById<TextView>(R.id.warmup2)
        val warmup3 = findViewById<TextView>(R.id.warmup3)
        val warmup4 = findViewById<TextView>(R.id.warmup4)
        val warmup5 = findViewById<TextView>(R.id.warmup5)
        val warmup6 = findViewById<TextView>(R.id.warmup6)

        val workout1 = findViewById<TextView>(R.id.workout1)
        val workout2 = findViewById<TextView>(R.id.workout2)
        val workout3 = findViewById<TextView>(R.id.workout3)
        val workout4 = findViewById<TextView>(R.id.workout4)

        val startWorkout = findViewById<TextView>(R.id.startWorkout)

        //TOp Part
        titleText.setText("Back Workouts")
        categoryText1.setText("Beginner")
        categoryText2.setText("Back")
        workoutDescription.setText("Strengthening your back muscles helps correct your posture, creating a cycle where good posture reinforces back strength and back strength reinforces good posture. Low-intensity stretching exercises like yoga are particularly helpful in creating good spinal posture")
        //Warm Up
        warmup1.setText("Back")
        warmup2.setText("Back")
        warmup3.setText("Back")
        warmup4.setText("Back")
        warmup5.setText("Back")
        warmup6.setText("Back")
        //Workouts
        workout1.setText("Plank 30sec")
        workout2.setText("Superman 12-15reps")
        workout3.setText("W extension 12-15reps")
        workout4.setText("Australian Pull Up 12-15reps")
        //Start btn
        startWorkout.setText("Start")
    }
}