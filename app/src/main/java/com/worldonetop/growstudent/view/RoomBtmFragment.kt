package com.worldonetop.growstudent.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.worldonetop.growstudent.databinding.FragmentRoomBtmBinding

class RoomBtmFragment : Fragment() {
    private var _binding: FragmentRoomBtmBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoomBtmBinding.inflate(layoutInflater,container, false)
        return binding.root
    }
}

class BottomAdapter : RecyclerView.Adapter<BottomAdapter.BottomVH>(){
    inner class BottomVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottomVH {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: BottomVH, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

}