package com.example.firebasetutor

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class MyAdapter(private val context : Activity, private val arrayList : ArrayList<User>) :
    ArrayAdapter<User>(context, R.layout.list_item,arrayList)  {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View{

            val inflater : LayoutInflater = LayoutInflater.from(context)
            val view : View = inflater.inflate(R.layout.list_item, null)

            val imageView : ImageView = view.findViewById(R.id.foodImage)
            val foodName : TextView = view.findViewById(R.id.foodName)
            val foodGram : TextView = view.findViewById(R.id.foodGram)
            val foodCalorie : TextView = view.findViewById(R.id.foodCalorie)
            val foodCarb : TextView = view.findViewById(R.id.foodCarb)
            val foodFat : TextView = view.findViewById(R.id.foodFat)
            val foodProtein : TextView = view.findViewById(R.id.foodProtein)


            imageView.setImageResource(arrayList[position].foodImage)
            foodName.text = arrayList[position].foodName
            foodGram.text = arrayList[position].foodGram
            foodCalorie.text = "Calories: " + arrayList[position].foodCalorie
            foodCarb.text = "Carbs: " + arrayList[position].foodCarb
            foodFat.text = "Fats: " + arrayList[position].foodFat
            foodProtein.text = "Protein: " + arrayList[position].foodProtein

            return view
        }
    }