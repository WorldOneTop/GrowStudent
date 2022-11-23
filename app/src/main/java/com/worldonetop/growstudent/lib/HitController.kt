package com.worldonetop.growstudent.lib

import android.util.Log
import com.worldonetop.growstudent.model.HitBox
import com.worldonetop.growstudent.util.MySize
import kotlin.math.abs
import kotlin.math.min


// View 좌표계로 계산
class HitController(val boxs: Array<HitBox>) {
    lateinit var userBox: HitBox
    var result = mutableListOf(false,false,false,false) //L, T, R, B 순으로 움직일수 없으면 1
    // null 아니면 방 리스트
    fun move(x:Float,y:Float):Array<Int>? {
        for(i in 0 until 4)
            result[i] = false


        boxMove(x,y)
        // 맵 판정
        // 하체만 히트박스해놔서 벽에는 따로 해야함
        if(userBox.left+x < MySize.userWith/3 ) {
            result[0] = true
        }
        if(userBox.top+y < MySize.userHeight*7/10) {
            result[1] = true
        }
        if(userBox.right+x > MySize.mapWith-MySize.userWith/3) {
            result[2] = true
        }
        if(userBox.bottom+y > MySize.mapHeight ) {
            result[3] = true
        }


        for(box in boxs){
            isHit(box)?.let {
                boxMove(-x,-y)
                return it
            }
        }

        boxMove(-x,-y)
        return null
    }
    fun boxMove(x:Float, y:Float){
        userBox.left += x
        userBox.top += y
        userBox.right += x
        userBox.bottom += y
    }


    // 0은 충돌x, 양수는 맵번호
    // -1은 가로 + 아래로, -2는 가로 + 위로만 , -3는 세로 + 왼쪽으로, -4는 세로 + 오른쪽으로만 움직이세요, -5는 두개일경우
    // -1은 위로못감, -2는 아래로못감, -3은 오른쪽못감, -4는 왼쪽못감
    private fun isHit(box: HitBox):Array<Int>? {
        if (userBox.left <= box.right && userBox.right >= box.left) {
            if (userBox.top <= box.bottom && userBox.bottom >= box.top) { // 일단 충돌
                if (
                    min(abs(userBox.left - box.right), abs(userBox.right - box.left))
                    > min(abs(userBox.top - box.bottom), abs(userBox.bottom - box.top))
                ) {// 가로가 더 넓게 부딪힌 상황 -> 가로로만
                    if(box.top < userBox.top&&userBox.top < box.bottom)
                        result[1] = true
                    else if(box.top < userBox.bottom&&userBox.bottom < box.bottom)
                        result[3] = true

                } else{ // 세로가 더 넓게 부딪힌 상황
                    if(box.left < userBox.left&&userBox.left < box.right)
                        result[0] = true
                    else if(box.left < userBox.right&&userBox.right < box.right)
                        result[2] = true
                }

                if (box.room != null)
                    return box.room
            }
        }
        return null
    }



}