package com.example.firebasetutor.intermediate

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasetutor.Dashboard
import com.example.firebasetutor.MacrosPg
import com.example.firebasetutor.NutritionPg
import com.example.firebasetutor.ProfilePg
import com.example.firebasetutor.workoutModule.WorkoutPg
import com.example.firebasetutor.databinding.ActivityIntermediatePgBinding

class IntermediatePg : AppCompatActivity() {

    private lateinit var binding: ActivityIntermediatePgBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntermediatePgBinding.inflate(layoutInflater)
        setContentView(binding.root)




        //Muscle Group Links Left to Right Downward

        binding.intermediateChest.setOnClickListener {
            val intent = Intent(this, IntermediateChestPg::class.java)
            startActivity(intent)

        }
        binding.intermediateBicep.setOnClickListener {
            val intent = Intent(this, IntermediateBicepPg::class.java)
            startActivity(intent)

        }
        binding.intermediateLegs.setOnClickListener {
            val intent = Intent(this, IntermediateLegPg::class.java)
            startActivity(intent)

        }
        binding.intermediateBack.setOnClickListener {
            val intent = Intent(this, IntermediateBackPg::class.java)
            startActivity(intent)

        }
        binding.intermediateShoulder.setOnClickListener {
            val intent = Intent(this, IntermediateShoulderPg::class.java)
            startActivity(intent)

        }
        binding.intermediateFullBody.setOnClickListener {
            val intent = Intent(this, IntermediateFullBodyPg::class.java)
            startActivity(intent)

        }





        //DONE DIRECTING TO NAVBAR
        binding.gridRoutineId.setOnClickListener {
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)

        }

        binding.gridWorkoutId.setOnClickListener {
            val intent = Intent(this, WorkoutPg::class.java)
            startActivity(intent)

        }

        binding.gridNutritionId.setOnClickListener {
            val intent = Intent(this, NutritionPg::class.java)
            startActivity(intent)

        }

        binding.gridMacrosId.setOnClickListener {
            val intent = Intent(this, MacrosPg::class.java)
            startActivity(intent)

        }

        binding.gridProfileId.setOnClickListener {
            val intent = Intent(this, ProfilePg::class.java)
            startActivity(intent)

        }
    }
}