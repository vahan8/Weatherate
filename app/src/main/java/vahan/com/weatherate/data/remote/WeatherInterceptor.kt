package vahan.com.weatherate.data.remote

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class WeatherInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url()

        // Adding api key not to provide it in every request as parameter
        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("APPID", API_KEY)
            .build()

        // Request customization: add request headers
        val requestBuilder = originalRequest.newBuilder().url(url)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }

    companion object {
        private const val API_KEY = "de123d83445eb8a214da533f10f1b1fa"
    }
}
