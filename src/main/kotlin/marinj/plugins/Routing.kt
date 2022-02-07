package marinj.plugins

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.plugins.StatusPages
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun Application.configureRouting() {


    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        install(StatusPages) {
            exception<AuthenticationException> { call, cause ->
                call.respond(HttpStatusCode.Unauthorized)
            }
            exception<AuthorizationException> { call, cause ->
                call.respond(HttpStatusCode.Forbidden)
            }

        }
    }
}

class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()
