package com.example.firebasetutor

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.firebasetutor.databinding.ActivityMacrosPgBinding
import com.example.firebasetutor.workoutModule.WorkoutPg

private lateinit var binding: ActivityMacrosPgBinding
private lateinit var spinnerGender: Spinner
private lateinit var spinnerActFactor: Spinner
private lateinit var macrosCalculate: LinearLayout
private lateinit var ageET: EditText
private lateinit var heightET: EditText
private lateinit var weightET: EditText
private var dataFactorValue = 0
private var cmToMeter: Float = 0.0f
private var bmi: Float = 0.0f
private var kgToFloat: Float = 0.0f
private var cmToMeterSquared: Float = 0.0f

private lateinit var routineImg: ImageView
private lateinit var workoutImg: ImageView
private lateinit var nutritionImg: ImageView
private lateinit var macrosImg: ImageView
private lateinit var profileImg: ImageView

private lateinit var routineTV: TextView
private lateinit var workoutTV: TextView
private lateinit var macrosTV: TextView
private lateinit var nutritionTV: TextView
private lateinit var profileTV: TextView


@Suppress("DEPRECATION")
class MacrosPg : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMacrosPgBinding.inflate(layoutInflater)
        setContentView(binding.root)



        spinnerGender = binding.dropBarGender
        spinnerActFactor = binding.dropBarActFactor
        footbar()


        val dataGender = arrayOf("Male", "Female")
        val dataFactor = arrayOf("Sedentary", "Lightly Active", "Moderately Active", "Very Active")

        // Create an ArrayAdapter using the string array and a default spinner layout

        val adapterGender = ArrayAdapter(this, android.R.layout.simple_spinner_item, dataGender)
        val adapterActFactor = ArrayAdapter(this, android.R.layout.simple_spinner_item, dataFactor)


        // Specify the layout to use when the list of choices appears
        adapterGender.setDropDownViewResource(R.layout.dropdown_gender_macros)
        adapterActFactor.setDropDownViewResource(R.layout.dropdown_gender_macros)

        // Apply the adapter to the spinner
        spinnerGender.adapter = adapterGender
        spinnerActFactor.adapter = adapterActFactor

        // Add a listener to spinnerActFactor to detect changes in selection
        spinnerActFactor.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // When selection changes, update dataFactorValue
                dropDownGetValue()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle case when nothing is selected
            }
        }


        setDecimalInputFilter(binding.heightET)
        setDecimalInputFilter(binding.weightET)
        ageET = binding.ageET
        heightET = binding.heightET
        weightET = binding.weightET
        macrosCalculate = binding.macrosCalculate

        calculateBtn()

    }

    private fun dropDownGetValue(){
        // Get the selected value from the spinner
        val selectedDataFactor = spinnerActFactor.selectedItem.toString()

        // Define a variable to hold the corresponding value
        dataFactorValue = 0 // Default value

        // Assign values based on the selected dataFactor
        when (selectedDataFactor) {
            "Sedentary" -> dataFactorValue = 30
            "Lightly Active" -> dataFactorValue = 35
            "Moderately Active" -> dataFactorValue = 40
            "Very Active" -> dataFactorValue = 45
        }


    }



    private fun calculateBtn() {

        macrosCalculate.setOnClickListener {
            if(weightET.text.isNullOrEmpty() && heightET.text.isNullOrEmpty() && ageET.text.isNullOrEmpty()){
                Toast.makeText(this, "Please Fill the Blanks", Toast.LENGTH_SHORT).show()
            }
            else if(ageET.text.isNullOrEmpty()){
                Toast.makeText(this, "Please Input Age", Toast.LENGTH_SHORT).show()
            }
            else if(heightET.text.isNullOrEmpty()){
                Toast.makeText(this, "Please Input Height", Toast.LENGTH_SHORT).show()
            }
            else if(weightET.text.isNullOrEmpty()){
                Toast.makeText(this, "Please Input Weight", Toast.LENGTH_SHORT).show()
            }
            else{

                val intentToMacrosResultPg = Intent(this, MacrosResultPg::class.java)




                intentToMacrosResultPg.putExtra("bmi", bmi)
                //Send Age next page
                val ageValue = ageET.text.toString().toIntOrNull() ?: 0
                intentToMacrosResultPg.putExtra("ageValue", ageValue)

                // Extract gender value from the spinner and pass it as an extra
                val genderValue = spinnerGender.selectedItem.toString()
                intentToMacrosResultPg.putExtra("genderValue",genderValue)

                //Send Weight next page
                val weightValue = weightET.text.toString().toFloatOrNull() ?: 0f
                intentToMacrosResultPg.putExtra("weightValue", weightValue)

                //Send Height next page
                val heightValue = heightET.text.toString().toFloatOrNull() ?: 0f
                intentToMacrosResultPg.putExtra("heightValue", heightValue)

                //Send Activity Factor next page
                intentToMacrosResultPg.putExtra("dataFactorValue", dataFactorValue)

                finish()
                startActivity(intentToMacrosResultPg)
                overridePendingTransition(0, 0)
            }




        }

    }









    fun setDecimalInputFilter(editText: EditText) {
        val decimalDigits = 2 // Number of decimal digits allowed
        val maxLength = 6 // Maximum length of the input (including decimal point)

        val filter = object : InputFilter {
            override fun filter(
                source: CharSequence?,
                start: Int,
                end: Int,
                dest: Spanned?,
                dstart: Int,
                dend: Int
            ): CharSequence? {
                val result = StringBuilder(dest).apply {
                    replace(
                        dstart,
                        dend,
                        source?.subSequence(start, end).toString()
                    )
                }
                return if (result.matches("^\\d{0,3}(\\.\\d{0,$decimalDigits})?\$".toRegex())) {
                    null // Accept the input
                } else {
                    "" // Reject the input
                }
            }
        }

        editText.filters = arrayOf(filter, InputFilter.LengthFilter(maxLength))
    }


    @SuppressLint("ClickableViewAccessibility")
    private fun footbar() {
        routineImg = binding.routineImg
        workoutImg = binding.workoutImg
        nutritionImg = binding.nutritionImg
        macrosImg = binding.macrosImg
        profileImg = binding.profileImg

        routineTV = binding.routineTV
        workoutTV = binding.workoutTV
        nutritionTV = binding.nutritionTV
        macrosTV = binding.macrosTV
        profileTV = binding.profileTV




        routineImg.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Handle touch down event
                    // Load the colored drawable
                    val coloredDrawable1 =
                        ContextCompat.getDrawable(this, R.mipmap.routine_white_100dp)
                    // Change image to colored version when touched
                    routineTV.setTextColor(ContextCompat.getColor(this, R.color.white))
                    routineImg.setBackgroundResource(R.color.skyblue)
                    routineImg.setImageDrawable(coloredDrawable1)
                }

                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    // Handle touch up or cancel event
                    // Revert to default image when touch is released or cancelled
                    val coloredDrawable2 =
                        ContextCompat.getDrawable(this, R.mipmap.routine_black_100dp)
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
                        startActivity(Intent(this, Dashboard::class.java))
                    }
                    startActivity(intent)

                    finish()
                }
            }
            true // Consume the touch event
        }



        workoutImg.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    val workout1 = ContextCompat.getDrawable(
                        this,
                        R.mipmap.workout_white_dumbbell100_foreground
                    )
                    workoutTV.setTextColor(ContextCompat.getColor(this, R.color.white))
                    workoutImg.setBackgroundResource(R.color.skyblue)
                    workoutImg.setImageDrawable(workout1)
                }

                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    val workout2 = ContextCompat.getDrawable(
                        this,
                        R.mipmap.workout_black_dumbbell100_foreground
                    )
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
                    val nutrition1 = ContextCompat.getDrawable(this, R.mipmap.nutrition_white_100dp)
                    // Change image to colored version when touched
                    nutritionTV.setTextColor(ContextCompat.getColor(this, R.color.white))
                    nutritionImg.setBackgroundResource(R.color.skyblue)
                    nutritionImg.setImageDrawable(nutrition1)
                }

                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    // Handle touch up or cancel event
                    // Revert to default image when touch is released or cancelled
                    val nutrition2 = ContextCompat.getDrawable(this, R.mipmap.nutrition_white_100dp)
                    // Change image to colored version when touched
                    nutritionTV.setTextColor(ContextCompat.getColor(this, R.color.white))
                    nutritionImg.setBackgroundResource(R.color.skyblue)
                    nutritionImg.setImageDrawable(nutrition2)
                    val intent = Intent(this, NutritionPg::class.java)
                    val animation = ActivityOptions.makeCustomAnimation(
                        this,
                        R.anim.slide_in_right,
                        R.anim.slide_out_left

                    )

                    nutritionTV.setOnClickListener {
                        startActivity(intent, animation.toBundle())
                    }
                    startActivity(intent, animation.toBundle())
                    finish()
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
                    val macros2 = ContextCompat.getDrawable(this, R.mipmap.macros_white_100dp)
                    // Change image to colored version when touched
                    macrosTV.setTextColor(ContextCompat.getColor(this, R.color.white))
                    macrosImg.setBackgroundResource(R.color.skyblue)
                    macrosImg.setImageDrawable(macros2)


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