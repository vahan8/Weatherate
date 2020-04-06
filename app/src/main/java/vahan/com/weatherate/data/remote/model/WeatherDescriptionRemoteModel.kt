package vahan.com.weatherate.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class WeatherDescriptionRemoteModel(
    @Expose @SerializedName("id") val id: Int,
    @Expose @SerializedName("main") val main: String,
    @Expose @SerializedName("description") val description: String,
    @Expose @SerializedName("icon") val icon: String
): Parcelable