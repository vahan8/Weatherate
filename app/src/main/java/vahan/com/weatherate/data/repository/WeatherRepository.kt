package vahan.com.weatherate.data.repository

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import vahan.com.weatherate.BaseCoroutineExceptionHandler
import vahan.com.weatherate.data.remote.WeatherRemoteDataSource
import vahan.com.weatherate.data.remote.model.CurrentWeatherRemoteModel
import vahan.com.weatherate.data.remote.model.HourlyForecastRemoteModel

interface WeatherRepository {
    suspend fun getCurrentWeather(lat: Double, lon: Double): CurrentWeatherRemoteModel?

    suspend fun getCurrentWeatherByCity(city: String): List<CurrentWeatherRemoteModel>?

    suspend fun getForecast(lat: Double, lon: Double): HourlyForecastRemoteModel?

}

class WeatherRepositoryImpl(private val dataSource: WeatherRemoteDataSource) : WeatherRepository {

    override suspend fun getCurrentWeather(lat: Double, lon: Double): CurrentWeatherRemoteModel? {
        return withContext(Dispatchers.IO + BaseCoroutineExceptionHandler(CoroutineExceptionHandler)) {
            val response = dataSource.getCurrentWeather(lat, lon)
            if (response.isSuccessful) {
                return@withContext response.body()
            } else {
                return@withContext null
            }
        }
    }

    override suspend fun getCurrentWeatherByCity(city: String): List<CurrentWeatherRemoteModel>? {
        return withContext(Dispatchers.IO + BaseCoroutineExceptionHandler(CoroutineExceptionHandler)) {
            val response = dataSource.getCurrentWeatherByCity(city)
            if (response.isSuccessful && response.body() != null) {
                return@withContext listOf(response.body()!!)
            } else {
                return@withContext null
            }
        }
    }

    override suspend fun getForecast(lat: Double, lon: Double): HourlyForecastRemoteModel? {
        return withContext(Dispatchers.IO + BaseCoroutineExceptionHandler(CoroutineExceptionHandler)) {
            val response = dataSource.getForecast(lat, lon)
            if (response.isSuccessful) {
                return@withContext response.body()
            } else {
                return@withContext null
            }
        }
    }
}