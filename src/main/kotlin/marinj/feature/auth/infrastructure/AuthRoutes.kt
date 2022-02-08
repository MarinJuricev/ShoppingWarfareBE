package marinj.feature.auth.infrastructure

import io.ktor.server.application.Application
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.routing

fun Application.registerAuthRoutes() {

    routing {
        signUpRoute()
    }
}

fun Route.signUpRoute() {

    post("/signup") {

    }

}