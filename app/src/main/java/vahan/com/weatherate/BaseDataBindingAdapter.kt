package vahan.com.weatherate

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import vahan.com.weatherate.util.WeatherHelper

object BaseDataBindingAdapter {

    @JvmStatic
    @BindingAdapter("temperature")
    fun setTemperature(textView: TextView, kelvin: Double) {
        textView.text =  WeatherHelper.convertKelvinToCelsius(kelvin).toInt().toString().plus('\u00B0')
           // String.format("%.2f", WeatherHelper.convertKelvinToCelsius(kelvin)).plus('\u00B0')
    }

    @JvmStatic
    @BindingAdapter("percent")
    fun setPercent(textView: TextView, percent: Double) {
        textView.text = percent.toString().plus("%")
    }

    @JvmStatic
    @BindingAdapter("visibleIf")
    fun changeVisibility(view: View, isVisible: Boolean) {
       // val visible = getVisibility(isVisible)
        view.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

//    private fun getVisibility(visibility: Any?): Boolean {
//        var isVisible = true
//        when (visibility) {
//            null -> isVisible = false
//            is Boolean -> isVisible = visibility
//            is String -> isVisible = visibility.isNotEmpty()
//            is Collection<*> -> isVisible = visibility.isNotEmpty()
//        }
//        return isVisible
//    }

}