package vahan.com.weatherate.util

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide

object ImageLoader {

    private const val ICON_BASE_URL = "http://openweathermap.org/img/wn/"
    private const val ICON_URL_ENDPOINT = "@2x.png"

    fun loadWeatherIcon(imageView: ImageView, icon: String?, defaultImage: Drawable? = null) {
        loadImage(imageView, ICON_BASE_URL.plus(icon).plus(ICON_URL_ENDPOINT), defaultImage)
    }

    private fun loadImage(imageView: ImageView, url: String?, defaultImage: Drawable? = null) {
        Glide.with(imageView.context)
            .load(url)
            .dontAnimate()
            .error(defaultImage)
            .placeholder(defaultImage)
            .fallback(defaultImage)
            .into(imageView)
    }

}