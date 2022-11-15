package com.worldonetop.growstudent.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.worldonetop.growstudent.R
import com.worldonetop.growstudent.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}