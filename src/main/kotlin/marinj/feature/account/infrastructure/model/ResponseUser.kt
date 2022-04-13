package marinj.feature.account.infrastructure.model

import io.ktor.server.auth.Principal
import kotlinx.serialization.Serializable

@Serializable
data class ResponseUser(
    val username: String,
    val token: String,
) : Principal
