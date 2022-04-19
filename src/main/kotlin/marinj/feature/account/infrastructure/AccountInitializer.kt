package marinj.feature.account.infrastructure

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.jwt
import marinj.core.config.ShoppingWarfareConfig
import marinj.core.di.inject
import marinj.core.model.Either.Left
import marinj.core.model.Either.Right
import marinj.feature.account.infrastructure.service.AccountService

fun Application.installAuth() {

    val accountService: AccountService by inject()
    val config: ShoppingWarfareConfig by inject()

    install(Authentication) {
        jwt {
            realm = config.jwtConfig.secret
            verifier(accountService.jwtVerifier)
            validate { credential ->
                val accessToken = credential.payload.getClaim(config.jwtConfig.claim).toString()

                when (val result = accountService.validateAccessToken(accessToken)) {
                    is Right -> result.value
                    is Left -> null
                }
            }
        }
    }
}