package marinj.feature.account.infrastructure.model

import io.ktor.server.auth.Principal
import kotlinx.serialization.Serializable

@Serializable
data class ResponseToken(
    val accessToken: String,
    val refreshToken: String,
): Principal
