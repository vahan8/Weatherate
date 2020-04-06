package vahan.com.weatherate.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.joda.time.LocalDate
import vahan.com.weatherate.BaseDataBindingAdapter
import vahan.com.weatherate.data.remote.model.HourlyForecastItemRemoteModel
import vahan.com.weatherate.databinding.ListItemForecastBinding
import vahan.com.weatherate.util.DateHelper
import vahan.com.weatherate.util.ImageLoader
import java.util.*


class DailyForecastAdapter(
    private val context: Context,
    private val items: List<HourlyForecastItemRemoteModel>
) : RecyclerView.Adapter<DailyForecastAdapter.ListViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ListItemForecastBinding.inflate(inflater, parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = items[position]
        val date = Date(DateHelper.getTimeInMillsFromUnixUtcTime(item.dateMills))
        val localDate = LocalDate.fromDateFields(date)
        holder.binding.hourTextView.text = localDate.toString("M/d")
        BaseDataBindingAdapter.setTemperature(holder.binding.temperatureTextView, item.main.temp)
        ImageLoader.loadWeatherIcon(holder.binding.iconImageView, item.weather.get(0).icon)
    }

    inner class ListViewHolder(val binding: ListItemForecastBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }
}