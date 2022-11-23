package com.worldonetop.growstudent.model

import android.graphics.drawable.Drawable
import java.io.Serializable

data class Item (
    val id:Int,
    val name:String,
    val content:String, // 설명
    val effect:Float,
    var num:Int, // 개수
    var price:Int = 0,
    var priceStr:String = "0",
    val img:Drawable? = null,
    var apply:Boolean = false,
): Serializable
/** id
 * 신발류 - 1,2,3,4
 * 휴식 - 100
 * 학업 - 200 201 202
 * 아이템 - 300 301 302 303
 * 기숙사 - 400~407
 *
 * effect -> viewmodel
 * */