package com.worldonetop.growstudent.source

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.worldonetop.growstudent.model.User
import org.json.JSONArray
import org.json.JSONObject


class Local(context: Context) {

    private val preference = context.getSharedPreferences("userData", AppCompatActivity.MODE_PRIVATE)
    private val editPreference = preference.edit()

    fun isLogin():Boolean = preference.getBoolean("isLogin",false)
    fun setLogin(isLogin:Boolean){
        editPreference.putBoolean("isLogin",isLogin)
        editPreference.apply()
    }

    fun isAlone():Boolean = preference.getBoolean("isAlone",false)
    fun setAlone(isLogin:Boolean){
        editPreference.putBoolean("isAlone",isLogin)
        editPreference.apply()
    }

    fun getUserData(): User {
        return User(
            preference.getString("id",null) ?: "visitor",
            preference.getFloat("x",0f),
            preference.getFloat("y",0f),
            preference.getFloat("speed",5f),
            preference.getInt("money",0),
            preference.getString("moneyStr","100") ?: "100",
            preference.getInt("hp",100),
            preference.getString("hpStr",null) ?: "100",
            preference.getInt("hpMax",100),
            preference.getString("hpMaxStr",null) ?: "100",
            preference.getInt("int",1),
            preference.getString("intStr",null) ?: "1",
            preference.getInt("duty",1),
            preference.getString("dutyStr",null) ?: "1",
        )
    }
    fun setUserData(user:User){
        editPreference.putString("id",user.id)
        editPreference.putFloat("x",user.x)
        editPreference.putFloat("y",user.y)
        editPreference.putInt("money",user.money)
        editPreference.putString("moneyStr",user.moneyStr)
        editPreference.putFloat("speed",user.speed)
        editPreference.putInt("hp",user.hp)
        editPreference.putString("hpStr",user.hpStr)
        editPreference.putInt("hpMax",user.hp)
        editPreference.putString("hpMaxStr",user.hpStr)
        editPreference.putInt("int",user.int)
        editPreference.putString("intStr",user.intStr)
        editPreference.putInt("duty",user.duty)
        editPreference.putString("dutyStr",user.dutyStr)
        editPreference.apply()
    }
    fun getItemData():String{
        return preference.getString("items","") ?: ""
    }
    fun setItemData(data:String){
        editPreference.putString("items",data)
        editPreference.apply()
    }
}