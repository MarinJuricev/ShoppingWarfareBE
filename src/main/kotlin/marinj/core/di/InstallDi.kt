package marinj.core.di

import io.ktor.server.application.Application
import io.ktor.server.application.install
import marinj.feature.auth.di.authModule
import org.koin.logger.slf4jLogger

fun Application.installDi() {
    install(KoinPlugin) {
        slf4jLogger()
        modules(
            authModule,
        )
    }
}