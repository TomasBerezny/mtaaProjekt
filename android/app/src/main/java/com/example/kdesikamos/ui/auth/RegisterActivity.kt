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
import com.example.kdesikamos.dto.RegisterRequest
import com.example.kdesikamos.dto.UserDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterActivity : AppCompatActivity() {

    private lateinit var etFirstName : EditText
    private lateinit var etLastName : EditText
    private lateinit var btnRegister : Button
    private lateinit var etUsername: EditText
    private lateinit var etPassword : EditText
    private lateinit var etPasswordRepeat : EditText

    private val baseUrl = "http://10.0.2.2:8000/"
    private lateinit var dbApi: DbApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etFirstName = findViewById(R.id.etRegisterFirstName)
        etLastName = findViewById(R.id.etRegisterLastName)
        btnRegister = findViewById(R.id.btnRegisterRegister)
        etUsername = findViewById(R.id.etRegisterUsername)
        etPassword = findViewById(R.id.etRegisterPassword)
        etPasswordRepeat = findViewById(R.id.etRegisterPasswordRep)


        btnRegister.setOnClickListener {
            register()
        }
    }

    private fun register(){
        if (etPassword.text.toString() != etPasswordRepeat.text.toString()){
            Toast.makeText(this.applicationContext, "Passwords doesnt match", Toast.LENGTH_SHORT)
            return
        }
        buildRetrofit()
        val atributes = RegisterRequest(
            etUsername.text.toString(),
            etPassword.text.toString(),
            etFirstName.text.toString(),
            etLastName.text.toString(),
        )

        val call = dbApi.register(atributes)

        call.enqueue(object: Callback<UserDTO> {
            override fun onResponse(call: Call<UserDTO>, response: Response<UserDTO>) {
                if (response.code() == 200){
                    Toast.makeText(this@RegisterActivity, "You can now login", Toast.LENGTH_SHORT)
                    finish()
                }
                else if (response.code() == 406){
                    Toast.makeText(this@RegisterActivity, "Chosen username is already in use", Toast.LENGTH_SHORT)
                    return
                }
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