package com.worldonetop.growstudent.lib

import android.graphics.drawable.AnimationDrawable
import android.util.Log
import android.widget.ImageView
import com.worldonetop.growstudent.R
import com.worldonetop.growstudent.model.UserLocate
import com.worldonetop.growstudent.util.MySize
import com.worldonetop.growstudent.view.custom.MapView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.*

class CanvasController(
    val mapView: MapView,
    val userView:ImageView,
    val userLoc:UserLocate,
    val hitCtl: HitController,
    var speedItem:Int,
    val roomListener: (Array<Int>) -> Unit,
) {
    var speed:Float = 0f

    // 유저 첫 위치 잡기
    init {
        if(userLoc.x < MySize.width/2 - MySize.userWith/2)  // 왼쪽이 맵끝일때
            userView.x = userLoc.x
        else if(MySize.mapWith - MySize.width/2 + MySize.userWith/2 < userLoc.x)  // 오른쪽이 맵 끝일때
            userView.x = userLoc.x - (MySize.mapWith - MySize.width)
        else
            userView.x = MySize.width/2 - MySize.userWith/2

        if(userLoc.y < MySize.mapViewHeight/2 - MySize.userHeight/2) // 위가 맵 끝일때
            userView.y = userLoc.y
        else if(MySize.mapHeight - MySize.mapViewHeight/2 + MySize.userHeight/2 < userLoc.y) // 아래가 맵 끝일때
            userView.y = userLoc.y - (MySize.mapHeight - MySize.mapViewHeight)
        else
            userView.y = MySize.mapViewHeight/2 - MySize.userHeight/2

    }
    // 주체는 랜더링쓰레드가 돌려야함
    fun drawMove(moveX:Float, moveY:Float){
        var x = moveX*speed
        var y = moveY*speed

        /** Hit 판정 */
        hitCtl.move(x,y)?.let { // 방 진입시
            roomListener(it)
//            userLoc.x += -x
//            userLoc.y += -y
//            mapView.moveX = -x
//            mapView.moveY = -y
//            hitCtl.boxMove(-x,-y)
//            return
        }

        if(moveX<0 && hitCtl.result[0]) x = 0f
        else if(moveX>0 && hitCtl.result[2]) x = 0f

        if(moveY<0 && hitCtl.result[1]) y = 0f
        else if(moveY>0 && hitCtl.result[3]) y = 0f

        /** 맵 이랑 유저 움직이게 */
        if(userLoc.x  < MySize.width/2 - MySize.userWith/2) { // 왼쪽이 맵끝일때
            mapView.moveX = 0f
            userView.x += x
        }else if(MySize.mapWith - MySize.width/2 - MySize.userWith/2 < userLoc.x ) { // 오른쪽이 맵 끝일때
            mapView.moveX =  0f
            userView.x += x
        }else{
            mapView.moveX = -x
        }

        if(userLoc.y < MySize.mapViewHeight/2 - MySize.userHeight/2){ // 위가 맵 끝일때
            mapView.moveY = 0f
            userView.y += y
        }else if(MySize.mapHeight - MySize.mapViewHeight/2 - MySize.userHeight/2 < userLoc.y){ // 아래가 맵 끝일때
            mapView.moveY = 0f
            userView.y += y
        }else{
            mapView.moveY = -y
        }

        /** 유저 애니메이션 */
        if(moveX > 0)
            userView.scaleX = 1f
        else
            userView.scaleX = -1f

        /** 데이터설정*/
        userLoc.x += x
        userLoc.y += y
        hitCtl.boxMove(x,y)

    }

    fun userMoveStart(){
        mapView.isRunning = true
        mapView.RenderingThread().start()

        CoroutineScope(Dispatchers.Main).launch {
            when (speedItem) {
                0 -> userView.setBackgroundResource(R.drawable.user_ani_right)
                1 -> userView.setBackgroundResource(R.drawable.user1_ani_right)
                2 -> userView.setBackgroundResource(R.drawable.user2_ani_right)
                3 -> userView.setBackgroundResource(R.drawable.user3_ani_right)
                4 -> userView.setBackgroundResource(R.drawable.user4_ani_right)
            }

            (userView.background as? AnimationDrawable)?.start()
        }
    }
    fun userMoveStop(){
        mapView.isRunning = false
        CoroutineScope(Dispatchers.Main).launch {
            when(speedItem){
                0 ->userView.setBackgroundResource(R.drawable.user_main)
                1 ->userView.setBackgroundResource(R.drawable.user1_main)
                2 ->userView.setBackgroundResource(R.drawable.user2_main)
                3 ->userView.setBackgroundResource(R.drawable.user3_main)
                4 -> userView.setBackgroundResource(R.drawable.user4_main)
            }

            (userView.background as? AnimationDrawable)?.stop()
        }
    }

}