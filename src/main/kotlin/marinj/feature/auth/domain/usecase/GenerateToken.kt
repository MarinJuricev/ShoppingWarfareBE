package marinj.feature.auth.domain.usecase

import com.auth0.jwt.JWTCreator
import com.auth0.jwt.algorithms.Algorithm
import marinj.core.config.ShoppingWarfareConfig
import java.time.Duration
import java.time.Duration.ofMinutes
import java.util.Date

class GenerateToken(
    private val jwtCreator: JWTCreator.Builder,
    private val config: ShoppingWarfareConfig,
    private val algorithm: Algorithm,
) {
    operator fun invoke(
        userId: String
    ): String {
        return with(config.jwtConfig) {
            // In its current state its untestable duo to the time calls
            // if we really want to we can introduce TimeProvider and make this unit testable
            // will try to cover with integration tests tho
            val currentTime = System.currentTimeMillis()
            val expiresAtTimeFrame = Date(
                currentTime.withOffset(ofMinutes(accessTokenLifeTime))
            )

            jwtCreator
                .withIssuer(issuer)
                .withAudience(audience)
                .withClaim(claim, userId)
                .withExpiresAt(expiresAtTimeFrame)
                .sign(algorithm)
        }
    }

    private fun Long.withOffset(offset: Duration) = this + offset.toMillis()
}
