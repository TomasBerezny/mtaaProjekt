package com.example.kdesikamos.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.kdesikamos.DbApi
import com.example.kdesikamos.MainActivity
import com.example.kdesikamos.R
import com.example.kdesikamos.dto.LoginRequest
import com.example.kdesikamos.dto.UserDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var tvLogin : EditText
    private lateinit var tvPassword : EditText
    private lateinit var btnLogin : Button
    private lateinit var tvRegister: TextView

    private val baseUrl = "http://10.0.2.2:8000/"
    private lateinit var dbApi: DbApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tvLogin = findViewById(R.id.tvLoginUsername)
        tvPassword = findViewById(R.id.tvLoginPassword)
        btnLogin = findViewById(R.id.btnLoginLogin)
        tvRegister = findViewById(R.id.tvLoginRegister)

        btnLogin.setOnClickListener {
            login()
        }

        tvRegister.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login(){
        buildRetrofit()
        val credentials = LoginRequest(tvLogin.text.toString(), tvPassword.text.toString())
        val call = dbApi.login(credentials)

        call.enqueue(object: Callback<UserDTO> {
            override fun onResponse(call: Call<UserDTO>, response: Response<UserDTO>) {
                if (response.code() == 404){
                    Toast.makeText(this@LoginActivity, "Wrong credentials", Toast.LENGTH_SHORT)
                    return
                }
                else if (response.code() != 200){
                    Toast.makeText(this@LoginActivity, "Something went wrong", Toast.LENGTH_SHORT)
                    return
                }

                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
            }

            override fun onFailure(call: Call<UserDTO>, t: Throwable) {
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