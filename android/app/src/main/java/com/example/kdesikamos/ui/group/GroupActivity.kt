package com.example.kdesikamos.ui.group

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.kdesikamos.R

class GroupActivity : AppCompatActivity() {

    private lateinit var tvName: TextView
    private lateinit var tvBio: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)

        tvName = findViewById(R.id.tvGroupGroupUsername)
        tvBio = findViewById(R.id.tvGroupGroupBio)

        setScreen()
    }

    private fun setScreen(){
        val group = GroupFragment.clickedGroup
        tvName.setText(group.name)
        tvBio.setText(group.bio)
    }
}