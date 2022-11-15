package com.worldonetop.growstudent.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.worldonetop.growstudent.R
import com.worldonetop.growstudent.databinding.FragmentMapBinding
import com.worldonetop.growstudent.databinding.FragmentRoomBinding

class RoomFragment : Fragment() {
    private var _binding: FragmentRoomBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoomBinding.inflate(layoutInflater,container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}