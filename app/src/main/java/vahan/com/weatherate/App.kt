package vahan.com.weatherate

import android.app.Application
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module
import vahan.com.weatherate.di.appModule

class App : Application() {
    override fun onCreate() {
        super.onCreate()


        //Koin
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            androidFileProperties()
            modules(getKoinModules())
        }
    }

    private fun getKoinModules(): List<Module> {
        return listOf(appModule)
    }

}
