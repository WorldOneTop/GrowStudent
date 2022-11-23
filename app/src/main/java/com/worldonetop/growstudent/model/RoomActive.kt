package com.worldonetop.growstudent.model

data class RoomActive (
    val name:String, // 해당 일 이름
    val limit:Int, // 해당 조건시 가능   -> 기숙사는 Item id값 400~407
    val limitStr:String, // 해당 조건시 가능
    val benefit:Int, // 수행 시 얻는 재화 양
    val benefitStr:String, // 수행 시 얻는 재화 양
    val minusHp:Int, // 체력 깎이는 양
    val minusHpStr:String, // 체력 깎이는 양
)
/** category
 * 1 : 학업 - 지능이 있어야 열리고, 지능을 얻음, 체력 깎임
 * 2 : 알바 - 지능이 있어야 열리고, 돈을 얻음. 체력 깎임
 * 3 : 운동 - 잠금없고, 체력 깎이고 최대 체력 얻음
 * 4 : 휴식 - 아이템이 있어야 열리고, 체력을 얻음
 * 5 : 상점 - 열리기 상관없고, 물건을 얻고, 돈을 깎음
 * 6 : 기타 - ㅁㄹ
 * */
