package vahan.com.weatherate.ui.main

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.provider.Settings
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import vahan.com.weatherate.R
import vahan.com.weatherate.databinding.FragmentMainBinding
import vahan.com.weatherate.ui.WeatherViewModel
import vahan.com.weatherate.util.LocationHelper
import vahan.com.weatherate.util.WeatherHelper

class MainFragment : Fragment() {

    private val viewModel: WeatherViewModel by sharedViewModel()

    private lateinit var binding: FragmentMainBinding

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        if (LocationHelper.checkPermissions(requireContext())) {
            if (LocationHelper.isLocationEnabled(requireContext()))
                getLocation()
            else {
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }

        } else {
            LocationHelper.requestPermissions(this@MainFragment)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMainBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            fragment = this@MainFragment
            viewmodel = this@MainFragment.viewModel
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.forecastLiveData.observe(viewLifecycleOwner, Observer {
            binding.hourlyForecastRecyclerView.adapter = HourlyForecastAdapter(requireContext(), it?.items?.take(7) ?: listOf())
            val dailyForecastItems = if (it?.items != null) WeatherHelper.getDailyForecast(it.items) else listOf()
            binding.daysForecastRecyclerView.adapter = DailyForecastAdapter(requireContext(), dailyForecastItems)
        })

        viewModel.updateWeatherLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                viewModel.setCurrentWeather(it)
                viewModel.getForecast(it.coordinate.lat, it.coordinate.lon)
            }
        })

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        requireActivity().menuInflater.inflate(R.menu.fragment_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_search -> {
                val action = MainFragmentDirections.actionMainFragmentToSearchFragment()
                findNavController().navigate(action)
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }


    private fun getLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    viewModel.getCurrentWeather(it.latitude, it.longitude)
                    viewModel.getForecast(it.latitude, it.longitude)
                }
            }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            LocationHelper.REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation()
                }
            }
        }
    }

}
