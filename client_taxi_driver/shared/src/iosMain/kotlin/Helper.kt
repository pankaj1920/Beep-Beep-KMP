import dev.icerock.moko.permissions.ios.PermissionsController
import di.appModule
import org.koin.core.context.startKoin
import org.koin.dsl.module
import data.BpLocationDataSource
import dev.icerock.moko.geo.LocationTracker
import domain.dataSource.IBpLocationDataSource

val permissions = module {
    single<IBpLocationDataSource> { BpLocationDataSource(get()) }
}

val locationTracker = module {
    single { LocationTracker(PermissionsController()) }
}


fun initKoin() {
    startKoin {
        modules(
            appModule(),
            permissions,
            locationTracker
        )
    }
}

