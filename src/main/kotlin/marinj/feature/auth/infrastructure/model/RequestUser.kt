package marinj.feature.auth.infrastructure.model

import kotlinx.serialization.Serializable

@Serializable
data class RequestUser(
    val userName: String,
    val password: String,
)
