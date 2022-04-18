package com.example.kdesikamos.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kdesikamos.DbApi
import com.example.kdesikamos.adapters.RecyclerViewHome
import com.example.kdesikamos.databinding.FragmentHomeBinding
import com.example.kdesikamos.dto.ActivityDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var recycler : RecyclerView
    private lateinit var dbApi: DbApi

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val baseUrl = "http://10.0.2.2:8000/"

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        buildRetrofit()
        buildActivities()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun buildRetrofit() {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        dbApi = retrofit.create(DbApi::class.java)
    }

    fun buildActivities(){
        val call = dbApi.getActivities()

        call.enqueue(object: Callback<List<ActivityDTO>>{

            override fun onResponse(call: Call<List<ActivityDTO>>, response: Response<List<ActivityDTO>>) {
                if (response.code() != 200){
                    return
                }
                val activities: List<ActivityDTO>? = response.body()
                activities?.let {
                    setRecycler(activities)
                }
            }

            override fun onFailure(call: Call<List<ActivityDTO>>, t: Throwable) {
                t.message?.let { Log.d("neide aktivita", it) }
            }

        })
    }

    fun setRecycler(list:List<ActivityDTO>){
        for (activity in list){
            if(activity.profile_pic.isNotBlank()){
                activity.profile_pic = activity.profile_pic.replace("\\","/")
                activity.profile_pic = baseUrl + activity.profile_pic
            }
        }
        val layoutManager = LinearLayoutManager(this.context)
        binding.rvHome.layoutManager = layoutManager

        var adapter = RecyclerViewHome(this.context, list)

        binding.rvHome.adapter = adapter
    }
}