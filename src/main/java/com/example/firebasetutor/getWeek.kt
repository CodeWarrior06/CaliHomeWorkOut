package com.example.firebasetutor

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class getWeek : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_week)

        val currentDate = Calendar.getInstance()

        val dateTextViews = mutableListOf<TextView>()
        dateTextViews.add(findViewById(R.id.monday_date))


        val dateFormat = SimpleDateFormat("EEE, MMM dd", Locale.getDefault())

        for (i in 0 until 8) {
            val date = currentDate.clone() as Calendar
            date.add(Calendar.DAY_OF_WEEK, i) // i represents the days ahead from the current day
            dateTextViews[i].text = "${dateFormat.format(date.time)}"
        }
    }
}
