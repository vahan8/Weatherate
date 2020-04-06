package vahan.com.weatherate.data.remote.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class HourlyForecastRemoteModel(
    @Expose @SerializedName("list") val items: List<HourlyForecastItemRemoteModel>
)