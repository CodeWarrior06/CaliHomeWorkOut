package com.example.firebasetutor

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasetutor.databinding.ActivityLoginBinding
import com.example.firebasetutor.inputDetails.AskName
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    private lateinit var  sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var dbRef: DatabaseReference
    private var beginnerProgressBar: Int = 0
    private var intermediateProgressBar: Int = 0
    private var beginnerPushPullPhase: Int = 0
    private var beginnerLegCorePhase: Int = 0
    private var intermediatePushPullPhase: Int = 0
    private var intermediateLegCorePhase: Int = 0

    private var isBeginnerSchedView: String? = ""
    private var isIntermediateSchedView: String? = ""
    private var beginnerAskSched:String? = ""
    private var intermediateAskSched: String? = ""
    private var isWorkout: String? = ""

    private var beginnerProgressBarValue: Int? = null
    private var intermediateProgressBarValue: Int? = null

    private var beginnerPushPullPhaseValue: Int? = null
    private var beginnerLegCorePhaseValue: Int? = null
    private var intermediatePushPullPhaseValue: Int? = null
    private var intermediateLegCorePhaseValue: Int? = null


    private var getIsBeginnerSchedView: String = ""
    private var getIsIntermediateSchedView: String = ""

    private var getBeginnerAskSched: String = ""
    private var getIntermediateAskSched: String = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Login"

        // Initialize FirebaseAuth instance
        auth = FirebaseAuth.getInstance()
        val userUid = FirebaseAuth.getInstance().currentUser?.uid
        val dbRef = FirebaseDatabase.getInstance().getReference("Employee")
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        binding.registerTV.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            overridePendingTransition(0, 0)
            finish()
        }

        // Login button click listener
        binding.loginBtn.setOnClickListener {
            val email = binding.emailLogin.text.toString()
            val password = binding.passwordLogin.text.toString()


            if (email.isNotEmpty() && password.isNotEmpty()) {
                // Sign in with email and password
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Login successful, navigate to next activity
                            val user = auth.currentUser
                            if (user != null) {
                                dbRef.child(user.uid).addListenerForSingleValueEvent(object : ValueEventListener {
                                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            // User data exists in the database
                                            val activityFactor = dataSnapshot.child("activity_factor").value?.toString()
                                            val getBeginnerProgressBar = dataSnapshot.child("beginnerProgressBar").value?.toString()
                                            val getIntermediateProgressBar = dataSnapshot.child("intermediateProgressBar").value?.toString()

                                            val getBeginnerPushPullPhase = dataSnapshot.child("beginnerPushPullPhase").value?.toString()
                                            val getBeginnerLegCorePhase = dataSnapshot.child("beginnerLegCorePhase").value?.toString()
                                            val getIntermediatePushPullPhase = dataSnapshot.child("intermediatePushPullPhase").value?.toString()
                                            val getIntermediateLegCorePhase = dataSnapshot.child("intermediatePushPullPhase").value?.toString()

                                            val getIsBeginnerSchedView = dataSnapshot.child("isBeginnerSchedView").value?.toString()
                                            val getIsIntermediateSchedView =dataSnapshot.child("isIntermediateSchedView").value?.toString()

                                            val getBeginnerAskSched=dataSnapshot.child("beginnerAskSched").value?.toString()
                                            val getIntermediateAskSched =dataSnapshot.child("intermediateAskSched").value?.toString()
                                            val getNutritionalStatus =dataSnapshot.child("nutritional_Status").value?.toString()

                                                //BEGINNER PROGRESS BAR
                                            if(!getBeginnerProgressBar.isNullOrEmpty()){

                                                beginnerProgressBarValue = getBeginnerProgressBar?.toIntOrNull()
                                                if(beginnerProgressBarValue != null){
                                                    editor.putInt("beginnerProgressBar",
                                                        beginnerProgressBarValue!!
                                                    )
                                                    editor.commit()
                                                }
                                            }
                                                //INTERMEDIATE PROGRESS BAR
                                            if(!getIntermediateProgressBar.isNullOrEmpty()){

                                                intermediateProgressBarValue = getIntermediateProgressBar?.toIntOrNull()
                                                if(intermediateProgressBarValue != null){
                                                    editor.putInt("intermediateProgressBar",
                                                        intermediateProgressBarValue!!
                                                    )
                                                    editor.commit()
                                                }
                                            }
                                                    //Beginner Push Pull Phase
                                                    if(!getBeginnerPushPullPhase.isNullOrEmpty()){
                                                        beginnerPushPullPhaseValue = getBeginnerPushPullPhase?.toIntOrNull()
                                                        if(beginnerPushPullPhaseValue != null){
                                                            editor.putInt("beginnerPushPullPhase",
                                                                beginnerPushPullPhaseValue!!
                                                            )
                                                            editor.commit()
                                                        }
                                                    }

                                                    if(!getBeginnerLegCorePhase.isNullOrEmpty()){
                                                        beginnerLegCorePhaseValue = getBeginnerLegCorePhase?.toIntOrNull()
                                                        if(beginnerLegCorePhaseValue != null){
                                                            editor.putInt("beginnerLegCorePhase",
                                                                beginnerLegCorePhaseValue!!
                                                            )
                                                            editor.commit()
                                                        }
                                                    }

                                                    if(!getIntermediatePushPullPhase.isNullOrEmpty()){
                                                        intermediatePushPullPhaseValue = getIntermediatePushPullPhase?.toIntOrNull()
                                                        if(intermediatePushPullPhaseValue != null){
                                                            editor.putInt("intermediatePushPullPhase",
                                                                intermediatePushPullPhaseValue!!
                                                            )
                                                            editor.commit()
                                                        }
                                                    }
                                                    if(!getIntermediateLegCorePhase.isNullOrEmpty()){
                                                        intermediateLegCorePhaseValue = getIntermediateLegCorePhase?.toIntOrNull()
                                                        if(intermediateLegCorePhaseValue != null){
                                                            editor.putInt("intermediateLegCorePhase",
                                                                intermediateLegCorePhaseValue!!
                                                            )
                                                            editor.commit()
                                                        }
                                                    }

                                                    if(!getNutritionalStatus.isNullOrEmpty()){
                                                        var nutritionalStatusString = getNutritionalStatus
                                                        if(nutritionalStatusString != null){
                                                            editor.putString("nutritionalStatusData",
                                                                getNutritionalStatus!!
                                                            )
                                                            editor.commit()
                                                        }
                                                    }


                                            if(!getIsBeginnerSchedView.isNullOrEmpty()){
                                                editor.putString("isBeginnerSchedView", getIsBeginnerSchedView!!)
                                                editor.commit()
                                            }
                                            if(!getIsIntermediateSchedView.isNullOrEmpty()){
                                                editor.putString("isIntermediateSchedView", getIsIntermediateSchedView!!)
                                                editor.commit()
                                            }

                                            if(!getBeginnerAskSched.isNullOrEmpty()){
                                                editor.putString("beginnerAskSched", getBeginnerAskSched!!)
                                                editor.commit()
                                            }

                                            if(!getIntermediateAskSched.isNullOrEmpty()){
                                                editor.putString("intermediateAskSched", getIntermediateAskSched!!)
                                                editor.commit()
                                            }



                                            if (!activityFactor.isNullOrEmpty()) {
                                                // User has the necessary data, navigate to Dashboard

                                                val intent = Intent(this@LoginActivity, Dashboard::class.java)
                                                overridePendingTransition(0, 0)
                                                startActivity(intent)
                                                finish()
                                            } else {
                                                // User data incomplete, navigate to AskName activity
                                                startActivity(Intent(this@LoginActivity, AskName::class.java))
                                                overridePendingTransition(0, 0)
                                                finish()
                                            }
                                        } else {
                                            // User data doesn't exist, navigate to AskName activity
                                            startActivity(Intent(this@LoginActivity, AskName::class.java))
                                            overridePendingTransition(0, 0)
                                            finish()
                                        }
                                    }

                                    override fun onCancelled(databaseError: DatabaseError) {
                                        // Handle error
                                    }
                                })
                            } else {
                                // User is null, show error message
                                Toast.makeText(
                                    this@LoginActivity,
                                    "Login failed: User is null",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        } else {
                            // Login failed, display error message
                            Toast.makeText(
                                this@LoginActivity,
                                "Login failed: ${task.exception?.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                    } else {
                    // Email or password is empty, show appropriate message
                    Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }



    }
