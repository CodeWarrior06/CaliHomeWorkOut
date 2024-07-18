package com.example.firebasetutor

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class textViewClicked : AppCompatActivity() {

    // SharedPreferences file name
    private val PREFS_NAME = "MyPrefs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_view_clicked)

        // Initialize SharedPreferences
        val prefs: SharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        // Get references to TextViews
        val textView1 = findViewById<TextView>(R.id.textView1)
        val textView2 = findViewById<TextView>(R.id.textView2)
        val textView3 = findViewById<TextView>(R.id.textView3)
        val textView4 = findViewById<TextView>(R.id.textView4)
        val textView5 = findViewById<TextView>(R.id.textView5)
        val button123 = findViewById<TextView>(R.id.button123)

        // Check the state of each TextView and hide if necessary
        if (prefs.getBoolean("textView1Clicked", false)) {
            textView1.visibility = TextView.VISIBLE
        }
        if (prefs.getBoolean("textView2Clicked", false)) {
            textView2.visibility = TextView.VISIBLE
        }
        if (prefs.getBoolean("textView3Clicked", false)) {
            textView3.visibility = TextView.VISIBLE
        }
        if (prefs.getBoolean("textView4Clicked", false)) {
            textView4.visibility = TextView.VISIBLE
        }
        if (prefs.getBoolean("textView5Clicked", false)) {
            textView5.visibility = TextView.VISIBLE
        }

        // Set click listeners for each TextView
        textView1.setOnClickListener {
            // Mark TextView as clicked in SharedPreferences
            prefs.edit().putBoolean("textView1Clicked", true).apply()
            // Hide the clicked TextView
            textView1.visibility = TextView.GONE
        }

        textView2.setOnClickListener {
            prefs.edit().putBoolean("textView2Clicked", true).apply()
            textView2.visibility = TextView.GONE
        }

        textView3.setOnClickListener {
            prefs.edit().putBoolean("textView3Clicked", true).apply()
            textView3.visibility = TextView.GONE
        }

        textView4.setOnClickListener {
            prefs.edit().putBoolean("textView4Clicked", true).apply()
            textView4.visibility = TextView.GONE
        }

        textView5.setOnClickListener {
            prefs.edit().putBoolean("textView5Clicked", true).apply()
            textView5.visibility = TextView.GONE
        }
        button123.setOnClickListener {
            val intent = Intent(this, tryxml::class.java)
            startActivity(intent)
        }
    }
}
