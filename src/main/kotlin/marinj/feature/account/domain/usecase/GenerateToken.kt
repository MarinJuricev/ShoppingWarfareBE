package marinj.feature.account.domain.usecase

import com.auth0.jwt.JWTCreator
import com.auth0.jwt.algorithms.Algorithm
import marinj.core.config.ShoppingWarfareConfig
import marinj.feature.account.domain.model.Token
import java.time.Duration
import java.time.Duration.ofMinutes
import java.util.Date

class GenerateToken(
    private val jwtCreator: JWTCreator.Builder,
    private val config: ShoppingWarfareConfig,
    private val algorithm: Algorithm,
    private val currentTimeProvider: () -> Long,
    private val uuidProvider: () -> String,
) {
    operator fun invoke(
        userId: Int
    ): Token {
        return with(config.jwtConfig) {
            val currentTime = currentTimeProvider()
            val expiresAtTimeFrame = Date(
                currentTime.withOffset(ofMinutes(accessTokenLifeTime))
            )

            val accessToken = jwtCreator
                .withIssuer(issuer)
                .withAudience(audience)
                .withClaim(claim, userId)
                .withExpiresAt(expiresAtTimeFrame)
                .sign(algorithm)

            Token(
                userId = userId,
                accessValue = accessToken,
                refreshValue = uuidProvider(),
                expiresAt = expiresAtTimeFrame.time
            )
        }
    }

    private fun Long.withOffset(offset: Duration) = this + offset.toMillis()
}
