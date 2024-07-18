package com.example.firebasetutor.beginner

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasetutor.beginnerChest.beginnerChestStart
import com.example.firebasetutor.databinding.ActivityBeginnerChestPgBinding

class BeginnerChestPg : AppCompatActivity() {

    private lateinit var binding: ActivityBeginnerChestPgBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBeginnerChestPgBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startWorkout.setOnClickListener{
            val intent = Intent(this, beginnerChestStart::class.java)
            startActivity(intent)
        }
    }
}