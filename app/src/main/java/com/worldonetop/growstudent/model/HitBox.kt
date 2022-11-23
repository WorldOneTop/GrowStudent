package com.worldonetop.growstudent.model

import kotlin.math.abs

data class HitBox(
    var left: Float,
    var top: Float,
    var right: Float,
    var bottom: Float,
    var room:Array<Int>? = null //null 은 장애물, 나머지 리스트는 번호는 맵에 번호
)