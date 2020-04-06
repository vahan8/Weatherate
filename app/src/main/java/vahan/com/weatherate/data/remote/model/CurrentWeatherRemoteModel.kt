package vahan.com.weatherate.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class CurrentWeatherRemoteModel(
    @Expose @SerializedName("coord") val coordinate: CoordinateRemoteModel,
    @Expose @SerializedName("weather") val weather: List<WeatherDescriptionRemoteModel>,
    @Expose @SerializedName("main") val main: WeatherMainInfoRemoteModel,
    @Expose @SerializedName("name") val cityName: String
):Parcelable