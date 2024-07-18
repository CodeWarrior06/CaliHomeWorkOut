package com.example.firebasetutor

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasetutor.databinding.ActivityMacrosResultPgBinding

private lateinit var binding: ActivityMacrosResultPgBinding
private lateinit var includedLayout: View
private var maintainCalorieString = ""


@Suppress("DEPRECATION")
class MacrosResultPg : AppCompatActivity() {
    private var TER = 0
    private var dataFactorValue = 0
    private var weightValue = 0f // Change the type to Float
    private var heightValue = 0f // Change the type to Float
    private var terVal = 0
    private var weightString = ""
    private var weightFloat = 0f
    private var is1Lbs = 500
    private var is2Lbs = 1000

    //CPF if 1lbs
    private var carb1Lbs = 0f
    private var protein1Lbs = 0f
    private var fat1Lbs = 0f

    //CPF if 2lbs
    private var carb2Lbs = 0f
    private var protein2Lbs = 0f
    private var fat2Lbs = 0f

    private var bmi = 0f
    private var bmiToString = ""
    private var defaultMacros = 0

    //The local variable add/subtract 500 or 1000
    private var gain1Lbs = 0f
    private var gain2Lbs = 0f
    private var lose1Lbs = 0f
    private var lose2Lbs = 0f

    //Round off the Kcal to nearest 50
    private var gain1LbsRound = 0f
    private var gain2LbsRound = 0f
    private var lose1LbsRound = 0f
    private var lose2LbsRound = 0f

    private var gain1LbsModulus = 0f
    private var gain2LbsModulus = 0f
    private var lose1LbsModulus = 0f
    private var lose2LbsModulus = 0f

    //CPF 1lbs Convert to string
    private var carb1LbsToString = ""
    private var protein1LbsToString = ""
    private var fat1LbsToString = ""

    //CPF 2lbs Convert to string
    private var carb2LbsToString = ""
    private var protein2LbsToString = ""
    private var fat2LbsToString = ""

    private var carb1LbsModulus = 0f
    private var carb2LbsModulus = 0f
    private var carb1LbsRound= 0f
    private var carb2LbsRound =0f

    private var protein2LbsRound = 0f
    private var protein1LbsRound = 0f
    private var protein1LbsModulus = 0f
    private var protein2LbsModulus = 0f

    private var fat2LbsRound = 0f
    private var fat1LbsRound = 0f
    private var fat1LbsModulus = 0f
    private var fat2LbsModulus = 0f

    //Computation for getting CPF
    private var carbValue = 0.6
    private var proteinValue = 0.15
    private var fatValue = 0.25

    private lateinit var calorieText1: TextView
    private lateinit var calorieText2: TextView

    private lateinit var amountGramCarb1: TextView
    private lateinit var amountGramProtein1: TextView
    private lateinit var amountGramFat1: TextView

    private lateinit var amountGramCarb2: TextView
    private lateinit var amountGramProtein2: TextView
    private lateinit var amountGramFat2: TextView

    private var dbwToString = ""
    private var carbMaintainShow = ""
    private var proteinMaintainShow = ""
    private var fatMaintainShow = ""
    private var calorieMaintainShow = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMacrosResultPgBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Inside MacrosResultPg activity

        val selectedGender = intent.getStringExtra("selectedGender")
        val selectedActFactor = intent.getStringExtra("selectedActFactor")
        TER = intent.getIntExtra("TER", 0)

        val ageValue = intent.getIntExtra("ageValue", 0)
        // Convert ageValue to string
        val ageString = ageValue.toString()

        // Retrieve selected gender value from intent extras
        val genderValue = intent.getStringExtra("genderValue")

        // Remove 'val' keyword here
        weightValue = intent.getFloatExtra("weightValue", 0f)
        // Convert weightValue to string
        weightString = weightValue.toString()

        // Remove 'val' keyword here
        heightValue = intent.getFloatExtra("heightValue", 0f)
        // Convert heightValue to string
        val heightString = heightValue.toString()

        dataFactorValue = intent.getIntExtra("dataFactorValue", 0)
        val dataFactorValueString = dataFactorValue.toString()

        // Now you can use the selectedGender and selectedActFactor as needed

        // Set the text of the TextView to the selected gender


        calculateTER()
        computationBMI()
        borderBoxColorChanger()
        val terToString = terVal.toString()


        inputData()




        binding.macrosResultOkBtn.setOnClickListener {
            val intent = Intent(this, MacrosPg::class.java)
            finish()
            overridePendingTransition(0, 0)
            startActivity(intent)
        }
    }

    private fun calculateTER() {
        // Convert weightString to Float, handling possible null value
        weightFloat = weightString.toFloatOrNull() ?: 0f

        // Multiply weightFloat and dataFactorValue
        terVal =
            (weightFloat * dataFactorValue).toInt() // Convert the result back to Int if necessary

        gain1Lbs =
            is1Lbs.toFloat() + (weightFloat * dataFactorValue)// Convert the result back to Int if necessary
        gain2Lbs =
            is2Lbs.toFloat() + (weightFloat * dataFactorValue) // Convert the result back to Int if necessary

        lose1Lbs = (weightFloat * dataFactorValue) - is1Lbs.toFloat() // Convert the result back to Int if necessary
        lose2Lbs = (weightFloat * dataFactorValue) - is2Lbs.toFloat() // Convert the result back to Int if necessary


    }


    private fun computationBMI() {

        val cmToMeter = heightValue / 100
        val cmToMeterSquared = cmToMeter * cmToMeter
        bmi = weightValue / cmToMeterSquared
        bmiToString = String.format("%.2f", bmi)
    }

    private fun borderBoxColorChanger() {
        val includedLayout1: View = findViewById(R.id.macrosBox1Include1)
        val includedLayout2: View = findViewById(R.id.macrosBox1Include2)

        if (bmi <= 18.5) {


            includedLayout1.setBackgroundResource(R.drawable.lightskyblue_macrosborderradius_strokeyellow)
            includedLayout2.setBackgroundResource(R.drawable.lightskyblue_macrosborderradius_strokeyellow)


            carb1Lbs = (gain1Lbs * carbValue.toFloat()) / 4
            protein1Lbs = (gain1Lbs * proteinValue.toFloat()) / 4
            fat1Lbs = (gain1Lbs * fatValue.toFloat()) / 9

            carb2Lbs = (gain2Lbs * carbValue.toFloat()) / 4
            protein2Lbs = (gain2Lbs * proteinValue.toFloat()) / 4
            fat2Lbs = (gain2Lbs * fatValue.toFloat()) / 9


        } else if (bmi <= 24.9) {


            includedLayout1.setBackgroundResource(R.drawable.lightskyblue_macrosborderradius_strokegreen)
            includedLayout2.visibility = View.GONE // Hide the Layout
        } else if (bmi >= 25) {
            includedLayout1.setBackgroundResource(R.drawable.lightskyblue_macrosborderradius_strokered)
            includedLayout2.setBackgroundResource(R.drawable.lightskyblue_macrosborderradius_strokered)
            carb1Lbs = (lose1Lbs * carbValue.toFloat()) / 4
            protein1Lbs = (lose1Lbs * proteinValue.toFloat()) / 4
            fat1Lbs = (lose1Lbs * fatValue.toFloat()) / 9

            carb2Lbs = (lose2Lbs * carbValue.toFloat()) / 4
            protein2Lbs = (lose2Lbs * proteinValue.toFloat()) / 4
            fat2Lbs = (lose2Lbs * fatValue.toFloat()) / 9


        } else {


            includedLayout1.setBackgroundResource(R.drawable.lightskyblue_macrosborderradius_strokered)
        }


    }


    private fun inputData() {


        if (bmi <= 18.5) {
            roundOffValues()
            BMIDialogGain()
            val gain1LbsRoundInt = gain1LbsRound.toInt()
            calorieText1 = binding.macrosBox1Include1.resultGain
            calorieText1.text = "You need " + gain1LbsRoundInt + " Calories per day to Gain 1lbs per week"

            val gain2LbsRoundInt = gain2LbsRound.toInt()
            calorieText2 = binding.macrosBox1Include2.resultGain
            calorieText2.text = "You need " + gain2LbsRoundInt + " Calories per day to Gain 2lbs per week"


            val carb1LbsRoundInt = carb1LbsRound.toInt()
            val carb1LbsToString = carb1LbsRoundInt.toString()
            amountGramCarb1 = binding.macrosBox1Include1.amountGrams1
            amountGramCarb1.text = carb1LbsToString + " grams"

            val carb2LbsRoundInt = carb2LbsRound.toInt()
            val carb2LbsToString = carb2LbsRoundInt.toString()
            amountGramCarb2 = binding.macrosBox1Include2.amountGrams1
            amountGramCarb2.text = carb2LbsToString + " grams"

            val protein1LbsRoundInt = protein1LbsRound.toInt()
            val protein1LbsToString = protein1LbsRoundInt.toString()
            amountGramProtein1 = binding.macrosBox1Include1.amountGrams2
            amountGramProtein1.text = protein1LbsToString + " grams"

            val protein2LbsRoundInt = protein2LbsRound.toInt()
            val protein2LbsToString = protein2LbsRoundInt.toString()
            amountGramProtein2 = binding.macrosBox1Include2.amountGrams2
            amountGramProtein2.text = protein2LbsToString + " grams"

            val fat1LbsRoundInt = fat1LbsRound.toInt()
            val fat1LbsToString = fat1LbsRoundInt.toString()
            amountGramFat1 = binding.macrosBox1Include1.amountGrams3
            amountGramFat1.text = fat1LbsToString + " grams"

            val fat2LbsRoundInt = fat2LbsRound.toInt()
            val fat2LbsToString = fat2LbsRoundInt.toString()
            amountGramFat2 = binding.macrosBox1Include2.amountGrams3
            amountGramFat2.text = fat2LbsToString + " grams"

        } else if (bmi <= 24.9) {
            computeDBW()
            BMIDialogMaintain()
            calorieText1 = binding.macrosBox1Include1.resultGain
            calorieText1.text = "You need " + calorieMaintainShow + " Calories per day to Maintain Your Weight"

            amountGramCarb1 = binding.macrosBox1Include1.amountGrams1
            amountGramCarb1.text = carbMaintainShow + " grams"

            amountGramProtein1 = binding.macrosBox1Include1.amountGrams2
            amountGramProtein1.text = proteinMaintainShow + " grams"

            amountGramFat1 = binding.macrosBox1Include1.amountGrams3
            amountGramFat1.text = fatMaintainShow + " grams"


        } else if (bmi >= 25) {
            roundOffValues()
            BMIDialogLose()
            val lose1LbsRoundInt = lose1LbsRound.toInt()
            val lose1LbsString = lose1LbsRoundInt.toString()
            calorieText1 = binding.macrosBox1Include1.resultGain
            calorieText1.text = "You need " + lose1LbsString + " Calories per day to Lose 1lbs per week"

            val lose2LbsRoundInt = lose2LbsRound.toInt()
            calorieText2 = binding.macrosBox1Include2.resultGain
            calorieText2.text = "You need " + lose2LbsRoundInt + " Calories per day to Lose 2lbs per week"



            val carb1LbsRoundInt = carb1LbsRound.toInt()
            val carb1LbsToString = carb1LbsRoundInt.toString()
            amountGramCarb1 = binding.macrosBox1Include1.amountGrams1
            amountGramCarb1.text = carb1LbsToString + " grams"

            val carb2LbsRoundInt = carb2LbsRound.toInt()
            val carb2LbsToString = carb2LbsRoundInt.toString()
            amountGramCarb2 = binding.macrosBox1Include2.amountGrams1
            amountGramCarb2.text = carb2LbsToString + " grams"

            val protein1LbsRoundInt = protein1LbsRound.toInt()
            val protein1LbsToString = protein1LbsRoundInt.toString()
            amountGramProtein1 = binding.macrosBox1Include1.amountGrams2
            amountGramProtein1.text = protein1LbsToString + " grams"

            val protein2LbsRoundInt = protein2LbsRound.toInt()
            val protein2LbsToString = protein2LbsRoundInt.toString()
            amountGramProtein2 = binding.macrosBox1Include2.amountGrams2
            amountGramProtein2.text = protein2LbsToString + " grams"

            val fat1LbsRoundInt = fat1LbsRound.toInt()
            val fat1LbsToString = fat1LbsRoundInt.toString()
            amountGramFat1 = binding.macrosBox1Include1.amountGrams3
            amountGramFat1.text = fat1LbsToString + " grams"

            val fat2LbsRoundInt = fat2LbsRound.toInt()
            val fat2LbsToString = fat2LbsRoundInt.toString()
            amountGramFat2 = binding.macrosBox1Include2.amountGrams3
            amountGramFat2.text = fat2LbsToString + " grams"

        } else {

        }

    }

    private fun roundOffValues() {

        if (bmi <= 18.5) {
            //Calories amount
            gain1LbsModulus = gain1Lbs % 50
            //If Calorie Intake is Less than 25 which will round off less vale
            if (gain1LbsModulus < 25.00) {
                gain1LbsRound = gain1Lbs - gain1LbsModulus
                gain2LbsRound = gain1LbsRound + 500
            //If Calorie Intake is greater than 25 which will round off high value
            } else if (gain1LbsModulus >= 25.00 ){
                gain1LbsRound = (gain1Lbs - gain1LbsModulus) + 50
                gain2LbsRound = gain1LbsRound + 500
            }
            //Carbs Amount
            carb1LbsModulus = carb1Lbs % 10
            carb2LbsModulus = carb2Lbs % 10

            //if carb 1lbs
            if (carb1LbsModulus < 5.00) {
                carb1LbsRound = carb1Lbs - carb1LbsModulus
            }
            else if (carb1LbsModulus >= 5.00 ) {
                carb1LbsRound = (carb1Lbs - carb1LbsModulus) + 10
            }

            //if carb 2lbs
            if(carb2LbsModulus <5.00)    {
                 carb2LbsRound = carb2Lbs - carb2LbsModulus
            }
           else if(carb2LbsModulus >= 5.00){
                carb2LbsRound = (carb2Lbs - carb2LbsModulus) + 10
            }

                        //Protein
                        //if Protein 1lbs
                        protein1LbsModulus = protein1Lbs % 10
                        protein2LbsModulus = protein2Lbs % 10
                        if (protein1LbsModulus < 5.00) {
                            protein1LbsRound = protein1Lbs - protein1LbsModulus
                        }
                        else if (protein1LbsModulus >= 5.00 ) {
                            protein1LbsRound = (protein1Lbs - protein1LbsModulus) + 10
                        }

                        //if protein 2lbs
                        if(protein2LbsModulus <5.00)    {
                            protein2LbsRound = protein2Lbs - protein2LbsModulus
                        }
                        else if(protein2LbsModulus >= 5.00){
                            protein2LbsRound = (protein2Lbs - protein2LbsModulus) + 10
                        }
                                    //Fats
                                    //if Fats 1lbs
                                    fat1LbsModulus = fat1Lbs % 10
                                    fat2LbsModulus = fat2Lbs % 10
                                    if (fat1LbsModulus < 5.00) {
                                        fat1LbsRound = fat1Lbs - fat1LbsModulus
                                    }
                                    else if (fat1LbsModulus >= 5.00 ) {
                                        fat1LbsRound = (fat1Lbs - fat1LbsModulus) + 10
                                    }

                                    //if fat 2lbs
                                    if(fat2LbsModulus < 5.00)    {
                                        fat2LbsRound = fat2Lbs - fat2LbsModulus
                                    }
                                    else if(fat2LbsModulus >= 5.00){
                                        fat2LbsRound = (fat2Lbs - fat2LbsModulus) + 10
                                    }

        }
         else if (bmi <= 24.9) {


        } else if (bmi >= 25) {
            //Calories amount
            lose1LbsModulus = lose1Lbs % 50
            lose2LbsModulus = lose2Lbs % 50
            //If Calorie Intake is Less than 25 which will round off less vale
            if (lose1LbsModulus < 25.00) {
                lose1LbsRound = lose1Lbs - lose1LbsModulus
                lose2LbsRound = lose1LbsRound - 500
                //If Calorie Intake is greater than 25 which will round off high value
            }
            else if (lose1LbsModulus >= 25.00 ){
                lose1LbsRound = (lose1Lbs - lose1LbsModulus) + 50
                lose2LbsRound = lose1LbsRound - 500
            }
            //Carbs Amount
            carb1LbsModulus = carb1Lbs % 10
            carb2LbsModulus = carb2Lbs % 10

            //if carb 1lbs
            if (carb1LbsModulus < 5.00) {
                carb1LbsRound = carb1Lbs - carb1LbsModulus
            }
            else if (carb1LbsModulus >= 5.00 ) {
                carb1LbsRound = (carb1Lbs - carb1LbsModulus) + 10
            }

            //if carb 2lbs
            if(carb2LbsModulus <5.00)    {
                carb2LbsRound = carb2Lbs - carb2LbsModulus
            }
            else if(carb2LbsModulus >= 5.00){
                carb2LbsRound = (carb2Lbs - carb2LbsModulus) + 10
            }

            //Protein
            //if Protein 1lbs
            protein1LbsModulus = protein1Lbs % 10
            protein2LbsModulus = protein2Lbs % 10
            if (protein1LbsModulus < 5.00) {
                protein1LbsRound = protein1Lbs - protein1LbsModulus
            }
            else if (protein1LbsModulus >= 5.00 ) {
                protein1LbsRound = (protein1Lbs - protein1LbsModulus) + 10
            }

            //if protein 2lbs
            if(protein2LbsModulus <5.00)    {
                protein2LbsRound = protein2Lbs - protein2LbsModulus
            }
            else if(protein2LbsModulus >= 5.00){
                protein2LbsRound = (protein2Lbs - protein2LbsModulus) + 10
            }
            //Fats
            //if Fats 1lbs
            fat1LbsModulus = fat1Lbs % 10
            fat2LbsModulus = fat2Lbs % 10
            if (fat1LbsModulus < 5.00) {
                fat1LbsRound = fat1Lbs - fat1LbsModulus
            }
            else if (fat1LbsModulus >= 5.00 ) {
                fat1LbsRound = (fat1Lbs - fat1LbsModulus) + 10
            }

            //if fat 2lbs
            if(fat2LbsModulus < 5.00)    {
                fat2LbsRound = fat2Lbs - fat2LbsModulus
            }
            else if(fat2LbsModulus >= 5.00){
                fat2LbsRound = (fat2Lbs - fat2LbsModulus) + 10
            }


        }
        else {

        }
    }
    private fun computeDBW(){
        var dbw = (heightValue - 100) - (0.10 * (heightValue - 100))
        var dbw2Dec = String.format("%.2f", dbw)
        var dbw2DecFloat = dbw2Dec.toFloat()
        dbwToString = dbw2DecFloat.toString()

        computeMaintainWeight()
    }
    private fun computeMaintainWeight(){
        var dbwValue = dbwToString.toFloat()
        var dbwValue2Dec = String.format("%.2f", dbwValue)
        var maintainCalorie  = dbwValue * dataFactorValue
        var maintainCalorie2Dec = String.format("%.2f", maintainCalorie)
        var maintainCalorie2DecModulus = maintainCalorie2Dec.toFloat() % 50

        maintainCalorieString = maintainCalorie2Dec.toString()

        if(maintainCalorie2DecModulus < 50){
            var calorieMaintainRound =  maintainCalorie2Dec.toFloat() - maintainCalorie2DecModulus
            var calorieMaintainInt = calorieMaintainRound.toInt()
            calorieMaintainShow  = calorieMaintainInt.toString()
        }
        else if(maintainCalorie2DecModulus >= 50){
            var calorieMaintainRound =  (maintainCalorie2Dec.toFloat() - maintainCalorie2DecModulus) + 100
            var calorieMaintainInt = calorieMaintainRound.toInt()
            calorieMaintainShow  = calorieMaintainInt.toString()
        }


        var carbMaintain = (maintainCalorie * 0.6) / 4
        var carbMaintain2Dec =  String.format("%.2f", carbMaintain)
        var carbMaintainModulusVal = carbMaintain2Dec.toFloat() % 10

        if(carbMaintainModulusVal < 5){
                var carbMaintainRound =  carbMaintain2Dec.toFloat() - carbMaintainModulusVal
                var carbMaintainShowInt  = carbMaintainRound.toInt()
                carbMaintainShow  = carbMaintainShowInt.toString()
            }
        else if(carbMaintainModulusVal >= 5){
            var carbMaintainRound =  carbMaintain2Dec.toFloat() + carbMaintainModulusVal
            var carbMaintainShowInt  = carbMaintainRound.toInt()
             carbMaintainShow  = carbMaintainShowInt.toString()
        }

        var proteinMaintain=(maintainCalorie * 0.15) / 4
        var proteinMaintain2Dec =  String.format("%.2f", proteinMaintain)
       var proteinMaintain2DecFloat = proteinMaintain2Dec.toFloat()
        var proteinMaintainModulusVal = proteinMaintain2DecFloat % 10

        if(proteinMaintainModulusVal < 5){
            var proteinMaintainRound = proteinMaintain2DecFloat - proteinMaintainModulusVal
            var proteinMaintainShowInt = proteinMaintainRound.toInt()
            proteinMaintainShow  = proteinMaintainShowInt.toString()
        }
        else if(proteinMaintainModulusVal >= 5){
            var proteinMaintainRound =  proteinMaintain2DecFloat + proteinMaintainModulusVal
            var proteinMaintainShowInt = proteinMaintainRound.toInt()
            proteinMaintainShow  = proteinMaintainShowInt.toString()
        }

        var fatMaintain =  (maintainCalorie * 0.25) / 9
        var fatMaintain2Dec =  String.format("%.2f", fatMaintain)
        var fatMaintain2DecFloat = fatMaintain2Dec.toFloat()
        var fatMaintainModulusVal = fatMaintain2DecFloat % 10


        if(fatMaintainModulusVal < 5){
            var fatMaintainRound =  fatMaintain2DecFloat - fatMaintainModulusVal
            var fatMaintainRoundInt = fatMaintainRound.toInt()
            fatMaintainShow  = fatMaintainRoundInt.toString()
        }
        else if(fatMaintainModulusVal >= 5){
            var fatMaintainRound =  fatMaintain2Dec.toFloat() + fatMaintainModulusVal
            var fatMaintainRoundInt = fatMaintainRound.toInt()
            fatMaintainShow  = fatMaintainRoundInt.toString()
        }

    }

    //Dialog if NUTRITIONAL STATUS IS UnderWeight
    private fun BMIDialogGain(){

            // Inflate the custom dialog layout
            val dialogViewGain = layoutInflater.inflate(R.layout.macros_bmigain_dialog, null)

            // Create an AlertDialog builder and set the custom view
            val builder = AlertDialog.Builder(this)
                .setView(dialogViewGain)

            // Create the AlertDialog object
            val alertDialog = builder.create()

            // Find the Yes and No buttons in the dialog layout
            val buttonOk = dialogViewGain.findViewById<TextView>(R.id.buttonOk)

            // Set background color for the Yes button
            buttonOk.setBackgroundResource(R.drawable.warmup_greenbackground_borderadius20)

            // Set click listeners for the Yes and No buttons
            buttonOk.setOnClickListener {
                // Handle Yes button click
                Toast.makeText(applicationContext, "Clicked OK", Toast.LENGTH_SHORT).show()
                alertDialog.dismiss()

            }



            // Show the AlertDialog
            alertDialog.show()

    }



    //Dialog if NUTRITIONAL STATUS IS Maintain Weight
    private fun BMIDialogLose(){

            // Inflate the custom dialog layout
            val dialogViewGain = layoutInflater.inflate(R.layout.macros_bmilose_dialog, null)

            // Create an AlertDialog builder and set the custom view
            val builder = AlertDialog.Builder(this)
                .setView(dialogViewGain)

            // Create the AlertDialog object
            val alertDialog = builder.create()

            // Find the Yes and No buttons in the dialog layout
            val buttonOk = dialogViewGain.findViewById<TextView>(R.id.buttonOk)

            // Set background color for the Yes button
            buttonOk.setBackgroundResource(R.drawable.warmup_greenbackground_borderadius20)

            // Set click listeners for the Yes and No buttons
            buttonOk.setOnClickListener {
                // Handle Yes button click

                alertDialog.dismiss()
            }
            // Show the AlertDialog
            alertDialog.show()
    }

    private fun BMIDialogMaintain(){
         // Inflate the custom dialog layout
        val dialogViewGain = layoutInflater.inflate(R.layout.macros_bmimaintain_dialog, null)

        // Create an AlertDialog builder and set the custom view
        val builder = AlertDialog.Builder(this)
            .setView(dialogViewGain)

        // Create the AlertDialog object
        val alertDialog = builder.create()

        // Find the Yes and No buttons in the dialog layout
        val buttonOk = dialogViewGain.findViewById<TextView>(R.id.buttonOk)

        // Set background color for the Yes button
        buttonOk.setBackgroundResource(R.drawable.warmup_greenbackground_borderadius20)

        // Set click listeners for the Yes and No buttons
        buttonOk.setOnClickListener {
            // Handle Yes button click
            Toast.makeText(applicationContext, "Clicked OK", Toast.LENGTH_SHORT).show()
            alertDialog.dismiss()
        }
        // Show the AlertDialog
        alertDialog.show()

    }

}