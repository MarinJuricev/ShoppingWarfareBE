package marinj.core.di

import marinj.core.config.AppConfig
import marinj.core.database.DatabaseInitializer
import marinj.core.database.DatabaseInitializerImpl
import org.koin.dsl.module

val coreModule = module {
    single<DatabaseInitializer> { DatabaseInitializerImpl() }
    single { AppConfig() }
}