package com.worldonetop.growstudent.model

import com.google.gson.annotations.SerializedName

data class Resp<T>(
    @SerializedName("code")
    val code:Int,
    @SerializedName("data")
    val data:T?,
    @SerializedName("items")
    val items:String?,
)