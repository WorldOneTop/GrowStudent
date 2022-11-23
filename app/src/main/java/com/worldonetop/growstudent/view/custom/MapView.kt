package com.worldonetop.growstudent.view.custom

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceView
import android.widget.ImageView
import com.worldonetop.growstudent.R
import com.worldonetop.growstudent.lib.CanvasController
import com.worldonetop.growstudent.lib.HitController
import com.worldonetop.growstudent.model.User
import com.worldonetop.growstudent.model.UserLocate
import com.worldonetop.growstudent.util.MySize
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.*
import javax.inject.Inject


@AndroidEntryPoint
class MapView: SurfaceView {

    constructor(context: Context):super(context)
    constructor(context: Context, attrs: AttributeSet?):super(context,attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int):super(context,attrs,defStyleAttr)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int):super(context,attrs,defStyleAttr,defStyleRes)

    @Inject lateinit var mySize: MySize
    private val map:Bitmap
    private val mapMatrix:Matrix
    val rightMax:Float
    val bottomMax:Float
    var moveX = 0f
    var moveY = 0f
    var joystickX = 0f
    var joystickY = 0f
    var isRunning:Boolean = false
    lateinit var canvasCtl:CanvasController


    init{
        mapMatrix = Matrix()
        val tempMap = BitmapFactory.decodeResource(context.resources, R.drawable.bg_img_main)
        map = Bitmap.createScaledBitmap(tempMap,mySize.mapWith.roundToInt(),mySize.mapHeight.roundToInt(),true)
        rightMax = -mySize.mapWith + mySize.width*1f
        bottomMax = -mySize.mapHeight + mySize.mapViewHeight
    }

    fun initDraw(userX:Float, userY:Float, ctl:CanvasController){
        canvasCtl = ctl

        val initX = if(userX < mySize.width/2-mySize.userWith/2){ // 왼쪽
            0f
        }else if(mySize.mapWith-mySize.width/2+mySize.userWith/2 < userX){ // 오른쪽
            rightMax
        }else{
            -userX + mySize.width/2 - mySize.userWith/2
        }

        val initY = if(userY < mySize.mapViewHeight/2-mySize.userHeight/2){//위쪽
            0f
        }else if(mySize.mapHeight-mySize.mapViewHeight/2+mySize.userHeight/2 < userY){ // 아래쪽
            bottomMax
        }else{
            -userY + mySize.mapViewHeight/2-mySize.userHeight/2
        }

        mapMatrix.setTranslate(initX,initY)
        val canvas = holder.lockCanvas()
        canvas.drawBitmap(map,mapMatrix,null)
        holder.unlockCanvasAndPost(canvas)
    }
    inner class RenderingThread : Thread() {
        override fun run() {
                while(isRunning) {
                    canvasCtl.drawMove(joystickX,joystickY)
                    var canvas = holder.lockCanvas()

                    mapMatrix.postTranslate(moveX,moveY)
                    canvas.drawColor(Color.WHITE)
                    canvas.drawBitmap(map,mapMatrix,null)

                    holder.unlockCanvasAndPost(canvas)
            }
            joystickX = 0f
            joystickY = 0f

        }
    } // RenderingThread
}