package com.worldonetop.growstudent.view

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.worldonetop.growstudent.R
import com.worldonetop.growstudent.databinding.ActivityMainBinding
import com.worldonetop.growstudent.lib.JoystickController
import com.worldonetop.growstudent.view.bottom.MapBtmFragment
import com.worldonetop.growstudent.view.bottom.RoomBtmFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    companion object{
        val roomFragment:RoomFragment by lazy {
            RoomFragment()
        }
        val mapBtmFragment: MapBtmFragment by lazy {
            MapBtmFragment()
        }
        val roomBtmFragment: RoomBtmFragment by lazy {
            RoomBtmFragment()
        }
    }
    lateinit var joystick:JoystickController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewData()
        initView()
    }
    private fun initViewData(){
        joystick = JoystickController(resources.displayMetrics.density)
        binding.mapView.holder.addCallback(mapSurfaceListener)
    }

    private fun initView(){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.bottomFrg, roomBtmFragment)
            .commit()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.isRunning = false
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initMapListener(){
        binding.moveLayout.setOnTouchListener{ v, e->
            if(e.action == MotionEvent.ACTION_UP){
                joystick.moveCenter(binding.moveInner)
                binding.mapView.isRunning = false
                (binding.userView.background as AnimationDrawable).stop()
                binding.userView.setBackgroundResource(R.drawable.user_main)
            }else{
                val moveArray = joystick.move(binding.moveInner, e.x, e.y)
                binding.mapView.moveX = moveArray[0]*-5
                binding.mapView.moveY = moveArray[1]*-5

                if(moveArray[0] > 0)
                    binding.userView.scaleX = 1f
                else
                    binding.userView.scaleX = -1f

                if(e.action == MotionEvent.ACTION_DOWN){
                    binding.mapView.isRunning = true
                    binding.mapView.RenderingThread().start()

                    binding.userView.setBackgroundResource(R.drawable.user_ani_right)
                    (binding.userView.background as AnimationDrawable).start()
                }
            }
            true
        }
    }
    private fun moveUser(x:Float, y:Float, isRight:Boolean){
        if(x<0){
//            binding.userView.scaleX = -1f   setBackgroundResource(R.drawable.user_ani_left)
//            (binding.userView.background as AnimationDrawable).start()
//        }else{
//            binding.userView.setBackgroundResource(R.drawable.user_ani_right)
//            (binding.userView.background as AnimationDrawable).start()
        }
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

    private val mapSurfaceListener: SurfaceHolder.Callback = object : SurfaceHolder.Callback {
        override fun surfaceDestroyed(holder: SurfaceHolder) {
        }
        override fun surfaceCreated(holder: SurfaceHolder) {
            initMapListener()
            binding.mapView.initDraw()
        }
        override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        }
    }


}