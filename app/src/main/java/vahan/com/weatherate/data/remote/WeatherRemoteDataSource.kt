package vahan.com.weatherate.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import vahan.com.weatherate.data.remote.model.CurrentWeatherRemoteModel
import vahan.com.weatherate.data.remote.model.HourlyForecastRemoteModel


interface WeatherRemoteDataSource {
    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(@Query("lat") lat: Double, @Query("lon") lon: Double): Response<CurrentWeatherRemoteModel>

    @GET("data/2.5/weather")
    suspend fun getCurrentWeatherByCity(@Query("q") cityName: String): Response<CurrentWeatherRemoteModel>

    @GET("data/2.5/forecast")
    suspend fun getForecast(@Query("lat") lat: Double, @Query("lon") lon: Double): Response<HourlyForecastRemoteModel>

}


