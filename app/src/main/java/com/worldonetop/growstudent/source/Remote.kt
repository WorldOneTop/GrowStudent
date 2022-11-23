package com.worldonetop.growstudent.source

import com.worldonetop.growstudent.model.Resp
import com.worldonetop.growstudent.model.User
import retrofit2.Call
import retrofit2.http.*

interface Remote {

    @FormUrlEncoded
    @POST("login/")
    fun login(
        @Field("id") id: String,
        @Field("pw") pw: String,
    ): Call<Resp<User>>

    @FormUrlEncoded
    @POST("signUp/")
    fun signUp(
        @Field("id") id: String,
        @Field("pw") pw: String,
        @Query("x") x: Float,
        @Query("y") y: Float,
        @Query("speed") speed:Float,
        @Query("money") money:Int,
        @Query("moneyStr") moneyStr:String,
        @Query("hp") hp:Int,
        @Query("hpStr") hpStr:String,
        @Query("int") int:Int,
        @Query("intStr") intStr:String,
        @Query("duty") duty:Int,
        @Query("dutyStr") dutyStr:String,
        @Query("items") items:String,
    ): Call<Resp<Unit>>

    @GET("save/")
    fun save(
        @Query("id") id: String,
        @Query("x") x: Float,
        @Query("y") y: Float,
        @Query("speed") speed:Float,
        @Query("money") money:Int,
        @Query("moneyStr") moneyStr:String,
        @Query("hp") hp:Int,
        @Query("hpStr") hpStr:String,
        @Query("int") int:Int,
        @Query("intStr") intStr:String,
        @Query("duty") duty:Int,
        @Query("dutyStr") dutyStr:String,
        @Query("items") items:String,
    ): Call<Resp<Unit>>

    @GET("save/")
    fun rank(): Call<Array<User>>

}
