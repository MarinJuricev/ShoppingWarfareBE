package marinj.feature.account.infrastructure.model

import io.ktor.server.auth.Principal
import kotlinx.serialization.Serializable
import marinj.feature.account.domain.model.Token

@Serializable
data class ResponseToken(
    val accessToken: String,
    val refreshToken: String,
) : Principal {
    companion object {
        fun Token.mapFromDomain(): ResponseToken {
            return ResponseToken(
                accessToken = accessValue,
                refreshToken = refreshValue,
            )
        }
    }
}
