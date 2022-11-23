package com.worldonetop.growstudent.view

import android.R.attr.*
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.worldonetop.growstudent.R
import com.worldonetop.growstudent.databinding.ActivityImageBinding
import com.worldonetop.growstudent.util.MySize
import kotlin.math.max
import kotlin.math.min


class ImageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityImageBinding
    private lateinit var mScaleGestureDetector: ScaleGestureDetector
    private var mScaleFactor = 1.0f
    private var oldX:Float = 0f
    private var oldY:Float = 0f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageBinding.inflate(layoutInflater)
        binding.image.setImageDrawable(ContextCompat.getDrawable(this,
            intent.getIntExtra("id", R.drawable.bg_img_main)
        ))

        mScaleGestureDetector = ScaleGestureDetector(this, ScaleListener())

        setContentView(binding.root)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            if(it.action == MotionEvent.ACTION_DOWN){
                oldX = it.x
                oldY = it.y
            }else if(it.action == MotionEvent.ACTION_MOVE){
                binding.image.x += it.x - oldX
                binding.image.y += it.y - oldY
                oldX = it.x
                oldY = it.y

                if(binding.image.scaleX>1)
                    binding.image.x = min(MySize.width*(binding.image.scaleX-1),max(binding.image.x, -MySize.width*(binding.image.scaleX-1)))
                else
                    binding.image.x = 0f
                if(binding.image.scaleY>1)
                    binding.image.y = min(MySize.height*(binding.image.scaleY-1),max(binding.image.y, -MySize.height*(binding.image.scaleY-1)))
                else
                    binding.image.y = 0f
            }
        }
        mScaleGestureDetector.onTouchEvent(event)


        return true
    }

    inner class ScaleListener : SimpleOnScaleGestureListener() {
        override fun onScale(scaleGestureDetector: ScaleGestureDetector): Boolean {
            // ScaleGestureDetector에서 factor를 받아 변수로 선언한 factor에 넣고
            mScaleFactor *= scaleGestureDetector.scaleFactor

            // 최대 10배, 최소 10배 줌 한계 설정
            mScaleFactor = max(0.1f,min(mScaleFactor, 10.0f))

            // 이미지뷰 스케일에 적용
            binding.image.scaleX = mScaleFactor
            binding.image.scaleY = mScaleFactor

            // 이미지뷰 움직임
            return true
        }
    }
}