package com.worldonetop.growstudent.view.bottom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.worldonetop.growstudent.databinding.FragmentTabTwoBinding
import com.worldonetop.growstudent.model.MapBuilding
import com.worldonetop.growstudent.vm.MainViewModel

class TabTwoFragment: Fragment() {
    private var _binding: FragmentTabTwoBinding? = null
    private val binding get() = _binding!!
    private val mainVM: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabTwoBinding.inflate(layoutInflater,container, false)
        initData()
        return binding.root
    }
    private fun initData(){
        binding.vm = mainVM
//        binding.hp.text = mainVM.hpStr.value.toString()
//        binding.intStr.text = mainVM.intStr.value.toString()
//        binding.money.text = mainVM.moneyStr.value.toString()
//        binding.speed.text = "이동속도 : "+mainVM.speed.value.toString()
    }
}
