// DateUtils.kt

package com.example.madcampweek2

import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.Date;

object DateUtils {
    fun convertToSeoulTime(created_at: String): String {
//        val NowTime = System.currentTimeMillis()
//        val DF = SimpleDateFormat("yyyy-MM-dd E", Locale.KOREAN)
//
//        val result = DF.format(NowTime)
//
//        return result
        try {
            // 입력된 문자열을 날짜와 시간으로 파싱
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ", Locale.US)
            val date = inputFormat.parse(created_at)

            // 서울 시간대로 설정
            val seoulTimeZone = TimeZone.getTimeZone("Asia/Seoul")
            val calendar = Calendar.getInstance(seoulTimeZone)
            calendar.time = date!!

            // 원하는 형식의 문자열로 포맷팅
            val outputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREA)
            return outputFormat.format(calendar.time)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        // 예외가 발생한 경우 원본 문자열 반환
        return created_at


    }
}

