package com.worldonetop.growstudent.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.worldonetop.growstudent.R
import com.worldonetop.growstudent.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    companion object{
        val mapFragment:MapFragment by lazy {
            MapFragment()
        }
        val roomFragment:RoomFragment by lazy {
            RoomFragment()
        }
        val bottomFragment:BottomFragment by lazy {
            BottomFragment()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView(){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.bottomFrg, bottomFragment)
            .replace(R.id.mainFrg, mapFragment)
            .commit()
    }

    fun changeScreen(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .add(R.id.mainFrg, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount == 0){
            super.onBackPressed()
        }else{
            supportFragmentManager.popBackStack()
        }
    }
}