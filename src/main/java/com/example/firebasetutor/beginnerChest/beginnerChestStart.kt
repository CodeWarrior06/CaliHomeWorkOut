package com.example.firebasetutor.beginnerChest

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasetutor.databinding.ActivityBeginnerChestStartBinding


class beginnerChestStart : AppCompatActivity() {

    private lateinit var binding: ActivityBeginnerChestStartBinding
    private lateinit var startBtn : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBeginnerChestStartBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}
