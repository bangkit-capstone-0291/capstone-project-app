package com.bangkit.electrateam.qualityumapp.ui.camera

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bangkit.electrateam.qualityumapp.databinding.FragmentCameraBinding
import com.bangkit.electrateam.qualityumapp.ui.profile.ProfileViewModel

class CameraFragment : Fragment() {

    companion object {
        fun newInstance() = CameraFragment()
    }

    private lateinit var binding: FragmentCameraBinding
    private lateinit var viewModel: CameraViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(CameraViewModel::class.java)
        binding = FragmentCameraBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}