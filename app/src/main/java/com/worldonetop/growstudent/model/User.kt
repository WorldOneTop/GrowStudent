package com.worldonetop.growstudent.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User (
    @SerializedName("userId_id")
    var id: String,
    @SerializedName("x")
    var x: Float=0f, //위치는 상단 왼쪽으로
    @SerializedName("y")
    var y: Float=0f, //위치는 상단 왼쪽으로
    @SerializedName("speed")
    var speed:Float=0f, // 이속
    @SerializedName("money")
    var money:Int=0, // 돈
    @SerializedName("moneyStr")
    var moneyStr:String, // 돈
    @SerializedName("hp")
    var hp:Int=0, // 체력
    @SerializedName("hpStr")
    var hpStr:String, // 나타내는 값  ex: 421K, 12M
    @SerializedName("hpMax")
    var hpMax:Int=0, // 체력
    @SerializedName("hpMaxStr")
    var hpMaxStr:String, // 나타내는 값  ex: 421K, 12M
    @SerializedName("int")
    var int:Int=0, // 지능
    @SerializedName("intStr")
    var intStr:String, // 나타내는 값
    @SerializedName("duty")
    var duty:Int=0, // 효도 , 달마다 용돈 양
    @SerializedName("dutyStr")
    var dutyStr:String,
): Serializable
// 단위 : K, M, G, T, P, E, Z, Y -> 각 10의 세제곱


data class UserLocate(
    var x: Float, // 위치는 View에서 그리는 좌표계로
    var y: Float,
)
