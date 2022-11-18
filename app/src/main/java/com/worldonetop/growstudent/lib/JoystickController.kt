package com.worldonetop.growstudent.lib

import android.view.View
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt

class JoystickController(private val density:Float) {
    private val moveLayoutSize: Int
    private val moveInnerSize: Int
    private val maxLen:Float

    init {
        moveLayoutSize = (density*110).roundToInt()
        moveInnerSize = (density*40).roundToInt()
        maxLen = moveLayoutSize/2f -moveInnerSize/2f
    }

    fun move(v:View, x:Float, y:Float):Array<Float>{ // x, y 각 얼마나 차이나는지 (0.00 까지)
        val len = sqrt((x - moveLayoutSize/2f).pow(2) + (y - moveLayoutSize/2f).pow(2))
        if(len <= maxLen ){
            v.x = x - moveInnerSize/2f
            v.y = y - moveInnerSize/2f
        }else{
            v.x = (x - moveLayoutSize/2f) * maxLen / len + moveLayoutSize/2f - moveInnerSize/2f
            v.y = (y - moveLayoutSize/2f) * maxLen / len + moveLayoutSize/2f - moveInnerSize/2f
        }

        return arrayOf(
            (((v.x - maxLen)/maxLen)*100f).roundToInt()/100f,
            (((v.y - maxLen)/maxLen)*100f).roundToInt()/100f
        )
    }
    fun moveCenter(v:View){
        v.x = maxLen
        v.y = maxLen
    }
}