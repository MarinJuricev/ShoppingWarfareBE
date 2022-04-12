package marinj.core.config

import io.ktor.server.config.ApplicationConfig

private const val JWT_SECRET = "jwt.secret"
private const val JWT_ISSUER = "jwt.issuer"
private const val JWT_AUDIENCE = "jwt.audience"
private const val JWT_REALM = "jwt.realm"
private const val JWT_CLAIM = "jwt.claim"
private const val JWT_ACCESS_LIFETIME = "jwt.access.lifetime"
private const val JWT_REFRESH_LIFETIME = "jwt.refresh.lifetime"

data class ShoppingWarfareConfig(
    val jwtConfig: JwtConfig
)

data class JwtConfig(
    val secret: String,
    val issuer: String,
    val audience: String,
    val realm: String,
    val claim: String,
    val accessTokenLifeTime: Long,
    val refreshTokenLifeTime: Long,
)

fun buildJwtConfig(applicationConfig: ApplicationConfig): JwtConfig =
    JwtConfig(
        secret = applicationConfig.property(JWT_SECRET).getString(),
        issuer = applicationConfig.property(JWT_ISSUER).getString(),
        audience = applicationConfig.property(JWT_AUDIENCE).getString(),
        realm = applicationConfig.property(JWT_REALM).getString(),
        claim = applicationConfig.property(JWT_CLAIM).getString(),
        accessTokenLifeTime = applicationConfig.property(JWT_ACCESS_LIFETIME).getString().toLong(),
        refreshTokenLifeTime = applicationConfig.property(JWT_REFRESH_LIFETIME).getString().toLong(),
    )

