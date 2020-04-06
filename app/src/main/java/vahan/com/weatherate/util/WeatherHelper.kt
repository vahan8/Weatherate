package vahan.com.weatherate.util

import org.joda.time.LocalDate
import vahan.com.weatherate.data.remote.model.HourlyForecastItemRemoteModel

import java.util.*

object WeatherHelper {
    fun convertKelvinToCelsius(kelvin: Double): Double {
        return kelvin.minus(273.15)
    }

    //Returns weather for next 5 days, as weather for the date it takes first forecast for that date but algorithm can be changed to take average weather for that date or any other algorithm
    fun getDailyForecast(items: List<HourlyForecastItemRemoteModel>): List<HourlyForecastItemRemoteModel> {
        var previousDate: LocalDate? = null
        val result: MutableList<HourlyForecastItemRemoteModel> = mutableListOf()
        for (item in items) {
            val curDate = LocalDate(Date(DateHelper.getTimeInMillsFromUnixUtcTime(item.dateMills)))
            if (previousDate == null) {
                previousDate = curDate
            } else {
                if (curDate != previousDate) {
                    result.add(item)
                    previousDate = curDate
                }
            }
        }
        return result
    }
}