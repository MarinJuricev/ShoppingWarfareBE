package marinj.core.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class Response(
    val status: Status,
    @Contextual
    val data: Any? = null,
    val message: String? = null,
)