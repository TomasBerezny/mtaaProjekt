package com.example.kdesikamos.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.bumptech.glide.Glide
import com.example.kdesikamos.DbApi
import com.example.kdesikamos.R
import com.example.kdesikamos.dto.UserActivity
import com.example.kdesikamos.ui.auth.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ActivityActivity : AppCompatActivity() {

    private lateinit var tvUsername : TextView
    private lateinit var tvActivity : TextView
    private lateinit var tvDescription : TextView
    private lateinit var ivProfilePic: ImageView
    private lateinit var btnAddToActivity: ImageView

    private val baseUrl = "http://10.0.2.2:8000/"
    private lateinit var dbApi: DbApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activity)

        tvUsername = findViewById(R.id.tvActivityUsername)
        tvActivity = findViewById(R.id.tvActivityTitle)
        tvDescription = findViewById(R.id.tvActivityDescription)
        ivProfilePic = findViewById(R.id.ivActivityProfilePic)
        btnAddToActivity = findViewById(R.id.btnActivityAddTo)

        setScreen()

        btnAddToActivity.setOnClickListener {
            addUserToActivity()
        }
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

    private fun addUserToActivity(){
        buildRetrofit()
        val atributes = UserActivity(HomeFragment.clickedActivity._id, LoginActivity.loggedUser._id)
        val call = dbApi.addUserToActivity(atributes)

        call.enqueue(object: Callback<UserActivity> {
            override fun onResponse(call: Call<UserActivity>, response: Response<UserActivity>) {
                if (response.code() != 200){
                    return
                }

                Toast.makeText(this@ActivityActivity, "Added to activity", Toast.LENGTH_SHORT)
            }

            override fun onFailure(call: Call<UserActivity>, t: Throwable) {
                Log.d("daco nedobre", t.toString())
            }

        })
    }

    fun buildRetrofit() {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        dbApi = retrofit.create(DbApi::class.java)
    }
}