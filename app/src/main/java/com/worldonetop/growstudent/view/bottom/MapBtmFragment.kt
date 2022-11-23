package com.worldonetop.growstudent.view.bottom

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.worldonetop.growstudent.R
import com.worldonetop.growstudent.databinding.FragmentMapBtmBinding
import com.worldonetop.growstudent.util.MyData
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MapBtmFragment : Fragment() {
    private var _binding: FragmentMapBtmBinding? = null
    private val binding get() = _binding!!
    private var currentTab = 0
    private lateinit var tabViews:Array<ImageView>
    private lateinit var tabFragments:Array<Fragment>
//    @Inject lateinit var myData:MyData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBtmBinding.inflate(layoutInflater,container, false)
        initData()
        initViews()
        initListener()

        return binding.root
    }
    private fun initData(){
        tabViews = arrayOf(binding.tab1,binding.tab2,binding.tab3,binding.tab4)
        tabFragments = arrayOf(
            TabOneFragment(),
            TabTwoFragment(),
            TabThreeFragment(),
            TabFourFragment(),
        )

    }
    private fun initViews(){
//        childFragmentManager.beginTransaction()
//            .add(R.id.mapBtmPage,tabFragments[0])
//            .commit()
        changeTab(0)
    }
    private fun initListener(){
        for(i in tabViews.indices){
            tabViews[i].setOnClickListener{ changeTab(i) }
        }
    }

    private fun changeTab(nextTab: Int){
        tabViews[currentTab].animate().scaleX(1f).scaleY(1f)
        tabViews[currentTab].setBackgroundColor(Color.TRANSPARENT)
        tabViews[nextTab].z = 0f

        tabViews[nextTab].animate().scaleX(1.6f).scaleY(1.6f)
        activity?.getColor(R.color.primary_color)?.let { tabViews[nextTab].setBackgroundColor(it) }
        tabViews[nextTab].z = 5f


        childFragmentManager.beginTransaction()
            .replace(R.id.mapBtmPage,tabFragments[nextTab])
            .commit()

        currentTab = nextTab
    }


}
