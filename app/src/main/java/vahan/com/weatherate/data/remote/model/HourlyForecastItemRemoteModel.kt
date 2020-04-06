package vahan.com.weatherate.data.remote.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class HourlyForecastItemRemoteModel(
    @Expose @SerializedName("weather") val weather: List<WeatherDescriptionRemoteModel>,
    @Expose @SerializedName("main") val main: WeatherMainInfoRemoteModel,
    @Expose @SerializedName("dt") val dateMills: Long,
    @Expose @SerializedName("dt_txt") val date: String
)