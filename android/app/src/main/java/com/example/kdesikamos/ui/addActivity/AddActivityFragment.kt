package com.example.kdesikamos.ui.addActivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.kdesikamos.databinding.FragmentAddActivityBinding

class AddActivityFragment : Fragment() {

    private var _binding: FragmentAddActivityBinding? = null

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

        saveActivity()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun saveActivity(){

    }
}