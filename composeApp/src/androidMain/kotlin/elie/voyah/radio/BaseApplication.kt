package elie.voyah.radio

import android.app.Application
import org.koin.android.ext.koin.androidContext
import elie.voyah.radio.app.di.initKoin

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@BaseApplication)
        }
    }
}