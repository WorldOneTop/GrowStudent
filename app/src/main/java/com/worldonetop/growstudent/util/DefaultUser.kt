package com.worldonetop.growstudent.util

import com.worldonetop.growstudent.model.User

class DefaultUser {
    fun getUser():User{
        return User(
            "visitor",0f,0f,3f,0,"0",100,"100",100,"100",1,"1",1,"1",
        )
    }
}