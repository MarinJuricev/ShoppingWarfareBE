package marinj

import io.ktor.server.application.Application
import marinj.core.di.installDi
import marinj.feature.account.infrastructure.installAccount
import marinj.feature.account.infrastructure.registerAuthRoutes
import marinj.plugins.configureRouting
import marinj.plugins.configureSerialization
import marinj.plugins.configureSockets

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    installDi()
    installAccount()

    configureRouting()
    configureSockets()
    configureSerialization()

    registerAuthRoutes()
}
