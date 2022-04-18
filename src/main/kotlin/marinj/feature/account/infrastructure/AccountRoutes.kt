package marinj.feature.account.infrastructure

import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receiveOrNull
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import marinj.core.di.inject
import marinj.feature.account.infrastructure.model.RequestUser
import marinj.feature.account.infrastructure.service.AccountService

fun Application.registerAuthRoutes() {

    val accountService: AccountService by inject()

    routing {
        registerRoute(accountService)
    }
}

fun Route.registerRoute(accountService: AccountService) {

    post("/v1/register") {
        val requestUser = call.receiveOrNull<RequestUser>()
    }

}