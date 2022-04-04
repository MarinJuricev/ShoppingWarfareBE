package marinj.core.di

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.config.ApplicationConfig
import marinj.core.config.JwtConfig
import marinj.core.config.ShoppingWarfareConfig
import marinj.core.config.buildJwtConfig
import marinj.core.database.DatabaseInitializer
import marinj.core.database.DatabaseInitializerImpl
import marinj.feature.auth.di.authModule
import org.koin.dsl.module
import org.koin.logger.slf4jLogger

fun Application.installDi() {
    val coreModule = module {
        single<DatabaseInitializer> { DatabaseInitializerImpl() }
        single {
            this@installDi.environment.config
        }
        single {
            val applicationConfig: ApplicationConfig = get()
            val jwtConfig = buildJwtConfig(applicationConfig)

            ShoppingWarfareConfig(
                jwtConfig = jwtConfig
            )
        }
    }

    install(KoinPlugin) {
        slf4jLogger()
        modules(
            coreModule,
            authModule,
        )
    }
}