package com.example.firebasetutor.inputDetails

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasetutor.databinding.ActivityAskNumberSchedBinding


class askNumberSched : AppCompatActivity() {

    private lateinit var binding: ActivityAskNumberSchedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAskNumberSchedBinding.inflate(layoutInflater)
        setContentView(binding.root)

            //BACK
        binding.askNumberSchedBckBtn.setOnClickListener {
            val intent = Intent(this, activityFactor::class.java)
            startActivity(intent)
        }
            //NEXT
        binding.nextBtnAskSched.setOnClickListener {
            val intent = Intent(this, Thankyou::class.java)
            startActivity(intent)
        }
    }
}