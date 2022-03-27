package marinj.feature.auth.infrastructure

import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receiveOrNull
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import marinj.core.di.inject
import marinj.feature.auth.infrastructure.model.RequestUser
import marinj.feature.auth.infrastructure.service.AuthService

fun Application.registerAuthRoutes() {

    val authService: AuthService by inject()

    routing {
        registerRoute(authService)
    }
}

fun Route.registerRoute(authService: AuthService) {

    post("/v1/register") {
        val requestUser = call.receiveOrNull<RequestUser>()
    }

}