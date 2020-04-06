package vahan.com.weatherate.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object NetworkManager {
    private const val BASE_URL = "https://api.openweathermap.org"

    private var apiRemoteDataSource: WeatherRemoteDataSource? = null

    private fun init() {
        //Adding request interceptors
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(WeatherInterceptor())
            .build()

        apiRemoteDataSource = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .callFactory(client)
            .client(client)
            .build()
            .create(WeatherRemoteDataSource::class.java)
    }

    fun getApiService(): WeatherRemoteDataSource {
        if (apiRemoteDataSource == null)
            init()
        return apiRemoteDataSource!!
    }
}
