package com.example.kdesikamos.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.kdesikamos.DbApi
import com.example.kdesikamos.R

class ActivityActivity : AppCompatActivity() {

    private lateinit var tvUsername : TextView
    private lateinit var tvActivity : TextView
    private lateinit var tvDescription : TextView
    private lateinit var ivProfilePic: ImageView

    private val baseUrl = "http://10.0.2.2:8000/"
    private lateinit var dbApi: DbApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activity)

        tvUsername = findViewById(R.id.tvActivityUsername)
        tvActivity = findViewById(R.id.tvActivityTitle)
        tvDescription = findViewById(R.id.tvActivityDescription)
        ivProfilePic = findViewById(R.id.ivActivityProfilePic)

        setScreen()
    }

    private fun setScreen(){
        val activity = HomeFragment.clickedActivity
        tvUsername.setText(activity.username)
        tvActivity.setText(activity.category_name)
        tvDescription.setText(activity.description)

        if(activity.profile_pic.isNotBlank()){
            Glide.with(this).load(activity.profile_pic).into(ivProfilePic)
        }



    }
}