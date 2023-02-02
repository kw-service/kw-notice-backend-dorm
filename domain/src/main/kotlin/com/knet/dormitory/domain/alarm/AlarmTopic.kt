package com.knet.dormitory.domain.alarm

enum class AlarmTopic(val value:String, val message:String) {
    KW_DORM_COMMON("kw-dorm-common", "빛솔재에 새 공지사항이 올라왔어요!"), KW_DORM_RECRUITMENT("kw-dorm-recruitment", "빛솔재 모집 공지사항이 올라왔어요!");
}