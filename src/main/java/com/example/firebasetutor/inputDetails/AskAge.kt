

package com.example.firebasetutor.inputDetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasetutor.R
import com.example.firebasetutor.databinding.ActivityAskAgeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

@Suppress("DEPRECATION")
class askAge : AppCompatActivity() {
    private lateinit var binding: ActivityAskAgeBinding
    private var isAgeNxtBtnClicked = false
    private lateinit var inputAge: EditText
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAskAgeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbRef = FirebaseDatabase.getInstance().getReference("Employee")
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        //backbutton

        binding.ageBckBtn.setOnClickListener {
            val intentToGender = Intent(this, AskGenderActivity::class.java)
            startActivity(intentToGender)
        }


        inputAge = findViewById<EditText>(R.id.inputAgeId)

        // Set input type to numeric
        inputAge.inputType = InputType.TYPE_CLASS_NUMBER

        // Set length limit to 3 digits
        inputAge.filters = arrayOf(InputFilter.LengthFilter(3))


        //Onclick effect of next button

        val nxtAgeClickableBox = findViewById<LinearLayout>(R.id.nextBtnAge)
        nxtAgeClickableBox.isClickable = true

        // Set the default background drawable
        nxtAgeClickableBox.setBackgroundResource(R.drawable.custom_button_background_pressed)

        // ...

        nxtAgeClickableBox.setOnClickListener {
            // Toggle between background drawables when clicked
            var ageValueString = inputAge.text.toString()
            editor.putString("age", ageValueString)
            editor.apply()
                isAgeNxtBtnClicked = !isAgeNxtBtnClicked
                if (inputAge.text.isNullOrEmpty()) {
                    Toast.makeText(this, "Please Enter your Age", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                else {
                    // Get the UID of the signed-in user
                    val userUid = FirebaseAuth.getInstance().currentUser?.uid
                    val intent = Intent(this, askHeight::class.java)
                    if (!userUid.isNullOrEmpty()) {

                        // Save the age to the "Employee" node under the user's UID
                        val ageValue = inputAge.text.toString()
                        dbRef.child(userUid).child("age").setValue(ageValue)



                    }
                    startActivity(intent)
                    overridePendingTransition(0, 0)

                }

        }

    }
}
