package marinj.core.di

import marinj.core.database.DatabaseInitializer
import marinj.core.database.DatabaseInitializerImpl
import org.koin.dsl.module

val databaseModule = module {
    single<DatabaseInitializer> { DatabaseInitializerImpl() }
}