package marinj.feature.account.infrastructure

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import marinj.core.config.ShoppingWarfareConfig
import marinj.core.di.inject
import marinj.feature.account.infrastructure.service.AuthService

fun Application.installAuth() {

    val authService: AuthService by inject()
    val config: ShoppingWarfareConfig by inject()

    install(Authentication) {
        jwt {
            realm = config.jwtConfig.secret
            verifier(
                JWT
                    .require(Algorithm.HMAC256(config.jwtConfig.secret))
                    .withIssuer(config.jwtConfig.issuer)
                    .withAudience(config.jwtConfig.audience)
                    .build()
            )
            validate { credential ->
                //TODO actually configure this to get the userId from the service
                if (credential.payload.getClaim("id").asString() != "") {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
//            validate { // 3
//                val payload = it.payload
//                val claim = payload.getClaim("id")
//                val claimString = claim.asInt()
//                val user = db.findUser(claimString) // 4
//                user
//            }
        }
    }

}