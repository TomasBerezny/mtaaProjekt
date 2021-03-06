package com.example.kdesikamos.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.kdesikamos.databinding.FragmentProfileBinding
import com.example.kdesikamos.ui.auth.LoginActivity
import com.example.kdesikamos.ui.auth.RegisterActivity

class ProfileFragment : Fragment() {

    private val baseUrl = "http://10.0.2.2:8000/"

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val user = LoginActivity.loggedUser
        val profileViewModel =
                ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setDetails()
        binding.btnProfileSettings.setOnClickListener {
            val intent = Intent(context, SettingsActivity::class.java)
            startActivity(intent)
        }

        binding.callCenterBtn.setOnClickListener{
            val intent = Intent(context, CallActivity::class.java)
            intent.putExtra("username",user.username)
            startActivity(intent)
        }





        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setDetails(){
        val user = LoginActivity.loggedUser

        binding.tvProfileUsername.text = user.username
        binding.tvProfileFirstName.text = user.first_name
        binding.tvProfileLastName.text = user.last_name
        binding.tvProfilePhoneNumber.text = user.phone_number
        binding.tvProfileBio.text = user.bio
        if (user.profile_photo_path.isNotBlank()){
            var profile_path = baseUrl + user.profile_photo_path
            profile_path = profile_path.replace("\\","/")
            Glide.with(requireContext()).load(profile_path).into(binding.imageView2)
        }
    }
}