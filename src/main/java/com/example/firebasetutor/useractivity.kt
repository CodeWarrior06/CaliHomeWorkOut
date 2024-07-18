package com.example.firebasetutor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasetutor.databinding.ActivityUseractivityBinding

class useractivity : AppCompatActivity() {


    private lateinit var binding : ActivityUseractivityBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUseractivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val foodName1 = intent.getStringExtra("foodName")
        val foodGram1 = intent.getStringExtra("foodGram")
        val foodCalorie1 = intent.getStringExtra("foodCalorie")
        val foodCarb1 = intent.getStringExtra("foodCarb")
        val foodProtein1 = intent.getStringExtra("foodProtein")
        val foodFat1 = intent.getStringExtra("foodFat")
        val foodImage1 = intent.getIntExtra("foodImage", R.drawable.food_apple)

        binding.foodName.text = foodName1 ?: ""
        binding.foodGram.text = foodGram1 ?: ""
        binding.foodCalorie.text = foodCalorie1 ?: ""
        binding.foodProtein.text = foodProtein1 ?: ""
        binding.foodFat.text = foodFat1 ?: ""
        binding.foodCarb.text = foodCarb1 ?: ""
        binding.foodImage.setImageResource(foodImage1)


    }
}