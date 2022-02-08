package marinj.feature.auth.infrastructure.model

import kotlinx.serialization.Serializable

@Serializable
data class ResponseUser(
    val id: String,
    val username: String,
)
