package com.worldonetop.growstudent.view.custom

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.SurfaceView
import com.worldonetop.growstudent.R
import com.worldonetop.growstudent.util.MySize
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MapView: SurfaceView {

    constructor(context: Context):super(context)
    constructor(context: Context, attrs: AttributeSet?):super(context,attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int):super(context,attrs,defStyleAttr)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int):super(context,attrs,defStyleAttr,defStyleRes)

    @Inject lateinit var mySize: MySize
    private val initX:Float
    private val initY:Float
    private val map:Bitmap
    private var mapMatrix = Matrix()
    var moveX = 0f
    var moveY = 0f
    var isRunning:Boolean = false

    init{
        initX = -1000*mySize.density + (mySize.width/2f)
        initY = -(mySize.mapHeight - mySize.height + mySize.bottomBarHeight + mySize.appBarHeight + mySize.statusBarHeight)
        val tempMap = BitmapFactory.decodeResource(context.resources, R.drawable.bg_img_main)
        map = Bitmap.createScaledBitmap(tempMap,mySize.mapWith.toInt(),mySize.mapHeight.toInt(),true)
        mapMatrix.setTranslate(initX,initY)
    }
    fun initDraw(){
        val canvas = holder.lockCanvas()
        canvas.drawBitmap(map,mapMatrix,null)
        holder.unlockCanvasAndPost(canvas)
    }
    inner class RenderingThread : Thread() {
        override fun run() {
                while(isRunning) {
                    var canvas = holder.lockCanvas()

                    mapMatrix.postTranslate(moveX,moveY)
                    canvas.drawBitmap(map,mapMatrix,null)

                    holder.unlockCanvasAndPost(canvas)
            }

        }
    } // RenderingThread
}