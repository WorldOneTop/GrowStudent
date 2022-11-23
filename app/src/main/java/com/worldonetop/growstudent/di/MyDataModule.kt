package com.worldonetop.growstudent.di

import android.content.Context
import androidx.core.content.ContextCompat
import com.worldonetop.growstudent.R
import com.worldonetop.growstudent.model.MapBuilding
import com.worldonetop.growstudent.util.MyData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MyDataModule {
    @Singleton
    @Provides
    fun provideMyData(@ApplicationContext context: Context):MyData{
        val result = MyData()
        result.mapData = listOf(
            MapBuilding(1,"공학관","공대생은 체크셔츠?",1, ContextCompat.getDrawable(context, R.drawable.ic_study)),
            MapBuilding(2,"대학본부","잔디 밟지 마시오.",6, ContextCompat.getDrawable(context, R.drawable.ic_other)),
            MapBuilding(3,"의학관","진료 좀 부탁드립니다",1, ContextCompat.getDrawable(context, R.drawable.ic_study)),
            MapBuilding(4,"인문관","너 자신을 알라",1, ContextCompat.getDrawable(context, R.drawable.ic_study)),
            MapBuilding(5,"대학본부별관","밥이 맛있어요",6, ContextCompat.getDrawable(context, R.drawable.ic_other)),
            MapBuilding(6,"실험동물센터","실험식물센터는 없나요",2, ContextCompat.getDrawable(context, R.drawable.ic_work)),
            MapBuilding(7,"자연과학관","엘리베이터 혼잡",1, ContextCompat.getDrawable(context, R.drawable.ic_study)),
            MapBuilding(8,"생명과학관","실험의 늪으로 오세요",1, ContextCompat.getDrawable(context, R.drawable.ic_study)),
            MapBuilding(9,"Campus Life Center","학생들의 쉼터",5, ContextCompat.getDrawable(context, R.drawable.ic_shopping)),
            MapBuilding(10,"사회·경영1관","애덤 스미스의 보이지 않는 손",1, ContextCompat.getDrawable(context, R.drawable.ic_study)),
            MapBuilding(11,"일송아트홀","공연 보러 오세요",2, ContextCompat.getDrawable(context, R.drawable.ic_work)),
            MapBuilding(12,"창업보육센터","창업 동아리 지원하러 왔습니다",2, ContextCompat.getDrawable(context, R.drawable.ic_work)),
            MapBuilding(13,"사회·경영2관","애덤 스미스의 보이지 않는 손",1, ContextCompat.getDrawable(context, R.drawable.ic_study)),
            MapBuilding(14,"국제관","외국인 친구들과 친해져 보자",2, ContextCompat.getDrawable(context, R.drawable.ic_work)),
            MapBuilding(15,"국제회의관","큰 회의실",2, ContextCompat.getDrawable(context, R.drawable.ic_work)),
            MapBuilding(16,"기초교육관","신입생들은 필수",1, ContextCompat.getDrawable(context, R.drawable.ic_study)),
            MapBuilding(17,"일송기념도서관","독서가 부족하진 않으신가요?",1, ContextCompat.getDrawable(context, R.drawable.ic_study)),
            MapBuilding(18,"한림레크리에이션센터","운동을 배우고 싶다면 이곳에서",3, ContextCompat.getDrawable(context, R.drawable.ic_exercise)),
            MapBuilding(19,"학군단","저희와 함께 ROTC 어떠세요?",3, ContextCompat.getDrawable(context, R.drawable.ic_exercise)),
            MapBuilding(20,"실내테니스장","체육 강의는 필수",3, ContextCompat.getDrawable(context, R.drawable.ic_exercise)),
            MapBuilding(21,"의료·바이오융합연구원","여긴 다른 학교인가요",2, ContextCompat.getDrawable(context, R.drawable.ic_work)),
            MapBuilding(22,"산학협력관","취업 준비는 이곳에서",2, ContextCompat.getDrawable(context, R.drawable.ic_work)),
            MapBuilding(23,"도헌글로벌스쿨","융합교육의 선두",1, ContextCompat.getDrawable(context, R.drawable.ic_study)),
            MapBuilding(24,"학생생활관 1관","기숙사",4, ContextCompat.getDrawable(context, R.drawable.ic_rest)),
            MapBuilding(25,"학생생활관 2관","기숙사",4, ContextCompat.getDrawable(context, R.drawable.ic_rest)),
            MapBuilding(26,"학생생활관 3관","기숙사",4, ContextCompat.getDrawable(context, R.drawable.ic_rest)),
            MapBuilding(27,"학생생활관 4관","기숙사",4, ContextCompat.getDrawable(context, R.drawable.ic_rest)),
            MapBuilding(28,"학생생활관 5관","기숙사",4, ContextCompat.getDrawable(context, R.drawable.ic_rest)),
            MapBuilding(29,"학생생활관 6관","기숙사",4, ContextCompat.getDrawable(context, R.drawable.ic_rest)),
            MapBuilding(30,"학생생활관 7관","기숙사",4, ContextCompat.getDrawable(context, R.drawable.ic_rest)),
            MapBuilding(31,"학생생활관 8관","기숙사",4, ContextCompat.getDrawable(context, R.drawable.ic_rest)),
            MapBuilding(32,"체육 기자재실","운동하는 데에 도구가 필요하시다구요?",2, ContextCompat.getDrawable(context, R.drawable.ic_work)),
            MapBuilding(33,"H Stadium","야외 체육활동은 이곳에서",3, ContextCompat.getDrawable(context, R.drawable.ic_exercise)),
            MapBuilding(34,"ILSONG Stadium","축구장 완비",3, ContextCompat.getDrawable(context, R.drawable.ic_exercise)),
            MapBuilding(35,"씨름장","모래 흩날리며",3, ContextCompat.getDrawable(context, R.drawable.ic_exercise)),
            MapBuilding(36,"온실","온실 속 화초",4, ContextCompat.getDrawable(context, R.drawable.ic_work)),
            MapBuilding(37,"춘천성심병원","한림대생이라면 학생할인 가능해요",2, ContextCompat.getDrawable(context, R.drawable.ic_work)),
        )
        return result
    }

}
/** category
 * 1 : 학업
 * 2 : 알바
 * 3 : 운동
 * 4 : 휴식
 * 5 : 상점
 * 6 : 기타
 * */