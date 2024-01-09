// DateUtils.kt

package com.example.madcampweek2

import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    fun convertToSeoulTime(created_at: String): String {
        val NowTime = System.currentTimeMillis()
        val DF = SimpleDateFormat("yyyy-MM-dd E", Locale.KOREAN)

        val result = DF.format(NowTime)

        return result
    }
}

