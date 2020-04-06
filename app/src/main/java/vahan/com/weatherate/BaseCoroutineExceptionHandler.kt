package vahan.com.weatherate

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.CoroutineContext

class BaseCoroutineExceptionHandler(override val key: CoroutineContext.Key<*>) : CoroutineExceptionHandler {

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        Log.e("Error", exception.localizedMessage!!)
    }

}