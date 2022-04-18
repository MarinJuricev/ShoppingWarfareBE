package marinj.feature.account.infrastructure

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import marinj.core.config.ShoppingWarfareConfig
import marinj.core.di.inject
import marinj.feature.account.infrastructure.service.AccountService

fun Application.installAuth() {

    val accountService: AccountService by inject()
    val config: ShoppingWarfareConfig by inject()

    install(Authentication) {
        jwt {
            realm = config.jwtConfig.secret
            verifier(accountService.jwtVerifier)
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