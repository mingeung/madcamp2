package com.example.madcampweek2

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.util.*

class Fragment1_Home : Fragment() {

    private lateinit var textView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment1_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val calendarView: CalendarView = view.findViewById(R.id.calendarView)
        textView = view.findViewById(R.id.textView)

        val participationDates = listOf(
            GregorianCalendar(2024, Calendar.JANUARY, 3).timeInMillis,
            GregorianCalendar(2024, Calendar.JANUARY, 10).timeInMillis,
            GregorianCalendar(2024, Calendar.JANUARY, 17).timeInMillis,
            GregorianCalendar(2024, Calendar.JANUARY, 25).timeInMillis
        )

        val today = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        val upcomingDate = participationDates.firstOrNull { it >= today }

        upcomingDate?.let {
            val remainingDays = ((it - today) / (24 * 60 * 60 * 1000)).toInt()
            val index = participationDates.indexOf(it) + 1
            textView.text = if (remainingDays == 0) {
                "Today is ${ordinal(index)} participation.\n Good Luck!"
            } else {
                "D-$remainingDays before ${ordinal(index)} participation"
            }
        } ?: run {
            textView.text = "No upcoming participation"
        }
    }

    private fun ordinal(number: Int): String {
        val suffixes = arrayOf("th", "st", "nd", "rd") + Array(6) { "th" }
        val mod100 = number % 100
        return number.toString() + if (mod100 in 11..13) {
            "th"
        } else {
            suffixes[(number % 10).coerceAtMost(4)]
        }
    }
}