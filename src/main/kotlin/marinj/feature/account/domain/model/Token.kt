package marinj.feature.account.domain.model

data class Token(
    val accessValue: String,
    val refreshValue: String,
    val expiresAt: Long,
)
