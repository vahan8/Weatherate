package vahan.com.weatherate.util

import java.util.*

object DateHelper {

    fun getTimeInMillsFromUnixUtcTime(utcTime: Long): Long {
        val offset = TimeZone.getDefault().rawOffset + TimeZone.getDefault().dstSavings
        return utcTime * 1000 - offset
    }
}