package com.worldonetop.growstudent.util

import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat
import com.worldonetop.growstudent.R
import com.worldonetop.growstudent.model.Item
import com.worldonetop.growstudent.model.RoomActive
import kotlin.math.abs

object MyConverter {
    val roomCategory:List<String> = listOf("학업","알바","운동","휴식","상점","기타")

    fun getRoomCategory(num:Int):String{
        if(num<1 || 6 < num)
            return roomCategory[5]
        return roomCategory[num-1]
    }

    /** id
     * 신발류 - 1,2,3,4
     * 휴식 - 100
     * 학업 - 200 201 202
     * 아이템 - 300 301 302 303
     * 기숙사 - 400~407
     * */
    fun getItem(context:Context, id:Int):Item{
        return when(id){
            1 -> Item(1,"신발","평범한 신발이다", 6f, 0,10000,"10A", ContextCompat.getDrawable(context, R.drawable.speed1))
            2 -> Item(2,"스케이트 보드","걷는거보다 힘들다", 10f, 0,200000,"200C", ContextCompat.getDrawable(context, R.drawable.speed2))
            3-> Item(3,"전동 킥보드","방지턱이 많아 불편하다", 17f, 0,100000,"100E", ContextCompat.getDrawable(context, R.drawable.speed3))
            4 -> Item(4,"레이","최고의 이동수단", 25f, 0,999888,"999J", ContextCompat.getDrawable(context, R.drawable.speed4))
            100 -> Item(100,"안마의자","안락하다", 2.5f, 0,400000,"400E", ContextCompat.getDrawable(context, R.drawable.gadget_hp))
            200 -> Item(200,"필통","필기구가 들어있다", 1.1f, 0,100,"100", ContextCompat.getDrawable(context, R.drawable.gadget_int1))
            201 -> Item(201,"헤드셋","집중력 향상에 도움된다", 1.3f, 0,1000000,"100C", ContextCompat.getDrawable(context, R.drawable.gadget_int2))
            202 -> Item(202,"노트북","다양한 업무에 쓰인다", 1.8f, 0,200000,"200E", ContextCompat.getDrawable(context, R.drawable.gadget_int3))
            300 -> Item(300,"초콜릿","충분한 열량", 2f, 0,100,"100", ContextCompat.getDrawable(context, R.drawable.item_hp1))
            301 -> Item(301,"에너지 드링크","각성에 도움을 준다", 10f, 0,1000000,"100B", ContextCompat.getDrawable(context, R.drawable.item_hp2))
            302 -> Item(302,"조각 케이크","매우 충분한 열량", 20f, 0,10000,"10E", ContextCompat.getDrawable(context, R.drawable.item_hp3))
            400 -> Item(400,"기숙사 1관","기숙사 1관을 들어갈 수 있다.",0f, 0, 1, "1", null)
            401 -> Item(401,"기숙사 2관","기숙사 2관을 들어갈 수 있다.",0f, 0, 100000, "100A", null)
            402 -> Item(402,"기숙사 3관","기숙사 3관을 들어갈 수 있다.",0f, 0, 100000, "100B", null)
            403 -> Item(403,"기숙사 4관","기숙사 4관을 들어갈 수 있다.",0f, 0, 100000, "100C", null)
            404 -> Item(404,"기숙사 5관","기숙사 5관을 들어갈 수 있다.",0f, 0, 10000, "10D", null)
            405 -> Item(405,"기숙사 6관","기숙사 6관을 들어갈 수 있다.",0f, 0, 100000, "100E", null)
            406 -> Item(406,"기숙사 7관","기숙사 7관을 들어갈 수 있다.",0f, 0, 100000, "1000F", null)
            407 -> Item(407,"기숙사 8관","기숙사 8관을 들어갈 수 있다.",0f, 0, 100000, "100G", null)
            else -> Item(303,"프로틴","근육까지 키울 수 있다", 40f, 0, 100000, "100D", ContextCompat.getDrawable(context, R.drawable.item_hp4))
        }
    }
    fun getAllItem(context: Context):ArrayList<Item>{
        val result = ArrayList<Item>()
        for( i in 1..4)
            result.add(getItem(context,i))

        result.add(getItem(context,100))

        for( i in 200..202)
            result.add(getItem(context,i))
        for( i in 300..303)
            result.add(getItem(context,i))
        for( i in 400..407)
            result.add(getItem(context,i))

        return result
    }

    fun strToItemsList(data:String, context: Context):ArrayList<Item>{
        if(data.isBlank())
            return arrayListOf()
        val result = ArrayList<Item>()
        val strSplit = data.split(",")
        val itemNo = ArrayList<Int>()
        val itemApply = ArrayList<Boolean>()
        val itemCount = ArrayList<Int>()
        for(i in strSplit.indices step(3)){
            itemNo.add(strSplit[i].toInt())
            itemApply.add(strSplit[i+1].toBoolean())
            itemCount.add(strSplit[i+2].toInt())
        }
        for(i in itemNo.indices){
            result.add(getItem(context, itemNo[i]))
            result.last().apply = itemApply[i]
            result.last().num = itemNo[i]
        }
        return result
    }
    fun itemsListToStr(data:ArrayList<Item>):String{
        var result = ""
        for(i in data.indices){
            result += "${data[i].id},${data[i].apply},${data[i].num}"
            if(i+1 != data.size)
                result += ","
        }
        return result
    }


    fun idToRoomImg(id:Int):Int = when(id){
        1 -> R.drawable.bg_map1
        4, 10, 13 -> R.drawable.bg_map4
        3, 6 -> R.drawable.bg_map6
        7, 8,  -> R.drawable.bg_map7
        9  -> R.drawable.bg_map9
        11 -> R.drawable.bg_map11
        17 -> R.drawable.bg_map17
        18 -> R.drawable.bg_map18
        20 -> R.drawable.bg_map20
        in 24..31 -> R.drawable.bg_map_31
        34 -> R.drawable.bg_map34
        37 -> R.drawable.bg_map_37
        else ->  -1
    }



    fun calcValue(isPlus:Boolean, leftInt:Int, leftStr:String, rightInt:Int, rightStr:String):List<String>{ // return : ["1234", "1B"]
        var resultInt = if(isPlus) leftInt + rightInt else leftInt-rightInt
        var bigUnit:Char = if(leftInt < 1000 && rightInt < 1000)
            'A'
        else if(leftInt > rightInt)
            leftStr.last()
        else
            rightStr.last()

        if(abs(resultInt)<1000){ // 단위 빼야함
            bigUnit -= 1
            if(bigUnit < 'A')
                return listOf(resultInt.toString(), resultInt.toString())
        }else if(abs(resultInt)>1000000){
            bigUnit +=1
            resultInt /= 1000
        }
        return listOf(resultInt.toString(), (resultInt/1000).toString()+bigUnit)
    }

    fun compareStr_leftBig(leftInt:Int, rightInt:Int, left:String, right:String):Boolean{
        return calcValue(false, leftInt, left, rightInt, right)[0].toInt() > 0
    }

    fun idToRoomActive(id:Int):Array<RoomActive> = when(id){
        1 ->arrayOf(//공대
            RoomActive( "한컴 타자연습", 0,"0",1,"1",1,"1",),
            RoomActive( "인프런 강의 듣기", 10000,"10A",500,"500",750,"750",),
            RoomActive( "학술 동아리 활동", 50000,"50C",75000,"75A",50000,"50A",),
            RoomActive( "알파고 클론코딩", 50000,"50H",75000,"75F",50000,"50F",),
            RoomActive( "자비스 만들기", 100000,"100J",20000,"20I",24000,"24I",),
        )3 ->arrayOf(//의대
            RoomActive( "의사 가운 고르기", 0,"0",1,"1",1,"1",),
            RoomActive( "감기약 처방하기", 10000,"10C",1000,"1B",1250,"1B",),
            RoomActive( "수술 참관", 100000,"100E",50000,"50C",78000,"78C",),
            RoomActive( "동의 보감 읽기", 50000,"500F",120000,"120C",200000,"200C",),
        )4 ->arrayOf(//인문대
            RoomActive( "끝말잇기", 0,"0",1,"1",1,"1",),
            RoomActive( "초성 퀴즈", 100,"100",4,"4",6,"6",),
            RoomActive( "고요속의 외침", 3000,"3A",38,"38",28,"28"),
            RoomActive( "맞춤법 지적하기", 7000,"7A",100,"100",90,"90"),
        )6 ->arrayOf(//실험동물
            RoomActive( "개미 관찰하기", 10,"10",3,"3",3,"3",),
            RoomActive( "쥐 식단 짜기", 100,"100",7,"7",14,"14",),
        )7 ->arrayOf(//자대
            RoomActive( "물 관측", 100,"100",5,"5",5,"5",),
            RoomActive( "불 관측", 100,"100",20,"20",23,"23",),
            RoomActive( "흙 관측", 100,"100",2,"2",1,"1",),
            RoomActive( "공기 관측", 100,"100",10,"10",11,"11",),
            RoomActive( "달 위상 관측", 15000,"15A",30,"30",32,"32",),
            RoomActive( "태양 흑점 관측", 200000,"200D",10000,"10A",15000,"15A",),
        )8 ->arrayOf(//생대
            RoomActive( "창조론 주장하기", 0,"0",1,"1",1,"1",),
            RoomActive( "유전자 편집하기", 50000,"50G",10000,"10f",24000,"24F",),
        )9 ->arrayOf(//학관
        )11 ->arrayOf(//일송 아트홀
            RoomActive( "무대 청소", 0,"0",1,"1",1,"1",),
            RoomActive( "좌석 청소하기", 10,"10",2,"2",14,"14",),
            RoomActive( "카메라 담당하기", 10000,"10A",2000,"2A",2700,"27A",),
        )10, 13 ->arrayOf(//사회경영1, 2관
            RoomActive( "사회학배우기", 100000,"100A",1000,"1A",1500,"1A",),
            RoomActive( "경영학배우기", 100000,"100A",2000,"2A",3200,"3A",),
            RoomActive( "심즈 경영하기", 10,"10",2,"2",14,"14",),
        )16 ->arrayOf(//기초관
            RoomActive( "말하기", 0,"0",1,"1",1,"1",),
            RoomActive( "듣기", 10,"10",2,"2",4,"4",),
            RoomActive( "쓰기", 10,"10",2,"2",4,"4",),
            RoomActive( "받아쓰기", 10,"10",2,"2",4,"4",),
        )17 ->arrayOf(//도서관
            RoomActive( "도서관 ASML 듣기", 0,"0",1,"1",1,"1",),
            RoomActive( "마법천자문 보기", 10,"10",2,"2",4,"4",),
            RoomActive( "공룡세계에서 살아남기 보기", 100,"100",20,"20",30,"30",),
            RoomActive( "코난 범인 표시하기", 33000,"33A",500,"500",550,"550",),
        )18 ->arrayOf(//랙센
            RoomActive( "거울 셀카 찍기", 0,"0",1,"1",1,"1",),
            RoomActive( "런닝머신 달리기", 10,"10",4,"4",3,"3",),
            RoomActive( "스쿼시하기", 5000,"5D",10000,"10C",9000,"9C",),
            RoomActive( "수영하기", 100000,"100D",1000,"1D",800000,"800C",),
            RoomActive( "고중량 스쿼트", 100000,"100E",3000,"3E",4000,"4E",),
        )20 ->arrayOf(//실내 테니스장
            RoomActive( "테니스의 왕자 시청", 0,"0",1,"1",1,"1",),
            RoomActive( "포핸드 연습", 10000,"10B",200000,"200A",190000,"19A",),
            RoomActive( "백핸드 연습", 10000,"10C",200000,"200B",190000,"19B",),
        )
        in 24..31 ->arrayOf(
            RoomActive( "기숙사 1관", 400,"0",1,"1",1,"1"),
            RoomActive( "기숙사 2관", 401,"0",10,"10",1,"1"),
            RoomActive( "기숙사 3관", 402,"0",35,"35",1,"1"),
            RoomActive( "기숙사 4관", 403,"0",100,"100",1,"1"),
            RoomActive( "기숙사 5관", 404,"0",1000,"1A",1,"1"),
            RoomActive( "기숙사 6관", 405,"0",7500,"7A",1,"1"),
            RoomActive( "기숙사 7관", 406,"0",100000,"100A",1,"1"),
            RoomActive( "기숙사 8관", 407,"0",100000,"100B",1,"1"),
        )34 ->arrayOf(//축구장
            RoomActive( "골키퍼하기", 10,"10",3,"3",1,"1",),
            RoomActive( "미드필더하기", 100,"100",3000,"3B",2700,"2B",),
            RoomActive( "공격수 하기", 1000,"1A",30000,"30B",27000,"27B",),
            RoomActive( "감독하기", 100000,"100C",300000,"300B",270000,"270B",),
        )37 ->arrayOf(//병원
            RoomActive( "수액 놓기", 10,"10",2,"2",14,"14",),
            RoomActive( "수술 보조하기", 10000,"10D",450000,"450C",555000,"555C",),
            RoomActive( "수술하기", 10000,"10F",500000,"500E",600000,"600E",),
            RoomActive( "닥터헬기 조종하기", 10000,"10H",70000,"70G",100000,"1H",),
            RoomActive( "예토전생하기", 10000,"10J",50000,"50I",80000,"80I",),
        )
        else ->arrayOf()
    }

}
/** id
 * 신발류 - 1,2,3,4
 * 휴식 - 5
 * 학업 - 6 7 8
 * 아이템 - 9 10 11 12
 * */