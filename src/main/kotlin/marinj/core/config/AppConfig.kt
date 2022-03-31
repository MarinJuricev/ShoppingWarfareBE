package marinj.core.config

import io.ktor.server.application.Application
import marinj.core.di.inject

private const val JWT_SECRET = "jwt.secret"
private const val JWT_ISSUER = "jwt.issuer"
private const val JWT_AUDIENCE = "jwt.audience"
private const val JWT_REALM = "jwt.realm"

// Hacky way to provide the config with Koin, set it as lateinit and initialize
// later, again a hacky approach.
class AppConfig {
    lateinit var jwtConfig: JwtConfig
}

data class JwtConfig(
    val secret: String,
    val issuer: String,
    val audience: String,
    val realm: String,
)

fun Application.initConfig() {
    val config by inject<AppConfig>()
    config.apply {
        jwtConfig = JwtConfig(
            secret = environment.config.property(JWT_SECRET).getString(),
            issuer = environment.config.property(JWT_ISSUER).getString(),
            audience = environment.config.property(JWT_AUDIENCE).getString(),
            realm = environment.config.property(JWT_REALM).getString(),
        )
    }
}
