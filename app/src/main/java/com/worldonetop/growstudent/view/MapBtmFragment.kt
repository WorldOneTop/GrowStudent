package com.worldonetop.growstudent.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.worldonetop.growstudent.databinding.FragmentMapBtmBinding

class MapBtmFragment : Fragment() {
    private var _binding: FragmentMapBtmBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMapBtmBinding.inflate(layoutInflater,container, false)
        return binding.root
    }
}