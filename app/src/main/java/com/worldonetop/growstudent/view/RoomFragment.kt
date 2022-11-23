package com.worldonetop.growstudent.view

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.worldonetop.growstudent.R
import com.worldonetop.growstudent.databinding.FragmentRoomBinding
import com.worldonetop.growstudent.databinding.RowRoomBinding
import com.worldonetop.growstudent.model.Item
import com.worldonetop.growstudent.model.RoomActive
import com.worldonetop.growstudent.util.MyConverter
import com.worldonetop.growstudent.view.dialog.PopupDialog
import com.worldonetop.growstudent.vm.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RoomFragment : Fragment() {
    private var _binding: FragmentRoomBinding? = null
    private val binding get() = _binding!!

    private var roomId:Int = 0
    private var category:Int = 0
    private lateinit var adapter:RoomAdapter
    private val mainVM: MainViewModel by activityViewModels()

    private lateinit var roomActive: Array<RoomActive>
    private var lockList: ArrayList<Boolean>? = null
    private var checkNum:Int=-1
    private lateinit var ani:Animation
    @Inject lateinit var popupDialog: PopupDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            roomId = it.getInt("id")
            category = it.getInt("category")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoomBinding.inflate(layoutInflater,container, false)
        initData()
        initView()
        initListener()

        return binding.root
    }
    private fun initData(){
        if(category==5){
            setShopping()
            return
        }
        roomActive = MyConverter.idToRoomActive(roomId)
        lockList = getLockList()


        roomActive.getOrNull(0)?.let {
            binding.ani.text = "+ ${it.benefit}"
        }

        adapter = RoomAdapter(roomActive,category,lockList){
                if (checkNum != it) {
                    adapter.changeCheck(checkNum, it)
                    checkNum = it
                    binding.ani.text = "+ ${roomActive[it].benefit}"
                }
        }

        binding.roomRv.adapter = adapter
    }
    private fun setShopping(){
        var getAllItem = MyConverter.getAllItem(requireContext())
        for(haveItem in mainVM.items.value!!)
            getAllItem.find { haveItem.id == it.id && !(id in 300..399) }?.let {
                it.num = haveItem.num
            }

        binding.roomRv.adapter = ShoppingAdapter(getAllItem){
            if((it.id in 300..399) || mainVM.items.value!!.getOrNull(it.id) == null){
                popupDialog.setByGadgetLayout(it){ dialogItem ->
                    val minusResult =  MyConverter.calcValue(false,mainVM.money.value!!,mainVM.moneyStr.value!!,it.price,it.priceStr)
                    if(minusResult[0].toInt() < 0) // 가지고있는거보다 크다면
                        Toast.makeText(requireContext(), "자금이 부족합니다.", Toast.LENGTH_SHORT).show()
                    else{
                        mainVM.money.value = minusResult[0].toInt()
                        mainVM.moneyStr.value = minusResult[1]
                        val newItems= ArrayList<Item>()
                        if(dialogItem.id in 300..399){
                            it.num = mainVM.items.value?.find { vm -> vm.id != it.id }?.num ?: 0
                            it.num += 1
                        }else{
                            it.num += 1
                        }
                        newItems.addAll(mainVM.items.value!!.filter{ vm -> vm.id != it.id})
                        newItems.add(it)
                        mainVM.items.value = newItems
                    }
                    if(dialogItem.id !in 300..399){
                        (binding.roomRv.adapter as ShoppingAdapter).changeData()
                        popupDialog.dialog.dismiss()
                    }
                }.show()
            }
        }

    }

    private fun initView(){
        val bgId = MyConverter.idToRoomImg(roomId)
        if(bgId == -1) {
            binding.roomBg.setImageResource(R.drawable.bg_map1)
            binding.lockBuilding.visibility = View.VISIBLE
        }
        else
            binding.roomBg.setImageResource(bgId)
        ani  = AnimationUtils.loadAnimation(requireContext(), R.anim.touch_ani)

    }
    @SuppressLint("ClickableViewAccessibility")
    private fun initListener(){
        binding.forTouch.setOnTouchListener{ _, _ ->
            true
        }

        binding.roomBg.setOnTouchListener{v, e ->
            if(e.action == MotionEvent.ACTION_UP) {
                if (category != 5 && checkNum != -1) {
                    binding.ani.x = e.x
                    binding.ani.y = e.y
                    binding.ani.startAnimation(ani)
                    if (passableAndDoWork()) {
                        changeLockList()?.let {
                            lockList!![it] = false
                            adapter.openLock(it)
                        }
                    } else {
                        Toast.makeText(requireContext(), "체력이 부족합니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            true
        }

    }
    private fun passableAndDoWork():Boolean{
        val minusResult = if(category==4) // 휴식은 더하기만
            listOf("1","1")
        else if(category == 5) // 상점은 돈을 뺌
            MyConverter.calcValue(false,mainVM.money.value!!,mainVM.moneyStr.value!!,roomActive[checkNum].minusHp,roomActive[checkNum].minusHpStr)
        else
            MyConverter.calcValue(false,mainVM.hp.value!!,mainVM.hpStr.value!!,roomActive[checkNum].minusHp,roomActive[checkNum].minusHpStr)

        if(minusResult[0].toInt() < 0) // 가지고있는거보다 크다면
            return false

        else if(category ==5){ // 상점은 돈으로
            mainVM.money.value = minusResult[0].toInt()
            mainVM.moneyStr.value = minusResult[1]
            return true
        }else if(category < 4){ //휴식 빼고
            mainVM.hp.value = minusResult[0].toInt()
            mainVM.hpStr.value = minusResult[1]
        }

        when(category){ //카테고리별 더할거
            1->{
                val plusResult = MyConverter.calcValue(true, mainVM.int.value!!,mainVM.intStr.value!!,roomActive[checkNum].benefit,roomActive[checkNum].benefitStr)
                mainVM.int.value = plusResult[0].toInt()
                mainVM.intStr.value = plusResult[1]
            }
            2-> {
                val plusResult = MyConverter.calcValue(true, mainVM.money.value!!,mainVM.moneyStr.value!!,roomActive[checkNum].benefit,roomActive[checkNum].benefitStr)
                mainVM.money.value = plusResult[0].toInt()
                mainVM.moneyStr.value = plusResult[1]
            }
            3-> {
                val plusResult = MyConverter.calcValue(true, mainVM.hpMax.value!!,mainVM.hpMaxStr.value!!,roomActive[checkNum].benefit,roomActive[checkNum].benefitStr)
                mainVM.hpMax.value = plusResult[0].toInt()
                mainVM.hpMaxStr.value = plusResult[1]
            }
            4-> {
                val plusResult = MyConverter.calcValue(true, mainVM.hp.value!!,mainVM.hpStr.value!!,roomActive[checkNum].benefit,roomActive[checkNum].benefitStr)
                if(MyConverter.compareStr_leftBig(
                    mainVM.hpMax.value!!,  mainVM.hp.value!!, mainVM.hpStr.value!!, mainVM.hpMaxStr.value!!
                )){
                    mainVM.hp.value = plusResult[0].toInt()
                    mainVM.hpStr.value = plusResult[1]
                }
            }
        }
        return true
    }
    private fun getLockList():ArrayList<Boolean>?{
        var result = arrayListOf<Boolean>()
        if(category <= 2){
            for(r in roomActive){
                result.add(
                    MyConverter.compareStr_leftBig(
                        r.limit,mainVM.int.value!!, r.limitStr, mainVM.intStr.value!!
                    )
                )
            }
        }else if(category == 4){
            for(r in roomActive){
                result.add(
                    mainVM.items.value?.find { it.id == r.limit } == null // null이 아니면 키 있는거
                )
            }
        }else {
            return null
        }
        return result
    }
    private fun changeLockList():Int?{
        if(lockList == null || category==4)
            return null
        for(i in lockList!!.indices){
            if(lockList!![i]){
                if(!MyConverter.compareStr_leftBig(
                        roomActive[i].limit,mainVM.int.value!!, roomActive[i].limitStr, mainVM.intStr.value!!
                    )
                ) return i
            }
        }
        return null
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

//    data class RoomActive (
//        val name:String, // 해당 일 이름
//        val limit:Int, // 해당 조건시 가능
//        val limitStr:String, // 해당 조건시 가능
//        val benefit:Int, // 수행 시 얻는 재화 양
//        val benefitStr:String, // 수행 시 얻는 재화 양
//        val minusHp:Int, // 체력 깎이는 양
//        val minusHpStr:Int, // 체력 깎이는 양
//    )
/** category
 * 1 : 학업 - 지능이 있어야 열리고, 지능을 얻음, 체력 깎임- 지능+ 체력-
 * 2 : 알바 - 지능이 있어야 열리고, 돈을 얻음. 체력 깎임 - 돈+ 체력-
 * 3 : 운동 - 잠금없고, 체력 깎이고 최대 체력 얻음- 최대체력+ 체격-
 * 4 : 휴식 - 아이템이 있어야 열리고, 체력을 얻음  - 체력+ X
 * 5 : 상점 - 열리기 상관없고, 물건을 얻고, 돈을 깎음 - 돈-
 * 6 : 기타 - ㅁㄹ
 * */
class RoomAdapter(private val data:Array<RoomActive>,private val category: Int,val lockList:ArrayList<Boolean>?, private val listener:((Int)->Unit)?) : RecyclerView.Adapter<RoomVH>() {
    var checked:Int = -1


    fun openLock(position: Int){
        notifyItemChanged(position)
    }
    fun changeCheck(old:Int,new:Int){
        checked = new
        notifyItemChanged(old)
        notifyItemChanged(new)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomVH {
        return RoomVH(
            RowRoomBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            ),category,listener)
    }

    override fun onBindViewHolder(holder: RoomVH, position: Int) {
        holder.bind(data[position], lockList?.get(position) ?: false,checked == position)
    }
    override fun getItemCount(): Int {
        return data.size
    }
}
class RoomVH(private val binding: RowRoomBinding,val category:Int,
             private val listener:((Int)->Unit)?) : RecyclerView.ViewHolder(binding.root){


    fun bind(item: RoomActive, isLock:Boolean, isChecked:Boolean){
        binding.title.text = item.name

        // 잠금해재 여부
        if(isLock) {
            when (category) {
                1, 2 -> {
                    binding.lockLimit.text = "학업 ${item.limitStr} 이상 잠금 해제"
                    if(isLock)
                        binding.lockLayout.visibility = View.VISIBLE
                    else
                        binding.lockLayout.visibility = View.GONE
                }
                3, 5 -> {
                    binding.lockLayout.visibility = View.GONE
                }
                4 -> {
                    binding.lockLimit.text = "상점에서 구매 가능"
                    if(isLock)
                        binding.lockLayout.visibility = View.VISIBLE
                    else
                        binding.lockLayout.visibility = View.GONE

                }
            }
        }else{
            binding.lockLayout.visibility = View.GONE
        }
        // 얻는 재화 ( 선택 옆에 글자)
        when(category){
            1 ->
                binding.benefit.text = "학업+${item.benefitStr} 체력-${item.minusHpStr}"
            2->
                binding.benefit.text = "자금+${item.benefitStr} 체력-${item.minusHpStr}"
            3->
                binding.benefit.text = "최대 체력+${item.benefitStr} 체력-${item.minusHpStr}"
            4->
                binding.benefit.text = "체력+${item.benefitStr}"
            5 ->
                binding.benefit.text = "가격 : ${item.minusHpStr}"
        }

        if(isChecked){
            binding.btn.setImageResource(R.drawable.btn_check_y)
        }else{
            binding.btn.setImageResource(R.drawable.btn_check_n)
        }

        listener?.let {
            binding.btn.setOnClickListener{
                if(!isLock)
                    it(adapterPosition)
            }
        }
    }
}

class ShoppingAdapter(private val data:ArrayList<Item>, private val listener:((Item)->Unit)?) : RecyclerView.Adapter<ShoppingAdapter.ShoppingBinding>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingAdapter.ShoppingBinding {
        return ShoppingBinding(
            RowRoomBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            ),listener)
    }

    override fun onBindViewHolder(holder: ShoppingAdapter.ShoppingBinding, position: Int) {
        holder.bind(data[position])
    }
    override fun getItemCount(): Int {
        return data.size
    }
    fun changeData(){
        notifyDataSetChanged()
    }

    inner class ShoppingBinding(private val binding: RowRoomBinding, private val listener:((Item)->Unit)?) : RecyclerView.ViewHolder(binding.root){

    fun bind(item:Item){
        binding.lockLayout.visibility = View.GONE
        binding.title.text = item.name
        binding.benefit.text = "가격 : ${item.priceStr}"

        if(item.num >0){
            binding.btn.setImageResource(R.drawable.btn_check_y)
        }else{
            binding.btn.setImageResource(R.drawable.btn_check_n)
        }

        listener?.let {
            if(item.num ==0 || item.id in 300..399)
            binding.root.setOnClickListener{
                it(item)
            }
        }
    }
    }

}