package marinj.feature.auth.infrastructure

import io.ktor.server.application.Application
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import marinj.core.di.inject
import marinj.feature.auth.infrastructure.service.AuthService

fun Application.registerAuthRoutes() {

    val authService: AuthService by inject()

    routing {
        signUpRoute(authService)
    }
}

fun Route.signUpRoute(authService: AuthService) {

    post("/v1/signup") {

    }

}