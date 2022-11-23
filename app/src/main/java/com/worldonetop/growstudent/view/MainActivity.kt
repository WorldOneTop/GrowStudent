package com.worldonetop.growstudent.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.worldonetop.growstudent.R
import com.worldonetop.growstudent.databinding.ActivityMainBinding
import com.worldonetop.growstudent.lib.CanvasController
import com.worldonetop.growstudent.lib.HitController
import com.worldonetop.growstudent.lib.JoystickController
import com.worldonetop.growstudent.model.*
import com.worldonetop.growstudent.source.Local
import com.worldonetop.growstudent.source.Remote
import com.worldonetop.growstudent.util.MyConverter
import com.worldonetop.growstudent.util.MyData
import com.worldonetop.growstudent.util.MySize
import com.worldonetop.growstudent.view.bottom.MapBtmFragment
import com.worldonetop.growstudent.view.bottom.TabOneAdapter
import com.worldonetop.growstudent.view.dialog.*
import com.worldonetop.growstudent.vm.MainViewModel
import com.worldonetop.growstudent.vm.MainViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Thread.sleep
import javax.inject.Inject
import kotlin.math.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    @Inject lateinit var hitCtl:HitController
    @Inject lateinit var mySize:MySize
    @Inject lateinit var myData: MyData
    @Inject lateinit var loadingDialog: LoadingDialog
    @Inject lateinit var popupDialog: PopupDialog
    @Inject lateinit var roomDialog: RoomDialog
    @Inject lateinit var remote: Remote
    lateinit var userLoc:UserLocate

    lateinit var joystick:JoystickController
    lateinit var user:User
    lateinit var items:ArrayList<Item>
    lateinit var mainVM:MainViewModel
    lateinit var canvasCtl:CanvasController

    var flagRoom:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadingDialog.dialog.show() // surfaceView가 끝난 뒤 dismiss
        initViewData()
        initView()

    }
    private fun initViewData(){
        user = intent.getSerializableExtra("user") as User
        if(user.x == 0f && user.y == 0f){
            user.x = 1800*mySize.density
            user.y = 2080*mySize.density
        }



        items = MyConverter.strToItemsList(intent.getStringExtra("items") ?: "", applicationContext)
        // TODO 삭제
//        items[8].num = 1
//        items[9].num = 1
//        items[10].num = 2
//        items[11].num = 3
        items = MyConverter.getAllItem(applicationContext)


        binding.lifecycleOwner = this
        mainVM = ViewModelProvider(this, MainViewModelFactory(user, items))[MainViewModel::class.java]
        userLoc = UserLocate(user.x, user.y)


        hitCtl.userBox = HitBox(
            user.x+mySize.userWith/3,user.y+mySize.userHeight*4/5,
            user.x+mySize.userWith*2/3, user.y+mySize.userHeight
        )
        // 하체만 히트박스로
        // -> 왼쪽여백은 전체의 1/3 가운데 1/3 오른쪽 1/3
        // 높이는 전체의 1/5



        joystick = JoystickController(resources.displayMetrics.density)
        binding.mapView.holder.addCallback(mapSurfaceListener)


        val speedItem = items.find{ it.id<100 && it.apply}?.id ?: 0

        canvasCtl = CanvasController(binding.mapView, binding.userView, userLoc, hitCtl, speedItem){
            roomInListener(it)
        }

        mainVM.speed.observe(this){
            canvasCtl.speed = it
        }
        mainVM.items.observe(this){
            canvasCtl.speedItem = items.find{ it.id<100 && it.apply}?.id ?: 0
            speedItemToImg(items.find{ it.id<100 && it.apply}?.id)
        }
    }

    private fun initView(){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.bottomFrg, MapBtmFragment())
            .commit()

        binding.vm = mainVM
        binding.id.text = user.id

        BottomSheetBehavior.from(binding.bottomFrg).state = BottomSheetBehavior.STATE_HIDDEN
        BottomSheetBehavior.from(binding.bottomFrg).skipCollapsed = true
        binding.upArrow.setOnClickListener{BottomSheetBehavior.from(binding.bottomFrg).state = BottomSheetBehavior.STATE_EXPANDED}

        speedItemToImg(items.find{ it.id<100 && it.apply}?.id)
    }

    override fun onPause() {
        binding.mapView.isRunning = false

        mainVM.getUser().let {
            it.x = userLoc.x
            it.y = userLoc.y
            Local(applicationContext).setUserData(it)
        }
        mainVM.items.value?.let {
            Local(applicationContext).setItemData(
                MyConverter.itemsListToStr(it)
            )
        }
        super.onPause()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initMapListener(){
        binding.bottomFrg.setOnTouchListener{_,_ ->
            true
        }
        binding.viewRootLayout.setOnTouchListener{ v,e->
            if(e.action == MotionEvent.ACTION_UP){
                joystick.moveCenter(binding.moveInner)
                canvasCtl.userMoveStop()
                binding.moveLayout.x = mySize.width - binding.moveLayout.width*1f - 24*mySize.density
                binding.moveLayout.y = mySize.mapViewHeight - binding.moveLayout.height*1f - 24*mySize.density

            }else if(e.action == MotionEvent.ACTION_DOWN){
                binding.moveLayout.x = e.x - binding.moveLayout.width/2f
                binding.moveLayout.y = e.y - binding.moveLayout.height/2f

                canvasCtl.userMoveStart()
            }else{
                val x = e.x - binding.moveLayout.x
                val y = e.y - binding.moveLayout.y
                val innerXY = joystick.move(binding.moveInner, x, y)
                binding.mapView.joystickX = innerXY[0]
                binding.mapView.joystickY = innerXY[1]

            }

            true
        }
    }
    private fun speedItemToImg(id:Int?){
        when(id){
            1 -> binding.userView.setBackgroundResource(R.drawable.user1_main)
            2 -> binding.userView.setBackgroundResource(R.drawable.user2_main)
            3 -> binding.userView.setBackgroundResource(R.drawable.user3_main)
            4 -> binding.userView.setBackgroundResource(R.drawable.user4_main)
            else -> binding.userView.setBackgroundResource(R.drawable.user_main)
        }

    }
    private fun roomInListener(roomList:Array<Int>){
        if(flagRoom != 0 && roomList[0] == flagRoom)
            return

        canvasCtl.userMoveStop()

        val putData = ArrayList<MapBuilding>()
        for(roomNum in roomList){
            putData.add(myData.mapData[roomNum-1])
        }
        val roomListAdapter = TabOneAdapter(putData.toTypedArray()){
            roomDialog.dialog.dismiss()
            binding.moveLayout.visibility = View.GONE


            val bundle = Bundle()
            bundle.putInt("id", it.num)
            bundle.putInt("category", it.category)

            val fragment = RoomFragment()
            fragment.arguments = bundle

            supportFragmentManager
                .beginTransaction()
                .add(R.id.mainFrg, fragment)
                .addToBackStack(null)
                .commit()
        }

        CoroutineScope(Dispatchers.Main).launch {
            roomDialog.setRv(roomListAdapter){
                flagRoom = roomListAdapter.getMapBuilding(0)!!.num
                CoroutineScope(Dispatchers.IO).launch {
                    sleep(1500)
                    if(flagRoom != 0)
                        flagRoom = 0
                }
            }.show()
        }
    }



    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount == 0){
            canvasCtl.userMoveStop()
            binding.moveLayout.x = mySize.width - binding.moveLayout.width*1f - 24*mySize.density
            binding.moveLayout.y = mySize.mapViewHeight - binding.moveLayout.height*1f - 24*mySize.density
            popupDialog.setInfoLayout(
                ContextCompat.getDrawable(applicationContext, R.drawable.ic_warning),
                "종료하시겠습니까?",
                "서버에 저장하기",
                "종료하기", { saveRemote() }, {popupDialog.dialog.dismiss()
                    this@MainActivity.finish() }
            ).show()
        }else{
            binding.moveLayout.visibility = View.VISIBLE
            supportFragmentManager.popBackStack()
        }
    }
    private fun saveRemote(){
        if(Local(applicationContext).isLogin()){
            Toast.makeText(applicationContext,"로그인이 필요합니다.", Toast.LENGTH_SHORT).show()
            return
        }
        loadingDialog.dialog.show()
        val user = mainVM.getUser().apply {
            x = userLoc.x
            y = userLoc.y
        }
        val response = remote.save(
            user.id,user.x,user.y,user.speed,user.money,user.moneyStr,user.hp,user.hpStr,user.int,user.intStr,user.duty,user.dutyStr,
            MyConverter.itemsListToStr(mainVM.items.value!!)
        )

        response.enqueue(object : Callback<Resp<Unit>> {
            override fun onResponse(call: Call<Resp<Unit>>, response: Response<Resp<Unit>>) {
                if(response.code() == 200){
                    response.body()?.let {
                        if(it.code == 0)
                            Toast.makeText(applicationContext,"서버에 저장이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                        else
                            Toast.makeText(applicationContext,getString(R.string.error_unknown), Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(applicationContext,getString(R.string.error_unknown), Toast.LENGTH_SHORT).show()
                }
                loadingDialog.dialog.dismiss()
            }

            override fun onFailure(call: Call<Resp<Unit>>, t: Throwable) {
                loadingDialog.dialog.dismiss()
                Toast.makeText(applicationContext,getString(R.string.error_failUre), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private val mapSurfaceListener: SurfaceHolder.Callback = object : SurfaceHolder.Callback {
        override fun surfaceDestroyed(holder: SurfaceHolder) {
        }
        override fun surfaceCreated(holder: SurfaceHolder) {
            binding.mapView.initDraw(userLoc.x, userLoc.y, canvasCtl)
            initMapListener()
            loadingDialog.dialog.dismiss()
        }
        override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        }
    }

}