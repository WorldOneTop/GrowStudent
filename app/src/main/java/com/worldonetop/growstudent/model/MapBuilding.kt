package com.worldonetop.growstudent.model

import android.graphics.drawable.Drawable

data class MapBuilding (
    val num:Int, // == ID
    val name:String,
    val content:String,//설명
    val category:Int,
    val drawable: Drawable? = null
)
/** category
 * 1 : 학업
 * 2 : 알바
 * 3 : 운동
 * 4 : 휴식
 * 5 : 상점
 * 6 : 기타
 * */