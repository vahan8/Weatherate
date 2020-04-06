package vahan.com.weatherate.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import vahan.com.weatherate.data.remote.model.CurrentWeatherRemoteModel
import vahan.com.weatherate.data.remote.model.HourlyForecastRemoteModel
import vahan.com.weatherate.data.repository.WeatherRepository

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel(), KoinComponent {

    private val _currentWeatherLiveData = MutableLiveData<CurrentWeatherRemoteModel?>()
    val currentWeatherLiveData: LiveData<CurrentWeatherRemoteModel?>
        get() = _currentWeatherLiveData

    private val _forecastLiveData = MutableLiveData<HourlyForecastRemoteModel?>()
    val forecastLiveData: LiveData<HourlyForecastRemoteModel?>
        get() = _forecastLiveData

    private val _loaderCountLiveData = MutableLiveData<Int>(0)
    val loaderCountLiveData: LiveData<Int>
        get() = _loaderCountLiveData

    private val _updateWeatherLiveData = MutableLiveData<CurrentWeatherRemoteModel?>()
    val updateWeatherLiveData: LiveData<CurrentWeatherRemoteModel?>
        get() = _updateWeatherLiveData

    private val _currentWeathersLiveData = MutableLiveData<List<CurrentWeatherRemoteModel>?>()
    val currentWeathersLiveData: LiveData<List<CurrentWeatherRemoteModel>?>
        get() = _currentWeathersLiveData

    fun getCurrentWeather(lat: Double, lon: Double) {
        viewModelScope.launch() {
            _loaderCountLiveData.value = _loaderCountLiveData.value?.plus(1)
            _currentWeatherLiveData.value = repository.getCurrentWeather(lat, lon)
            _loaderCountLiveData.value = _loaderCountLiveData.value?.minus(1)
        }
    }

    fun getForecast(lat: Double, lon: Double) {
        viewModelScope.launch() {
            _loaderCountLiveData.value = _loaderCountLiveData.value?.plus(1)
            _forecastLiveData.value = repository.getForecast(lat, lon)
            _loaderCountLiveData.value = _loaderCountLiveData.value?.minus(1)
        }
    }

    fun setCurrentWeather(weather: CurrentWeatherRemoteModel) {
        _currentWeatherLiveData.value = weather
    }

    fun updateWeather(weather: CurrentWeatherRemoteModel) {
        _updateWeatherLiveData.value = weather
    }

    fun getCurrentWeather(city: String) {
        //for not making unnecessary request
        if (city.isEmpty()) {
            _currentWeathersLiveData.value = null
        } else {
            viewModelScope.launch() {
                _loaderCountLiveData.value = _loaderCountLiveData.value?.plus(1)
                _currentWeathersLiveData.value = repository.getCurrentWeatherByCity(city)
                _loaderCountLiveData.value = _loaderCountLiveData.value?.minus(1)
            }
        }
    }
}
