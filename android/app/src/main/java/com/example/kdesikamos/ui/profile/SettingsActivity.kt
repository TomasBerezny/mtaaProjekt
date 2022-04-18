package com.example.kdesikamos.ui.profile

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
import com.example.kdesikamos.dto.Settings
import com.example.kdesikamos.dto.UserDTO
import com.example.kdesikamos.ui.auth.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SettingsActivity : AppCompatActivity() {

    private lateinit var etFirstName : EditText
    private lateinit var etLastName : EditText
    private lateinit var etBio : EditText
    private lateinit var etPhoneNumber: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnSave: Button

    private val baseUrl = "http://10.0.2.2:8000/"
    private lateinit var dbApi: DbApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        etFirstName = findViewById(R.id.etSettingsFirstName)
        etLastName = findViewById(R.id.etSettingsLastName)
        etBio = findViewById(R.id.etSettingsBio)
        etPhoneNumber = findViewById(R.id.etSettingsPhoneNumber)
        etPassword = findViewById(R.id.etSettingsPassword)
        btnSave = findViewById(R.id.btnSettingsSave)

        setValues()

        btnSave.setOnClickListener {
            save()
        }
    }

    private fun setValues(){
        val user = LoginActivity.loggedUser

        etFirstName.setText(user.first_name)
        etLastName.setText(user.last_name)
        etBio.setText(user.bio)
        etPhoneNumber.setText(user.phone_number)
    }

    private fun save(){
        buildRetrofit()
        val atributes = Settings(
            etBio.text.toString(),
            etPhoneNumber.text.toString(),
            etPassword.text.toString(),
            etFirstName.text.toString(),
            etLastName.text.toString(),
        )
        val call = dbApi.saveSettings(LoginActivity.loggedUser._id, atributes)

        call.enqueue(object: Callback<UserDTO> {
            override fun onResponse(call: Call<UserDTO>, response: Response<UserDTO>) {
                if (response.code() == 400){
                    Toast.makeText(this@SettingsActivity, response.body().toString(), Toast.LENGTH_SHORT)
                    return
                }
                else if (response.code() != 200){
                    Toast.makeText(this@SettingsActivity, "Something went wrong", Toast.LENGTH_SHORT)
                    return
                }
                response.body()?.let {
                    LoginActivity.loggedUser = it
                }
                Toast.makeText(this@SettingsActivity, "Updatnute", Toast.LENGTH_SHORT)
                return
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