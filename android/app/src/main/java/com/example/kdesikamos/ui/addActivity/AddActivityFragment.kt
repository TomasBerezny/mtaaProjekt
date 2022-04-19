package com.example.kdesikamos.ui.addActivity

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.kdesikamos.DbApi
import com.example.kdesikamos.databinding.FragmentAddActivityBinding
import com.example.kdesikamos.dto.*
import com.example.kdesikamos.ui.auth.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


class AddActivityFragment : Fragment() {

    private var _binding: FragmentAddActivityBinding? = null

    private val baseUrl = "http://10.0.2.2:8000/"
    private lateinit var dbApi: DbApi

    private lateinit var categories: List<CategoryDTO>
    private lateinit var categoriesNames: MutableList<String>
    private lateinit var date: Date

    private var datePickerDialog: DatePickerDialog? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
                ViewModelProvider(this).get(AddActivityViewModel::class.java)

        _binding = FragmentAddActivityBinding.inflate(inflater, container, false)
        val root: View = binding.root


        initDatePicker();
        binding.datePickerButton.setText(getTodaysDate());
        binding.datePickerButton.setOnClickListener {
            openDatePicker(null)
        }
        buildRetrofit()
        setSpinner()
        binding.btnAddSave.setOnClickListener {
            saveActivity()
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setSpinner(){
        val call = dbApi.getCategories()

        call.enqueue(object: Callback<List<CategoryDTO>> {
            override fun onResponse(call: Call<List<CategoryDTO>>, response: Response<List<CategoryDTO>>) {
                if(response.code() != 200)
                    return
                categories = response.body()!!
                categoriesNames = mutableListOf()
                for (category in categories){
                    categoriesNames.add(category.name)
                }

                val adapter = ArrayAdapter(requireContext(),
                    android.R.layout.simple_spinner_item, categoriesNames)
                binding.spinAddCategory.adapter = adapter
            }

            override fun onFailure(call: Call<List<CategoryDTO>>, t: Throwable) {
                Log.d("daco nedobre", t.toString())
            }

        })
    }

    private fun saveActivity(){
        buildRetrofit()
        val category_pos = binding.spinAddCategory.selectedItemPosition
        val request = ActivityRequest(
            "",
            categories.get(category_pos)._id,
            binding.etAddPlace.text.toString(),
            LoginActivity.loggedUser._id,
            binding.etAddDesctiption.text.toString(),
            date,
            true,
            false,
            "null",
        )
        val call = dbApi.postActivity(request)

        call.enqueue(object: Callback<ActivityRequest> {
            override fun onResponse(call: Call<ActivityRequest>, response: Response<ActivityRequest>) {
                if (response.code() != 201){
                    Log.d("daco", "daco se pokazilo?")
                    return
                }
                Toast.makeText( context, "Activity created", Toast.LENGTH_SHORT)
            }

            override fun onFailure(call: Call<ActivityRequest>, t: Throwable) {
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


    private fun initDatePicker() {
        val dateSetListener =
            OnDateSetListener { datePicker, year, month, day ->
                var month = month + 1
                val datee: String = ""+day+"."+month+"."+year
                var cal = Calendar.getInstance()
                cal.set(year, month, day, 0, 0);
                date = cal.time
                binding.datePickerButton.setText(datee)
            }
        val cal: Calendar = Calendar.getInstance()
        val year: Int = cal.get(Calendar.YEAR)
        val month: Int = cal.get(Calendar.MONTH)
        val day: Int = cal.get(Calendar.DAY_OF_MONTH)
        val style: Int = AlertDialog.THEME_HOLO_LIGHT
        datePickerDialog = DatePickerDialog(requireContext(), style, dateSetListener, year, month, day)
    }

    fun openDatePicker(view: View?) {
        datePickerDialog!!.show()
    }

    private fun getTodaysDate(): String? {
        val cal = Calendar.getInstance()
        val year = cal[Calendar.YEAR]
        var month = cal[Calendar.MONTH]
        month = month + 1
        val day = cal[Calendar.DAY_OF_MONTH]
        date = cal.time
        return ""+day+"."+month+"."+year
    }
}