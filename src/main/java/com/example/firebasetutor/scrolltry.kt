package com.example.firebasetutor

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView

class scrolltry : AppCompatActivity() {

    private var isScrollEnabled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolltry)

        val scrollView = findViewById<NestedScrollView>(R.id.my_scroll_view)
        val scrollButton = findViewById<Button>(R.id.scrollButton)
        val container3 = findViewById<LinearLayout>(R.id.container3)
        val container2 = findViewById<LinearLayout>(R.id.container2)




        scrollButton.setOnClickListener {
            // Scroll to the bottom-most container

            isScrollEnabled = true
            scrollView.smoothScrollTo(0, container3.top)
        }

        scrollView.setOnTouchListener { _, event ->
            if (!isScrollEnabled) {
                // Disable scrolling if not enabled
                true
            } else {
                // Allow scrolling if enabled
                false
            }
        }
    }
}
