package com.worldonetop.growstudent.view.bottom

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.worldonetop.growstudent.databinding.FragmentTabOneBinding
import com.worldonetop.growstudent.databinding.RowTabOneBinding
import com.worldonetop.growstudent.model.MapBuilding
import com.worldonetop.growstudent.util.MyConverter
import com.worldonetop.growstudent.util.MyData
import com.worldonetop.growstudent.view.ImageActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TabOneFragment: Fragment() {
    private var _binding: FragmentTabOneBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: TabOneAdapter
    @Inject lateinit var myData: MyData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabOneBinding.inflate(layoutInflater,container, false)
        initData()
        return binding.root
    }
    private fun initData(){
        adapter = TabOneAdapter(myData.mapData.toTypedArray(), null)
        binding.mapRv.adapter = adapter

        binding.mapCategory.adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_dropdown_item, arrayOf("카테고리") + MyConverter.roomCategory)
        binding.mapCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                adapter.setCategory(position)
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        binding.mapViewBtn.setOnClickListener{
            startActivity(Intent(activity, ImageActivity::class.java))
        }
    }
}
class TabOneAdapter(private val data:Array<MapBuilding>, private val listener:((MapBuilding)->Unit)?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val category:ArrayList<MapBuilding> = ArrayList()
    init{
        category.addAll(data)
    }

    fun setCategory(category: Int) {
        notifyItemRangeRemoved(0, this.category.size)

        this.category.clear()
        this.category.addAll(data)

        if(category != 0) {
            this.category.removeIf {
                it.category != category
            }
        }

        notifyItemRangeInserted(0, this.category.size)
    }
    fun getMapBuilding(index:Int):MapBuilding? = category.getOrNull(index)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TabOneVH(
            RowTabOneBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            ),listener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as TabOneVH).bind(category[position])
    }
    override fun getItemCount(): Int {
        return category.size
    }
}
class TabOneVH(private val binding: RowTabOneBinding, private val listener:((MapBuilding)->Unit)?) : RecyclerView.ViewHolder(binding.root){
    fun bind(map: MapBuilding){
        binding.title.text = "${map.num} - ${map.name}"
        binding.content.text = map.content
        binding.img.setImageDrawable(map.drawable)
        listener?.let {
            binding.root.setOnClickListener{ it(map)}
        }
    }
}
/** category
 * 1 : 학업
 * 2 : 알바
 * 3 : 운동
 * 4 : 휴식
 * 5 : 상점
 * 6 : 기타
 * */