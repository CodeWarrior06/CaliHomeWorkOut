package com.example.firebasetutor

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.firebasetutor.databinding.ActivityNutritionPgBinding
import com.example.firebasetutor.workoutModule.WorkoutPg

private lateinit var binding: ActivityNutritionPgBinding
private lateinit var  userArrayList : ArrayList<User>
private lateinit var routineImg: ImageView
private lateinit var workoutImg: ImageView
private lateinit var nutritionImg: ImageView
private lateinit var macrosImg: ImageView
private lateinit var profileImg: ImageView

private lateinit var routineTV: TextView
private lateinit var  workoutTV: TextView
private lateinit var macrosTV: TextView
private lateinit var nutritionTV: TextView
private lateinit var profileTV: TextView

@Suppress("DEPRECATION")
class NutritionPg : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNutritionPgBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val foodImage = intArrayOf(
            R.drawable.food_apple,
            R.drawable.food_bacon,
            R.drawable.food_banana,
            R.drawable.food_boiled_egg,
            R.drawable.food_broccoli,
            R.drawable.food_cheese,
            R.drawable.food_chicken_breast,
            R.drawable.food_corn,
            R.drawable.food_grapes,
            R.drawable.food_milk,
            R.drawable.food_nuts,
            R.drawable.food_pork,
            R.drawable.food_potato,
            R.drawable.food_rice,
            R.drawable.food_shrimp,
            R.drawable.food_sweet_potatoes,
            R.drawable.food_tilapia,
            R.drawable.food_toasted_bread,
            R.drawable.food_tofu,
            R.drawable.food_yogurt,
            R.drawable.food_chocolate,
            R.drawable.food_pandesal,
            R.drawable.food_manok_paa,
            R.drawable.food_dugo,
            R.drawable.food_chicken_kidney,
            R.drawable.food_chicken_meat,
            R.drawable.food_hito,
            R.drawable.food_pusit,
            R.drawable.food_bangus,
            R.drawable.food_pastilyas,
            R.drawable.food_taho,
            R.drawable.food_ice_candy,
            R.drawable.food_yema,
            R.drawable.food_avocado,
            R.drawable.food_sunflower_seed,
            R.drawable.food_mango_drink,
            R.drawable.food_orange_soda,

            R.drawable.food_pumpkin_seed,
            R.drawable.food_watermelon_seed,
            R.drawable.food_balot,
            R.drawable.food_canned_tuna,
            R.drawable.food_pumpkin,
            R.drawable.food_carrot,
            R.drawable.food_malunggay,
            R.drawable.food_toge,
            R.drawable.food_ampalaya,
            R.drawable.food_tomato,
            R.drawable.food_papaya,
            R.drawable.food_pechay,
            R.drawable.food_pipino,
            R.drawable.food_eggplant,

            R.drawable.food_lemon_juice,
            R.drawable.food_orange_soda
        )

        val foodName = arrayOf(

            "Apple",
            "Bacon",
            "Banana",
            "Boiled Egg",
            "Broccoli",
            "Cheese",
            "Chicken Breast",
            "Corn",
            "Grapes",
            "Milk",
            "Nuts",
            "Pork",
            "Potato",
            "Rice",
            "Shrimp",
            "Sweet Potatoes",
            "Tilapia",
            "Toasted Bread",
            "Tofu",
            "Yogurt",

            "Chocolate",
            "Pandesal",
            "Manok Paa",
            "Dugo",
            "Chiken Kidney",
            "Chicken Meat",
            "Hito",
            "Pusit",
            "Bangus",
            "Pastilyas",
            "Taho",
            "Ice Candy",
            "Yema",
            "Avocado",
            "Sunflower",
            "Mango Drink",
            "Orange Soda",

            "Pumpkin Seed",
            "Watermelon Seed",
            "Balut",
            "Tuna Flakes",
            "Pumpkin",
            "Carrot",
            "Malunggay",
            "Toge",
            "Ampalaya",
            "Tomato",
            "Papaya",
            "Petchay",
            "Cucumber",
            "Egg Plant",
            "Lemon Juice",
            "Orange Juice"








        )

        val foodGram = arrayOf(
            "235 grams",
            "10 grams",
            "65 grams",
            "55 grams",
            "40g (raw)\n45g (cooked)",
            "30 grams",
            "30 grams",
            "120 grams",
            "69 grams",
            "250ml",
            "10 grams",
            "35 grams",
            "170 grams",
            "55 grams",
            "40 grams",
            "85 grams",
            "25 grams",
            "30 grams",
            "100 grams",
            "150ml",

            "5 grams",
            "35 grams",
            "35 grams",
            "35 grams",
            "45 grams",
            "30 grams",
            "35 grams",
            "50 grams",
            "35 grams",
            "5 grams",
            "40 grams",
            "75 grams",
            "25 grams",
            "65 grams",
            "8 grams",
            "35ml",
            "250ml",

            "10g",
            "10g",
            "65g",
            "45g",
            "45g",
            "45g (raw)\n40g (cooked)",
            "45g raw",
            "45g cooked",
            "45g",
            "40g raw",
            "40g (raw)\n45g (cooked)",
            "45g cooked",
            "40g raw",
            "45g cooked",
            "1/2 cup",
            "1/3 cup"

        )

        val foodCalorie = arrayOf(
            "110 kcal",
            "54 kcal",
            "92 kcal",
            "86 kcal",
            "16 kcal",
            "90 kcal",
            "41 kcal",
            "100 kcal",
            "40 kcal",
            "125 kcal",
            "122 kcal",
            "122 kcal",
            "100 kcal",
            "92 kcal",
            "41 kcal",
            "92g kcal",
            "41 kcal",
            "108 kcal",
            "86 kcal",
            "125 kcal",

            "20 kcal",
            "108 kcal",
            "86 kcal",
            "41 kcal",
            "41 kcal",
            "41 kcal",
            "41 kcal",
            "41 kcal",
            "41 kcal",
            "20 kcal",
            "20 kcal",
            "20 cal",
            "20 kcal",
            "0 kcal",
            "45 kcal",
            "20 kcal",
            "120 kcal",

            "45 kcal",
            "45 kcal",
            "122 kcal",
            "122 kcal",
            "16 kcal",
            "16 kcal",
            "16 kcal",
            "16 kcal",
            "16 kcal",
            "16 kcal",
            "16 kcal",
            "16 kcal",
            "16 kcal",
            "16 kcal",
            "130 kcal",
            "90 kcal"


        )

        val foodProtein = arrayOf(
            "0g",
            "3.7g",
            "0g",
            "8g",
            "1g",
            "3g",
            "8g",
            "2g",
            "0g",
            "8g",
            "8g",
            "8g",
            "2g",
            "0g",
            "8g",
            "0g",
            "8g",
            "4g",
            "8g",
            "8g",

            "0g",
            "4g",
            "8g",
            "8g",
            "8g",
            "8g",
            "8g",
            "8g",
            "8g",
            "0g",
            "0g",
            "0g",
            "0g",
            "0g",
            "0g",
            "0g",
            "0g",

            "0g",
            "0g",
            "8g",
            "8g",
            "1g",
            "1g",
            "1g",
            "1g",
            "1g",
            "1g",
            "1g",
            "1g",
            "1g",
            "1g",
            "0g",
            "0g"




        )

        val foodCarb = arrayOf(
            "32.45g",
            "0.14g",
            "23g",
            "0g",
            "3g",
            "4g",
            "0g" ,
            "23g",
            "0g",
            "12g",
            "0g",
            "0g",
            "23g",
            "23g",
            "0g ",
            "23g",
            "0g",
            "23g",
            "0g",
            "12g",

            "5g",
            "23g",
            "0g",
            "0g",
            "0g",
            "0g",
            "0g",
            "0g",
            "0g",
            "5g",
            "5g",
            "5g",
            "5g",
            "0g",
            "0g",
            "0g",
            "0g",

            "0g",
            "0g",
            "0g",
            "0g",
            "3g",
            "3g",
            "3g",
            "3g",
            "3g",
            "3g",
            "3g",
            "3g",
            "3g",
            "3g",
            "10g",
            "10g"



        )

        val foodFat = arrayOf(
            "0g",
            "5g",
            "0g" ,
            "6g",
            "0g",
            "7g",
            "1g",
            "0g",
            "0g",
            "5g",
            "10g",
            "10g",
            "0g",
            "0g",
            "1g",
            "0g" ,
            "1g",
            "0g" ,
            "6g",
            "5g",

            "0g",
            "5g",
            "8g",
            "1g",
            "1g",
            "1g",
            "1g",
            "1g",
            "1g",
            "0g",
            "0g",
            "0g",
            "0g",
            "5g",
            "5g",
            "0g",
            "0g",

            "5g" ,
            "5g" ,
            "10g" ,
            "10g" ,
            "0g" ,
            "0g" ,
            "0g" ,
            "0g" ,
            "0g" ,
            "0g" ,
            "0g" ,
            "0g" ,
            "0g" ,
            "0g" ,
            "0g",
            "0g"

        )

        userArrayList = ArrayList()
        
        for (i in foodName.indices) {

            val user = User(
                foodName[i],
                foodCalorie[i],
                foodGram[i],
                foodCarb[i],
                foodProtein[i],
                foodFat[i],
                foodImage[i]
            )
            userArrayList.add(user)
        }
        binding.listview.isClickable = false
        binding.listview.adapter = MyAdapter(this, userArrayList)
        binding.listview.setOnItemClickListener { parent, view, position, id ->

            val foodName = foodName[position]
            val foodCalorie = foodCalorie[position]
            val foodGram = foodGram[position]
            val foodCarb = foodCarb[position]
            val foodProtein = foodProtein[position]
            val foodFat = foodFat[position]
            val foodImage = foodImage[position]

            if(binding.listview.isClickable){
                val i = Intent(this, useractivity::class.java)
                i.putExtra("foodName", foodName)
                i.putExtra("foodCalorie", foodCalorie)
                i.putExtra("foodGram", foodGram)
                i.putExtra("foodCarb", foodCarb)
                i.putExtra("foodProtein", foodProtein)
                i.putExtra("foodFat", foodFat)
                i.putExtra("foodImage", foodImage)
                startActivity(i)
            }




        }




        footbar()

    }

    @SuppressLint("ClickableViewAccessibility")
    private fun footbar(){
        routineImg = binding.routineImg
        workoutImg = binding.workoutImg
        nutritionImg = binding.nutritionImg
        macrosImg = binding.macrosImg
        profileImg = binding.profileImg

        routineTV = binding.routineTV
        workoutTV= binding.workoutTV
        nutritionTV = binding.nutritionTV
        macrosTV = binding.macrosTV
        profileTV = binding.profileTV




        routineImg.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Handle touch down event
                    // Load the colored drawable
                    val coloredDrawable1 = ContextCompat.getDrawable(this, R.mipmap.routine_white_100dp)
                    // Change image to colored version when touched
                    routineTV.setTextColor(ContextCompat.getColor(this, R.color.white))
                    routineImg.setBackgroundResource(R.color.skyblue)
                    routineImg.setImageDrawable(coloredDrawable1)
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    // Handle touch up or cancel event
                    // Revert to default image when touch is released or cancelled
                    val coloredDrawable2 = ContextCompat.getDrawable(this, R.mipmap.routine_black_100dp)
                    // Change image to colored version when touched
                    routineTV.setTextColor(ContextCompat.getColor(this, R.color.black))
                    routineImg.setBackgroundResource(R.color.skyblue)
                    routineImg.setImageDrawable(coloredDrawable2)
                    val intent = Intent(this, Dashboard::class.java)
                    val animation = ActivityOptions.makeCustomAnimation(
                        this,
                        R.anim.slide_in_right,
                        R.anim.slide_out_left

                    )

                    routineTV.setOnClickListener {
                        startActivity(intent, animation.toBundle())
                    }
                    startActivity(intent, animation.toBundle())
                    finish()
                }
            }
            true // Consume the touch event
        }



        workoutImg.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    val workout1 = ContextCompat.getDrawable(this, R.mipmap.workout_white_dumbbell100_foreground)
                    workoutTV.setTextColor(ContextCompat.getColor(this, R.color.white))
                    workoutImg.setBackgroundResource(R.color.skyblue)
                    workoutImg.setImageDrawable(workout1)
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    val workout2 = ContextCompat.getDrawable(this, R.mipmap.workout_black_dumbbell100_foreground)
                    workoutTV.setTextColor(ContextCompat.getColor(this, R.color.black))
                    workoutImg.setBackgroundResource(R.color.skyblue)
                    workoutImg.setImageDrawable(workout2)
                    val intent = Intent(this, WorkoutPg::class.java)
                    val animation = ActivityOptions.makeCustomAnimation(
                        this,
                        R.anim.slide_in_right,
                        R.anim.slide_out_left

                    )

                    workoutTV.setOnClickListener {
                        startActivity(intent, animation.toBundle())
                    }
                    startActivity(intent, animation.toBundle())
                    finish()
                }
            }
            true // Consume the touch event
        }




        nutritionImg.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Handle touch down event
                    // Load the colored drawable
                    val  nutrition1 = ContextCompat.getDrawable(this, R.mipmap.nutrition_white_100dp)
                    // Change image to colored version when touched
                    nutritionTV.setTextColor(ContextCompat.getColor(this, R.color.white))
                    nutritionImg.setBackgroundResource(R.color.skyblue)
                    nutritionImg.setImageDrawable( nutrition1)
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    // Handle touch up or cancel event
                    // Revert to default image when touch is released or cancelled
                    val  nutrition2 = ContextCompat.getDrawable(this, R.mipmap.nutrition_white_100dp)
                    // Change image to colored version when touched
                    nutritionTV.setTextColor(ContextCompat.getColor(this, R.color.white))
                    nutritionImg.setBackgroundResource(R.color.skyblue)
                    nutritionImg.setImageDrawable( nutrition2)


                    // You can optionally trigger click event here if needed
                    // view.performClick()
                }
            }
            true // Consume the touch event
        }




        macrosImg.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Handle touch down event
                    // Load the colored drawable
                    val macros1 = ContextCompat.getDrawable(this, R.mipmap.macros_white_100dp)
                    // Change image to colored version when touched
                    macrosTV.setTextColor(ContextCompat.getColor(this, R.color.white))
                    macrosImg.setBackgroundResource(R.color.skyblue)
                    macrosImg.setImageDrawable(macros1)
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    // Handle touch up or cancel event
                    // Revert to default image when touch is released or cancelled
                    val macros2 = ContextCompat.getDrawable(this, R.mipmap.macros_black_100dp)
                    // Change image to colored version when touched
                    macrosTV.setTextColor(ContextCompat.getColor(this, R.color.black))
                    macrosImg.setBackgroundResource(R.color.skyblue)
                    macrosImg.setImageDrawable(macros2)
                    val intent = Intent(this, MacrosPg::class.java)
                    val animation = ActivityOptions.makeCustomAnimation(
                        this,
                        R.anim.slide_in_left,
                        R.anim.slide_out_right
                    )

                    macrosTV.setOnClickListener {
                        startActivity(intent, animation.toBundle())
                    }
                    startActivity(intent, animation.toBundle())
                    finish()

                    // You can optionally trigger click event here if needed
                    // view.performClick()
                }
            }
            true // Consume the touch event
        }



        profileImg.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    val profile1 = ContextCompat.getDrawable(this, R.mipmap.profile_white_100dp)
                    profileTV.setTextColor(ContextCompat.getColor(this, R.color.white))
                    profileImg.setBackgroundResource(R.color.skyblue)
                    profileImg.setImageDrawable(profile1)
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    val profile2 = ContextCompat.getDrawable(this, R.mipmap.profile_black_100dp)
                    profileTV.setTextColor(ContextCompat.getColor(this, R.color.black))
                    profileImg.setBackgroundResource(R.color.skyblue)
                    profileImg.setImageDrawable(profile2)
                    val intent = Intent(this, ProfilePg::class.java)
                    val animation = ActivityOptions.makeCustomAnimation(
                        this,
                        R.anim.slide_in_left,
                        R.anim.slide_out_right
                    )

                    workoutTV.setOnClickListener {
                        startActivity(intent, animation.toBundle())
                    }
                    startActivity(intent, animation.toBundle())
                    finish()
                }
            }
            true // Consume the touch event
        }
    }
}