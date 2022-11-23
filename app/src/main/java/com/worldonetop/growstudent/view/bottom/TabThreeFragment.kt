package com.worldonetop.growstudent.view.bottom

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.worldonetop.growstudent.databinding.FragmentTabThreeBinding
import com.worldonetop.growstudent.databinding.RowTabThreeBinding
import com.worldonetop.growstudent.model.Item
import com.worldonetop.growstudent.view.dialog.PopupDialog
import com.worldonetop.growstudent.vm.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TabThreeFragment: Fragment() {
    private var _binding: FragmentTabThreeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: TabThreeAdapter
    private val mainVM: MainViewModel by activityViewModels()
    @Inject lateinit var popupDialog: PopupDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabThreeBinding.inflate(layoutInflater,container, false)
        initData()
        return binding.root
    }

    private fun initData(){
        val data = ArrayList<Item>()
        data.addAll(mainVM.items.value!!.filter { it.id<400 })

        adapter = TabThreeAdapter(data){ it ->
            popupDialog.setGadgetLayout(it){

                if(it.id >=300) {
                    it.num -= 1
                    mainVM.activeGadget(it)
                    if (it.num<=0)
                        popupDialog.dialog.dismiss()
                    adapter.changeItemNum(it)
                }else{
                    if(it.apply)
                        mainVM.passiveGadget(it)
                    else
                        mainVM.activeGadget(it)
                    popupDialog.dialog.dismiss()
                }
            }.show()
        }
        binding.gadgetRv.adapter = adapter
        binding.gadgetRv.layoutManager = GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)

        activity?.let {
            mainVM.items.observe(it, Observer { data ->
                adapter.changeItem(data)
            })
        }
    }
}
class TabThreeAdapter(val input:ArrayList<Item>,val listener: (Item) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val data = ArrayList<Item>()
    init {
        data.addAll(input)
    }

    fun changeItem(data: ArrayList<Item>){
        val prev = this.data.size
        this.data.clear()
        notifyItemRangeRemoved(0, prev)

        this.data.addAll(data)

        notifyItemRangeInserted(0, this.data.size)
    }
    fun changeItemNum(item:Item){
        data.indices.firstOrNull{ item.id == data[it].id }?.let {
            notifyItemChanged(it)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TabThreeVH(
            RowTabThreeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            ))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as TabThreeVH).bind(data[position],listener)
    }
    override fun getItemCount(): Int {
        return data.size
    }
}
class TabThreeVH(private val binding: RowTabThreeBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(item:Item, listener:((Item)->Unit)){
        binding.item.setImageDrawable(item.img)
        binding.num.text = item.num.toString()

        if(item.apply)
            binding.itemApply.visibility = View.VISIBLE
        else
            binding.itemApply.visibility = View.GONE

        if(item.id >= 300)
            binding.num.visibility = View.VISIBLE
        else
            binding.num.visibility = View.GONE


        if(item.id >=400){
            binding.root.visibility = View.GONE
        }else{
            binding.root.visibility = View.VISIBLE
        }
            binding.root.setOnClickListener{listener(item)}
    }
}