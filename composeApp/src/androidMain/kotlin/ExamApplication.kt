import android.app.Application
import di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ExamApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        /** 수정필요. */
        startKoin {
            androidContext(this@ExamApplication)
            androidLogger()
//            modules(appModule() + androidModule)
            modules(appModule())
        }
    }
}