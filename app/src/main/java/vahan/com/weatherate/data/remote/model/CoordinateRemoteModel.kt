package vahan.com.weatherate.data.remote.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CoordinateRemoteModel(
    @Expose @SerializedName("lon") val lon: Double,
    @Expose @SerializedName("lat") val lat: Double
): Serializable