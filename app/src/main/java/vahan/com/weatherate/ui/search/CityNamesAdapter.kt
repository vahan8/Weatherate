package vahan.com.weatherate.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vahan.com.weatherate.data.remote.model.CurrentWeatherRemoteModel
import vahan.com.weatherate.databinding.ListItemCityNamesBinding


class CityNamesAdapter(
    private val context: Context,
    private val items: MutableList<CurrentWeatherRemoteModel?>,
    val onClick: (CurrentWeatherRemoteModel) -> Unit
) : RecyclerView.Adapter<CityNamesAdapter.ListViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ListItemCityNamesBinding.inflate(inflater, parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = items[position]
        holder.binding.nameTextView.text = item?.cityName
    }

    fun updateItems(items: List<CurrentWeatherRemoteModel?>?) {
        this.items.clear()
        items?.let {
            this.items.addAll(it)
        }
        notifyDataSetChanged()
    }

    inner class ListViewHolder(val binding: ListItemCityNamesBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                onClick(items[layoutPosition]!!)
            }
        }

    }
}