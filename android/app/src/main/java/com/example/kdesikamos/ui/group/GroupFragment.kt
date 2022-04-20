package com.example.kdesikamos.ui.group

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kdesikamos.DbApi
import com.example.kdesikamos.adapters.RecyclerViewGroup
import com.example.kdesikamos.adapters.RecyclerViewHome
import com.example.kdesikamos.databinding.FragmentGroupBinding
import com.example.kdesikamos.dto.ActivityDTO
import com.example.kdesikamos.dto.GroupDTO
import com.example.kdesikamos.ui.auth.LoginActivity
import com.example.kdesikamos.ui.home.ActivityActivity
import com.example.kdesikamos.ui.home.HomeFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GroupFragment : Fragment() {

    companion object{
        lateinit var clickedGroup: GroupDTO
    }

    private var _binding: FragmentGroupBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var dbApi: DbApi

    private val baseUrl = "http://10.0.2.2:8000/"

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val groupViewModel =
                ViewModelProvider(this).get(GroupViewModel::class.java)

        _binding = FragmentGroupBinding.inflate(inflater, container, false)
        val root: View = binding.root

        getGroups()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getGroups(){
        buildRetrofit()
        val call = dbApi.getGroups(LoginActivity.loggedUser._id)

        call.enqueue(object: Callback<List<GroupDTO>> {

            override fun onResponse(call: Call<List<GroupDTO>>, response: Response<List<GroupDTO>>) {
                if (response.code() != 200){
                    return
                }
                val groups: List<GroupDTO>? = response.body()
                groups?.let {
                    setRecycler(groups)
                }
            }

            override fun onFailure(call: Call<List<GroupDTO>>, t: Throwable) {
                t.message?.let { Log.d("neide aktivita", it) }
            }

        })
    }

    fun setRecycler(list:List<GroupDTO>){
        val layoutManager = LinearLayoutManager(this.context)
        binding.rvGroup.layoutManager = layoutManager

        var adapter = RecyclerViewGroup(this.context, list)

        val clickListener : RecyclerViewGroup.ItemClickListener = object: RecyclerViewGroup.ItemClickListener {
            override fun onItemClick(view: View?, position: Int) {
                clickedGroup = list.get(position)
                val intent = Intent(context, GroupActivity::class.java)
                startActivity(intent)
            }

        }

        adapter.setClickListener(clickListener)
        binding.rvGroup.adapter = adapter
    }

    fun buildRetrofit() {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        dbApi = retrofit.create(DbApi::class.java)
    }
}