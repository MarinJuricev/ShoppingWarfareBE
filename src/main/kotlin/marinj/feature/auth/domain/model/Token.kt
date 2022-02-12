package marinj.feature.auth.domain.model

data class Token(
    val value: String,
    val refreshValue: String,
    val expiresAt: Long,
)
