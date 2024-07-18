package com.example.firebasetutor

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityOptions
import android.app.ActivityOptions.*
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.firebasetutor.databinding.ActivityProfilePgBinding
import com.example.firebasetutor.workoutModule.WorkoutPg
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.DecimalFormat

private lateinit var binding: ActivityProfilePgBinding

@Suppress("DEPRECATION")
class ProfilePg : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var profileUsernameText: TextView
    private lateinit var ageDetailText: TextView
    private lateinit var dbRef: DatabaseReference
    private lateinit var genderDetailText: TextView
    private lateinit var heightDetailText: TextView
    private lateinit var weightDetailText: TextView
    private lateinit var activityFactorDetailText: TextView
    private lateinit var  bmiGradeDetailText: TextView
    private lateinit var  bmiDetailText: TextView
    private lateinit var username: String
    private lateinit var  sharedPreferences:SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private  var nutritionalStatus = ""
    private var bmiComputeFormatPref = ""
    private lateinit var profileIV : ImageView
    private lateinit var editProfile: CardView

    private var beginnerProgressBar: Int = 0
    private var intermediateProgressBar: Int = 0
    private var beginnerPushPullPhase: Int = 0
    private var beginnerLegCorePhase: Int = 0
    private var intermediatePushPullPhase: Int = 0
    private var intermediateLegCorePhase: Int = 0

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

    private var isBeginnerSchedView:String? = ""
    private var isIntermediateSchedView :String? = ""
    private var beginnerAskSched :String? = ""
    private var intermediateAskSched :String? = ""
    private var    isWorkout :String? = ""




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfilePgBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        beginnerProgressBar = sharedPreferences.getInt("beginnerProgressBar", 0)
        intermediateProgressBar = sharedPreferences.getInt("intermediateProgressBar", 0)
        beginnerPushPullPhase = sharedPreferences.getInt("beginnerPushPullPhase", 0)
        beginnerLegCorePhase = sharedPreferences.getInt("beginnerLegCorePhase", 0)
        intermediatePushPullPhase = sharedPreferences.getInt("intermediatePushPullPhase", 0)
        intermediateLegCorePhase = sharedPreferences.getInt("intermediateLegCorePhase", 0)
        isBeginnerSchedView = sharedPreferences.getString("isBeginnerSchedView", "")
        isIntermediateSchedView = sharedPreferences.getString("isIntermediateSchedView", "")
        beginnerAskSched = sharedPreferences.getString("beginnerAskSched", "")
        intermediateAskSched = sharedPreferences.getString("intermediateAskSched", "")


        sendProgressBarData()


        auth = FirebaseAuth.getInstance()

        editProfile = binding.profileCVId


        editProfile.setOnClickListener{
            //openImagePicker()
                val intent1 = Intent(this, editProfileActivity::class.java)
            val animation = makeCustomAnimation(this, R.anim.slide_in_right, R.anim.slide_out_left)
            startActivity(intent1, animation.toBundle())
               // startActivity(intent1)
               // overridePendingTransition(0, 0)

        }

        binding.signOut.setOnClickListener {
            signOutUser()

        }

                profileUsernameText = findViewById(R.id.profileUsername)
                ageDetailText = findViewById(R.id.ageDetail)
                genderDetailText = findViewById(R.id.genderDetail)
                heightDetailText = findViewById(R.id.heightDetail)
                weightDetailText = findViewById(R.id.weightDetail)
                activityFactorDetailText = findViewById(R.id.acivityLevelDetail)
                bmiGradeDetailText = findViewById(R.id.bmiGradeDetail)
                bmiDetailText = findViewById(R.id.bmiDetail)
                dbRef = FirebaseDatabase.getInstance().getReference("Employee")

                // Get the UID of the signed-in user
                val userUid = FirebaseAuth.getInstance().currentUser?.uid
                localData()

        if(!userUid.isNullOrEmpty()) {
            // Retrieve the first name, last name, and age from the "Employee" node under the user's UID
            dbRef.child(userUid).addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val name = dataSnapshot.child("username").value?.toString()
                        val age = dataSnapshot.child("age").value?.toString()
                        val gender = dataSnapshot.child("gender").value?.toString()
                        val height = dataSnapshot.child("height").value?.toString()
                        val weight = dataSnapshot.child("weight").value?.toString()
                        val activityFactor = dataSnapshot.child("activity_factor").value?.toString()

                        // Set the retrieved values to the respective TextViews
                        if (!name.isNullOrEmpty()) {
                            profileUsernameText.text = name
                        }
                        if (!age.isNullOrEmpty()) {
                            ageDetailText.text = "$age yrs old"
                        }
                        if (!gender.isNullOrEmpty()) {
                            genderDetailText.text = gender
                        }
                        if (!age.isNullOrEmpty()) {
                            heightDetailText.text = height
                        }
                        if (!weight.isNullOrEmpty()) {
                            weightDetailText.text = weight
                        }
                        if (!activityFactor.isNullOrEmpty()) {
                            activityFactorDetailText.text = activityFactor
                        }

                            val heightDouble: Double = height?.toDoubleOrNull() ?: 0.0
                            val weightDouble: Double = weight?.toDoubleOrNull() ?: 0.0
                            //COnvert to Meter
                            val heightConvert = heightDouble * 0.01

                            val heightsquared = heightConvert * heightConvert
                            val bmiCompute = weightDouble / heightsquared

                        val bmiDecimal = formatDoubleWithMaxThreeDecimalPlaces(bmiCompute)
                        bmiGradeDetailText.text = bmiDecimal
                        dbRef.child(userUid).child("bmigrade").setValue(bmiDecimal)

                        //BMI Level
                        if(bmiCompute < 18.5){
                            bmiDetailText.text = "Underweight"
                            dbRef.child(userUid).child("nutritional_Status").setValue("Underweight")
                        }
                        else  if(bmiCompute <= 24.9){
                            bmiDetailText.text = "Normal"
                            dbRef.child(userUid).child("nutritional_Status").setValue("Normal")
                        }

                        else  if(bmiCompute <= 29.9){
                            bmiDetailText.text = "Pre - Obesity"
                            dbRef.child(userUid).child("nutritional_Status").setValue("Pre - Obesity")
                        }
                        else  if(bmiCompute <= 34.9){
                            bmiDetailText.text = "Obesity Class 1"
                            dbRef.child(userUid).child("nutritional_Status").setValue("Obesity Class 1")
                        }
                        else  if(bmiCompute <= 39.9){
                            bmiDetailText.text = "Obesity Class 2"
                            dbRef.child(userUid).child("nutritional_Status").setValue("Obesity Class 2")
                        }
                        else  if(bmiCompute >= 40){
                            bmiDetailText.text = "Obesity Class 3"
                            dbRef.child(userUid).child("nutritional_Status").setValue("Obesity Class 3")
                        }

                        else{
                            bmiDetailText.text = "None"
                            dbRef.child(userUid).child("nutritional_Status").setValue("None")
                        }
                        dbRef = FirebaseDatabase.getInstance().getReference("Employee")
                        val userRef = dbRef.child(userUid)
                        // Keep this user's data synced for offline use

                    }

                override fun onCancelled(databaseError: DatabaseError) {
                            // Handle errors here
                        }
                    })
                }






        footbar()
    }


    private fun sendProgressBarData(){
        dbRef = FirebaseDatabase.getInstance().getReference("Employee")
        val userUid = FirebaseAuth.getInstance().currentUser?.uid

        if (!userUid.isNullOrEmpty()) {
            if (beginnerProgressBar != null) {
                // Assuming inputHeight is a Double variable representing the height in inches
                dbRef.child(userUid).child("beginnerProgressBar").setValue(beginnerProgressBar)

            }
            if(intermediateProgressBar != null){
                dbRef.child(userUid).child("intermediateProgressBar").setValue(intermediateProgressBar)
            }
                if(beginnerPushPullPhase != null){
                    dbRef.child(userUid).child("beginnerPushPullPhase").setValue(beginnerPushPullPhase)
                }
                if(beginnerLegCorePhase != null){
                    dbRef.child(userUid).child("beginnerLegCorePhase").setValue(beginnerLegCorePhase)
                }
                if(intermediatePushPullPhase != null){
                    dbRef.child(userUid).child("intermediatePushPullPhase").setValue(intermediatePushPullPhase)
                }
                if(intermediateLegCorePhase != null){
                    dbRef.child(userUid).child("intermediateLegCorePhase").setValue(intermediateLegCorePhase)
                }
                    if(isBeginnerSchedView != null){
                        dbRef.child(userUid).child("isBeginnerSchedView").setValue(isBeginnerSchedView)
                    }
                    if(isIntermediateSchedView != null){
                        dbRef.child(userUid).child("isIntermediateSchedView").setValue(isIntermediateSchedView)
                    }
                        if(beginnerAskSched != null){
                            dbRef.child(userUid).child("beginnerAskSched").setValue(beginnerAskSched)
                        }
                        if(intermediateAskSched != null){
                            dbRef.child(userUid).child("intermediateAskSched").setValue(intermediateAskSched)
                        }




        }


    }


    // Function to check if the user is online



    private fun signOutUser() {
        val connectivityChecker = ConnectivityChecker(this)
        // Check if the user is authenticated
        val isUserAuthenticated = connectivityChecker.isUserAuthenticated()
        // Check if there is an active internet connection
        val isInternetConnected = connectivityChecker.isInternetConnected()

        if(isInternetConnected){
            sendProgressBarData()
            resetSharedPref()
            auth.signOut()
            //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
        else if(!isInternetConnected){
            Toast.makeText(this, "You need internet connection to log out", Toast.LENGTH_SHORT).show()

        }

    }

    private fun resetSharedPref(){
        // Assuming "prefs" is your SharedPreferences instance
        editor = sharedPreferences.edit()
        editor.clear()
        editor.commit()
        Log.d("Ilan_Ang_Beginner_Progress_Bar", "beginnerProgress_Bar is $beginnerProgressBar.")
        Log.d("Ilan_Ang_intermediate_Progress_Bar", "intermediateProgress_Bar is $intermediateProgressBar.")
    }


    private fun formatDoubleWithMaxThreeDecimalPlaces(value: Double): String {
        val decimalFormat = DecimalFormat("#.##")
        return decimalFormat.format(value)
    }
    private fun localData(){
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "")
        profileUsernameText.text = "$username"

        val gender = sharedPreferences.getString("gender", "")
        genderDetailText.text = "$gender"

        val age = sharedPreferences.getString("age", "")
        ageDetailText.text = "$age yrs old"

        val height = sharedPreferences.getString("height", "")
        heightDetailText.text = "$height"

        val weight = sharedPreferences.getString("weight", "")
        weightDetailText.text = "$weight"

        val activityFactor = sharedPreferences.getString("activityFactor", "")
        activityFactorDetailText.text = "$activityFactor"

        localDataBMI()

        val nutritionalStatus = sharedPreferences.getString("nutritionalStatus", "")
        bmiDetailText.text = "$nutritionalStatus"

         val bmi = sharedPreferences.getString("bmi", "")
         bmiGradeDetailText.text = "$bmi"





    }

    private fun localDataBMI(){
        val weightString = sharedPreferences.getString("weight", "") ?: ""
        val heightString = sharedPreferences.getString("height", "") ?: ""
        var weightToDouble = weightString.toDoubleOrNull() ?: 0.0
        var heightToDouble = heightString.toDoubleOrNull() ?: 0.0
        var heightToMeter = heightToDouble * 0.01
        var bmiCompute = weightToDouble / (heightToMeter * heightToMeter)
        var bmiComputeFormat  = formatDoubleWithMaxThreeDecimalPlaces(bmiCompute)
        bmiComputeFormatPref = bmiComputeFormat
        var bmiComputeToDouble = bmiComputeFormatPref.toDouble()
        if(bmiComputeToDouble < 18.5){
            nutritionalStatus = "Underweight"

        }
        else  if(bmiComputeToDouble <= 24.9){
            nutritionalStatus  = "Normal"

        }

        else  if(bmiComputeToDouble <= 29.9){
            nutritionalStatus  = "Pre - Obesity"

        }
        else  if(bmiComputeToDouble <= 34.9){
            nutritionalStatus  = "Obesity Class 1"

        }
        else  if(bmiComputeToDouble <= 39.9){
            nutritionalStatus  = "Obesity Class 2"

        }
        else  if(bmiComputeToDouble >= 40) {
            nutritionalStatus = "Obesity Class 3"

        }
        else{
            nutritionalStatus  = "None"

        }

        editor = sharedPreferences.edit()
        editor.putString("nutritionalStatus", nutritionalStatus)
        editor.putString("bmi", bmiComputeFormatPref )
        editor.commit()

    }


    private val resultLauncher = registerForActivityResult(StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val selectedImageUri = data?.data
            profileIV.setImageURI(selectedImageUri)
        }
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
                    val intent = Intent(this,Dashboard::class.java)

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
                    val macros2 = ContextCompat.getDrawable(this, R.mipmap.macros_black_100dp)
                    // Change image to colored version when touched
                    macrosTV.setTextColor(ContextCompat.getColor(this, R.color.black))
                    macrosImg.setBackgroundResource(R.color.skyblue)
                    macrosImg.setImageDrawable(macros2)
                    val intent = Intent(this, MacrosPg::class.java)
                    val animation = ActivityOptions.makeCustomAnimation(
                        this,
                        R.anim.slide_in_right,
                        R.anim.slide_out_left

                    )
                    macrosTV.setOnClickListener {
                        startActivity(intent, animation.toBundle())
                    }
                    startActivity(intent, animation.toBundle())
                    finish()
                }
            }
            true // Consume the touch event
        }



        profileImg.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Handle touch down event
                    // Load the colored drawable
                    val profile1 = ContextCompat.getDrawable(this, R.mipmap.profile_white_100dp)
                    // Change image to colored version when touched
                    profileTV.setTextColor(ContextCompat.getColor(this, R.color.white))
                    profileImg.setBackgroundResource(R.color.skyblue)
                    profileImg.setImageDrawable(profile1)
                }

                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    // Handle touch up or cancel event
                    // Revert to default image when touch is released or cancelled
                    val  profile2 = ContextCompat.getDrawable(this, R.mipmap.profile_white_100dp)
                    // Change image to colored version when touched
                    profileTV.setTextColor(ContextCompat.getColor(this, R.color.white))
                    profileImg.setBackgroundResource(R.color.skyblue)
                    profileImg.setImageDrawable(profile2)


                    // You can optionally trigger click event here if needed
                    // view.performClick()
                }
            }
            true // Consume the touch event
        }

    }



    override fun onResume() {
        super.onResume()
        // Reload data to reflect changes
        localData()
    }






}