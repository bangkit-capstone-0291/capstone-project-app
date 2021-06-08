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

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

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

        if (activity != null) {

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ESMART_IKM_URL = "http://esmartikm.id/"
        private const val SMART_SENTRA_URL = "https://smartsentra.kemenperin.go.id/"
    }
}