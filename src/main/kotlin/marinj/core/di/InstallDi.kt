package marinj.core.di

import io.ktor.server.application.Application
import io.ktor.server.application.install
import marinj.core.config.AppConfig
import marinj.core.config.JwtConfig
import marinj.core.config.initConfig
import marinj.feature.auth.di.authModule
import org.koin.logger.slf4jLogger

fun Application.installDi() {
    install(KoinPlugin) {
        slf4jLogger()
        modules(
            coreModule,
            authModule,
        )
    }
}
