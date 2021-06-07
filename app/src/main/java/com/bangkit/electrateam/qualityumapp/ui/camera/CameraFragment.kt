package com.bangkit.electrateam.qualityumapp.ui.camera

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bangkit.electrateam.qualityumapp.databinding.FragmentCameraBinding

class CameraFragment : Fragment() {

    private lateinit var viewModel: CameraViewModel
    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(CameraViewModel::class.java)
        _binding = FragmentCameraBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if (activity != null) {
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}