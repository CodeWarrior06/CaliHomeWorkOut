package com.example.firebasetutor.beginner

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import com.example.firebasetutor.R
import com.example.firebasetutor.databinding.ActivityBeginnerPhase1ScrollBarBinding


@Suppress("DEPRECATION")
class BeginnerPhase1ScrollBar : AppCompatActivity() {
    private lateinit var binding: ActivityBeginnerPhase1ScrollBarBinding
    private var isScrollEnabled = false
    private lateinit var scrollView : NestedScrollView

    private lateinit var nextBtnOverview : TextView
    private lateinit var nextBtn1 : TextView
    private lateinit var nextBtn2 : TextView
    private lateinit var nextBtn3 : TextView
    private lateinit var nextBtn4 : TextView
    private lateinit var nextBtn5 : TextView
    private lateinit var nextBtn6: TextView
    private lateinit var nextBtnStretching : TextView

    private lateinit var startStretching : TextView
    private lateinit var container1 : View
    private lateinit var container2 : View
    private lateinit var container3 : View
    private lateinit var container4 : View
    private lateinit var container5 : View
    private lateinit var container6 : View
    private lateinit var containerLast : View

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBeginnerPhase1ScrollBarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        scrollView = binding.myScrollView

        containerLast = binding.containerLast
        container5 = binding.container5
        container4 = binding.container4
        container3 = binding.container3
        container2 = binding.container2
        container1 = binding.container1


        val includeStretch1 = findViewById<View>(R.id.includeStretch1)
        val imageV = includeStretch1.findViewById<ImageView>(R.id.imageReuse)
        imageV.setImageResource(R.drawable.warmupbeginner)


        scrollBtnOverview()
        scrollBtnWarmUp1()
        scrollBtnWarmUp2()
        scrollBtnWarmUp3()
        scrollBtnWarmUp4()
        scrollBtnWarmUp5()

       // scrollBtnWarmUp3()
    // scrollBtnWarmUp4()







    }

    private fun scrollBtnOverview(){
        val includeStretchOverview = findViewById<View>(R.id.includeStretchOverview)
        val nextBtnOverview = includeStretchOverview.findViewById<TextView>(R.id.nextBtn)



        nextBtnOverview.setOnClickListener {
            // Scroll to the bottom-most container
            enableScrolling()

            scrollView.smoothScrollTo(0, container1.top)
            scrollBtnTrigger()
        }
    }





    private fun scrollBtnWarmUp1(){
        scrollBtnTrigger()  //Disable manual scrolling
        val includeStretch1 = findViewById<View>(R.id.includeStretch1)
        nextBtn1 = includeStretch1.findViewById(R.id.nextBtn)


       nextBtn1.setOnClickListener {
            // Scroll to the bottom-most container

            isScrollEnabled = true
            scrollView.smoothScrollTo(0, container2.top)
            scrollBtnTrigger()
        }
    }


    private fun scrollBtnWarmUp2(){
        scrollBtnTrigger()  //Disable manual scrolling
        val includeStretch2 = findViewById<View>(R.id.includeStretch2)
        nextBtn2 = includeStretch2.findViewById(R.id.nextBtn)


        nextBtn2.setOnClickListener {
            // Scroll to the bottom-most container

            isScrollEnabled = true
            scrollView.smoothScrollTo(0, container3.top)
            scrollBtnTrigger()
        }
    }

    private fun scrollBtnWarmUp3(){
        scrollBtnTrigger()  //Disable manual scrolling
        val includeStretch3 = findViewById<View>(R.id.includeStretch3)
        nextBtn3 = includeStretch3.findViewById(R.id.nextBtn)


        nextBtn3.setOnClickListener {
            // Scroll to the bottom-most container

            isScrollEnabled = true
            scrollView.smoothScrollTo(0, container4.top)
            scrollBtnTrigger()
        }
    }

    private fun scrollBtnWarmUp4(){
        scrollBtnTrigger()  //Disable manual scrolling
        val includeStretch4 = findViewById<View>(R.id.includeStretch4)
        nextBtn4 = includeStretch4.findViewById(R.id.nextBtn)


        nextBtn4.setOnClickListener {
            // Scroll to the bottom-most container

            isScrollEnabled = true
            scrollView.smoothScrollTo(0, container5.top)
            scrollBtnTrigger()
        }
    }

    private fun scrollBtnWarmUp5() {
        scrollBtnTrigger()  //Disable manual scrolling
        val includeStretch5 = findViewById<View>(R.id.includeStretch5)

        nextBtn5 = includeStretch5.findViewById(R.id.nextBtn)


        nextBtn5.setOnClickListener {
            // Scroll to the bottom-most container
            isScrollEnabled = true
            scrollView.smoothScrollTo(0, containerLast.top)
            scrollBtnTrigger()
        }

    }
























    private fun scrollBtnTrigger(){
        //Scroll Button to Container 2
        scrollView.setOnTouchListener { _, event ->
            if (!isScrollEnabled) {
                // Disable scrolling if not enabled
                true
            }
            else {
                // Allow scrolling if enabled
                false
            }
        }
    }

    private fun enableScrolling() {
        scrollView.setOnTouchListener { _, event ->
            if (!isScrollEnabled) {
                // Enable scrolling if not enabled
                isScrollEnabled = true
            }
            false // Always return false to allow touch events to be processed normally
        }
    }

    private fun disableScrolling() {
        scrollView.setOnTouchListener { _, _ ->
            true // Return true to indicate that touch events should be consumed and scrolling should be disabled
        }
    }



}