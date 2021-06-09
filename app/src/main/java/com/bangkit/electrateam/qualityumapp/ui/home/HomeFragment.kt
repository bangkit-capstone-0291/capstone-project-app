package com.bangkit.electrateam.qualityumapp.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bangkit.electrateam.qualityumapp.databinding.FragmentHomeBinding
import com.bangkit.electrateam.qualityumapp.viewmodel.ViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")

        if (activity != null) {
            loadProfile()

            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

            binding.cvEsmartIkm.setOnClickListener {
                val urlIntent = Intent(Intent.ACTION_VIEW)
                urlIntent.data = Uri.parse(ESMART_IKM_URL)
                requireActivity().startActivity(urlIntent)
            }

            binding.cvSmartSentra.setOnClickListener {
                val urlIntent = Intent(Intent.ACTION_VIEW)
                urlIntent.data = Uri.parse(SMART_SENTRA_URL)
                requireActivity().startActivity(urlIntent)
            }

            binding.cvCategoryFruit.setOnClickListener {
                val action = HomeFragmentDirections.actionNavigationHomeToNavigationStock(10)
                action.let {
                    findNavController().navigate(it)
                }
            }

            binding.cvCategoryVegetable.setOnClickListener {
                val action = HomeFragmentDirections.actionNavigationHomeToNavigationStock(20)
                action.let {
                    findNavController().navigate(it)
                }
            }

            binding.cvCategoryOther.setOnClickListener {
                val action = HomeFragmentDirections.actionNavigationHomeToNavigationStock(30)
                action.let {
                    findNavController().navigate(it)
                }
            }
        }
    }

    private fun loadProfile() {
        val user = auth.currentUser
        val userReference = databaseReference?.child(user?.uid!!)

        userReference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.tvUserName.text = snapshot.child("fullname").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ESMART_IKM_URL = "http://esmartikm.id/"
        private const val SMART_SENTRA_URL = "https://smartsentra.kemenperin.go.id/"
    }
}