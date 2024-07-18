// MainActivity.kt
package com.example.firebasetutor.routine

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasetutor.R
import com.example.firebasetutor.databinding.ActivityDashboard2Binding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class Dashboard2 : AppCompatActivity() {
    private lateinit var binding: ActivityDashboard2Binding
    private lateinit var container: LinearLayout
    private val numberOfItems = 10
    private val dateFormat = SimpleDateFormat("MMMM d, yyyy", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboard2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        container = findViewById(R.id.container)

        // Initially populate the TextViews
        populateTextViews()

        // Update the TextViews every 60 seconds to check for date change
        val handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable {
            override fun run() {
                populateTextViews()
                handler.postDelayed(this, 60 * 1000) // Update every 60 seconds
            }
        })
    }

    private fun populateTextViews() {
        container.removeAllViews() // Clear existing views
        val currentDate = Calendar.getInstance().time

        for (i in 1 until numberOfItems) {
            val itemView = LayoutInflater.from(this).inflate(R.layout.item_date, container, false)
            val titleTextView = itemView.findViewById<TextView>(R.id.item_title)
            val dateTextView = itemView.findViewById<TextView>(R.id.item_date)

            titleTextView.text = "Title $i"
            val calendar = Calendar.getInstance()
            calendar.time = currentDate
            calendar.add(Calendar.DAY_OF_YEAR, i)
            val date = calendar.time

            dateTextView.text = dateFormat.format(date)
            container.addView(itemView)
        }
    }
}
