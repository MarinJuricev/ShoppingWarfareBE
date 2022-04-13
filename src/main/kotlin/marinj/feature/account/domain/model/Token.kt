package marinj.feature.account.domain.model

data class Token(
    val value: String,
    val refreshValue: String,
    val expiresAt: Long,
)
