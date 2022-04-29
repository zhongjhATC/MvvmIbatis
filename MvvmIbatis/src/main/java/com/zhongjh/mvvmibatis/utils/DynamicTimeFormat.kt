package com.zhongjh.mvvmibatis.utils

import java.text.FieldPosition
import java.text.SimpleDateFormat
import java.util.*

/**
 * 动态时间格式化
 *
 * @author zhongjh
 * @date 2021/3/31
 */
class DynamicTimeFormat(yearFormat: String?, dateFormat: String?, timeFormat: String?) :
    SimpleDateFormat(
        String.format(
            LOCALE, "%s %s %s", yearFormat, dateFormat, timeFormat
        ), LOCALE
    ) {
    private var mFormat = "%s"

    constructor(format: String) : this() {
        mFormat = format
    }

    constructor(
        format: String = "%s",
        yearFormat: String? = "yyyy年",
        dateFormat: String? = "M月d日",
        timeFormat: String? = "HH:mm"
    ) : this(yearFormat, dateFormat, timeFormat) {
        mFormat = format
    }

    override fun format(date: Date, toAppendTo: StringBuffer, pos: FieldPosition): StringBuffer {
        var value = toAppendTo
        value = super.format(date, value, pos)
        val otherCalendar = calendar
        val todayCalendar = Calendar.getInstance()
        val hour = otherCalendar[Calendar.HOUR_OF_DAY]
        val times = value.toString().split(" ".toRegex()).toTypedArray()
        val moment = if (hour == 12) MOMENTS[0] else MOMENTS[hour / 6 + 1]
        val timeFormat = moment + " " + times[2]
        val dateFormat = times[1] + " " + timeFormat
        val yearFormat = times[0] + dateFormat
        value.delete(0, value.length)
        val yearTemp = todayCalendar[Calendar.YEAR] == otherCalendar[Calendar.YEAR]
        if (yearTemp) {
            val todayMonth = todayCalendar[Calendar.MONTH]
            val otherMonth = otherCalendar[Calendar.MONTH]
            // 表示是同一个月
            if (todayMonth == otherMonth) {
                val temp = todayCalendar[Calendar.DATE] - otherCalendar[Calendar.DATE]
                when (temp) {
                    0 -> value.append(timeFormat)
                    1 -> {
                        value.append("昨天 ")
                        value.append(timeFormat)
                    }
                    2 -> {
                        value.append("前天 ")
                        value.append(timeFormat)
                    }
                    3, 4, 5, 6 -> {
                        val dayOfMonth = otherCalendar[Calendar.WEEK_OF_MONTH]
                        val todayOfMonth = todayCalendar[Calendar.WEEK_OF_MONTH]
                        // 表示是同一周
                        if (dayOfMonth == todayOfMonth) {
                            val dayOfWeek = otherCalendar[Calendar.DAY_OF_WEEK]
                            // 判断当前是不是星期日     如想显示为：周日 12:09 可去掉此判断
                            if (dayOfWeek != 1) {
                                value.append(WEEKS[otherCalendar[Calendar.DAY_OF_WEEK] - 1])
                                value.append(' ')
                                value.append(timeFormat)
                            } else {
                                value.append(dateFormat)
                            }
                        } else {
                            value.append(dateFormat)
                        }
                    }
                    else -> value.append(dateFormat)
                }
            } else {
                value.append(dateFormat)
            }
        } else {
            value.append(yearFormat)
        }
        val length = value.length
        value.append(String.format(LOCALE, mFormat, value.toString()))
        value.delete(0, length)
        return value
    }

    companion object {
        private val LOCALE = Locale.CHINA
        private val WEEKS = arrayOf("周日", "周一", "周二", "周三", "周四", "周五", "周六")
        private val MOMENTS = arrayOf("中午", "凌晨", "早上", "下午", "晚上")
    }
}