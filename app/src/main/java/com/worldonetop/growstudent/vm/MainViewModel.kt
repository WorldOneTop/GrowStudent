package com.worldonetop.growstudent.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.gson.annotations.SerializedName
import com.worldonetop.growstudent.model.Item
import com.worldonetop.growstudent.model.User
import com.worldonetop.growstudent.util.DefaultUser
import com.worldonetop.growstudent.util.MyConverter

class MainViewModel(private val user:User, private val i:ArrayList<Item>):ViewModel() {


    val money = MutableLiveData(user.money) // 돈
    val moneyStr = MutableLiveData(user.moneyStr) // 돈
    val speed = MutableLiveData(user.speed) // 이속
    val hp = MutableLiveData(user.hp) // 체력
    val hpStr = MutableLiveData(user.hpStr) // 나타내는 값  ex: 421K, 12M
    val hpMax = MutableLiveData(user.hpMax) // 체력
    val hpMaxStr = MutableLiveData(user.hpMaxStr) // 나타내는 값  ex: 421K, 12M
    val int = MutableLiveData(user.int) // 지능
    val intStr = MutableLiveData(user.intStr) // 나타내는 값
    val duty = MutableLiveData(user.duty) // 효도 , 달마다 용돈 양
    val dutyStr = MutableLiveData(user.dutyStr)

    val items = MutableLiveData(i)

    val defaultUser = DefaultUser().getUser()

    var workEffect:Float // 학업효율
    var restEffect:Float // 휴식효율
    init {
        workEffect = 1f
        restEffect = 1f
//        items.value.find { it.id< }
    }


    fun getUser():User = User(
            user.id,
            0f,0f,
            speed.value!!,
            money.value!!,
            moneyStr.value!!,
            hp.value!!,
            hpStr.value!!,
            hpMax.value!!,
            hpMaxStr.value!!,
            int.value!!,
            intStr.value!!,
            duty.value!!,
            dutyStr.value!!
        )

    fun activeGadget(item:Item){
        if(item.id < 300) {
            val newItems = items.value
            items.value = newItems
            if(item.id <100) {
                speed.value = item.effect
                newItems?.find{it.id<100 && it.apply}?.apply = false
            }
            else if(item.id < 200) {
                restEffect = item.effect
                newItems?.find{ it.id in 100..199 && it.apply}?.apply = false
            }
            else {
                workEffect = item.effect
                newItems?.find{ it.id in 200..299 && it.apply}?.apply = false
            }
            newItems?.find{it.id == item.id}?.apply = true
        }else if(item.id < 400){
            val newItems = arrayListOf<Item>()
            newItems.addAll(items.value?.filter { it.id != item.id }!!)
            if(item.num > 0){
                newItems.add(item)
            }else{
                items.value = newItems
            }
            effectHp(item.effect.toInt())
        }
    }
    fun passiveGadget(item:Item){
        val newItems = items.value
        newItems?.find{it.id == item.id}?.apply = false
        items.value = newItems
        if(item.id <100)
            speed.value = defaultUser.speed
        else if(item.id < 200)
            restEffect = 1f
        else if(item.id < 300)
            workEffect = 1f
    }
    fun byGadget(item:Item){
        val newItems = items.value!!
        item.num += 1
        newItems.add(item)
        items.value = newItems

    }

    private fun effectHp(getHp:Int){
        val getHpToInt = hpMax.value!!/100*getHp
        val getHpToStr = if(getHpToInt < 1000)
            getHpToInt.toString()
        else
            getHpToInt.toString() + hpMaxStr.value!!.last()

        val plusResult = MyConverter.calcValue(true, hp.value!!,hpStr.value!!,getHpToInt,getHpToStr)
        if(MyConverter.compareStr_leftBig(
                hpMax.value!!,  hp.value!!, hpStr.value!!, hpMaxStr.value!!
            )){
            hp.value = plusResult[0].toInt()
            hpStr.value = plusResult[1]
        }
    }

    //    * 1 : 학업 - 지능이 있어야 열리고, 지능을 얻음, 체력 깎임   - 지능+ 체력-
//    * 2 : 알바 - 지능이 있어야 열리고, 돈을 얻음. 체력 깎임     - 돈+ 체력-
//    * 3 : 운동 - 잠금없고, 체력 깎이고 최대 체력 얻음          - 최대체력+ 체격-
//    * 4 : 휴식 - 아이템이 있어야 열리고, 체력을 얻음           - 체력+ X
//    * 5 : 상점 - 열리기 상관없고, 물건을 얻고, 돈을 깎음   - X
//    * 6 : 아이템 - 체력 회복만
//    fun passableAndDoWork(type:Int, getInt:Int, getStr:String, minusInt:Int, minusStr:String):Boolean{
//        val minusResult = if(type==4 || type==6) // 휴식 및 아이템은 더하기만
//            listOf("1","1")
//        else
//            calcValue(false,hp.value!!,minusInt,hpStr.value!!,minusStr)
//
//        if(minusResult[0].toInt() < 0)
//            return false
//
//        else if(type <5){
//            hp.value = minusResult[0].toInt()
//            hpStr.value = minusResult[1]
//        }else{
//            money.value = minusResult[0].toInt()
//            moneyStr.value = minusResult[1]
//            return true
//        }
//
//        when(type){
//            1->{
//                val plusResult = calcValue(true, int.value!!,getInt,intStr.value!!,getStr)
//                if(plusResult[0].toInt() < 0)
//                    return false
//                int.value = plusResult[0].toInt()
//                intStr.value = plusResult[1]
//            }
//            2-> {
//                val plusResult = calcValue(true, money.value!!,getInt,moneyStr.value!!,getStr)
//                if(plusResult[0].toInt() < 0)
//                    return false
//                money.value = plusResult[0].toInt()
//                moneyStr.value = plusResult[1]
//            }
//            3-> {
//                val plusResult = calcValue(true, hpMax.value!!,getInt,hpMaxStr.value!!,getStr)
//                if(plusResult[0].toInt() < 0)
//                    return false
//                hpMax.value = plusResult[0].toInt()
//                hpMaxStr.value = plusResult[1]
//            }
//            4-> {
//                val plusResult = calcValue(true, hp.value!!,getInt,hpStr.value!!,getStr)
//                if(plusResult[0].toInt() < 0)
//                    return false
//                hp.value = plusResult[0].toInt()
//                hpStr.value = plusResult[1]
//            }
//        }
//        return true
//    }


//    fun calcValue(isPlus:Boolean, leftInt:Int, rightInt:Int, leftStr:String, rightStr:String):List<String>{ // return : ["1234564", "1B"]
//        var resultInt:Int
//        var resultStr:String //뒤에는 네ㅏㅈ리만 남도록
//
//        val leftUnit =leftStr.last()
//        val rightUnit =rightStr.last()
//
//        resultInt = if(isPlus) leftInt + rightInt else leftInt-rightInt
//
//
//
//        if(leftInt < 1000 && rightInt < 1000){
//            resultInt = if(isPlus) leftInt + rightInt else leftInt-rightInt
//            resultStr = if(resultInt > 1000)
//                (resultInt/1000).toString() + "A"
//            else
//                resultInt.toString()
//
//            return listOf(resultInt.toString(),resultStr)
//        }
//
//        if(leftUnit == rightUnit){
//            if(isPlus){
//                if(leftInt + rightInt > 1000){
//                    1998
//                }
//            }
//        }else{
//
//        }
//    }

//    fun transValue(intStr:List<String>):List<String>{ //넘치거나 적어질 값을 원래대로
//        var resultInt = intStr[0].toInt()
//        var resultStr = intStr[0]
//        var unit = resultStr.last()
//
//        if(resultInt <1000){
//
//        }else if(resultInt)
////12 345.6890
//        return listOf(resultInt.toString(), resultStr)
//    }




}
class MainViewModelFactory(private val param: User,private val param2:ArrayList<Item>) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(param, param2) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}