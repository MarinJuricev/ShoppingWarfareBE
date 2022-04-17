package marinj.feature.account.domain.model

data class Token(
    val userId: Int,
    val accessValue: String,
    val refreshValue: String,
    val expiresAt: Long,
)
