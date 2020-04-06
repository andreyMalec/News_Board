package com.proj.newsboard.util

import androidx.core.util.Pair
import androidx.fragment.app.FragmentActivity
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.proj.newsboard.R
import java.util.*

class DateRangePicker(private val activity: FragmentActivity, private val onDatePicked: (startDate: String, endDate: String) -> Unit) {
    private val millisInMonth: Long = 2592000000

    fun pick() {
        val picker = getRangeDatePicker()
        picker.addOnPositiveButtonClickListener {
            val dateFormatter = DateFormatter()
            val startDate = dateFormatter.fromNormalToApiFormat(Date(it.first!!))
            val endDate = dateFormatter.fromNormalToApiFormat(Date(it.second!!))

            onDatePicked(startDate, endDate)
        }
        picker.show(activity.supportFragmentManager, "datePicker")
    }

    private fun getRangeDatePicker(): MaterialDatePicker<Pair<Long, Long>> {
        return MaterialDatePicker.Builder.dateRangePicker().apply {
            setTitleText(activity.getString(R.string.date_pick))
            setCalendarConstraints(getCalendarConstraints())
        }.build()
    }

    private fun getCalendarConstraints(): CalendarConstraints {
        val calendar = Calendar.getInstance()
        val startDate = calendar.timeInMillis - millisInMonth
        val endDate = calendar.timeInMillis

        return CalendarConstraints.Builder().apply {
            setStart(startDate)
            setEnd(endDate)
            setValidator(RangeValidator(startDate, endDate))
        }.build()
    }
}