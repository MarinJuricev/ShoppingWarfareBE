package marinj.feature.account.domain.usecase

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTCreator
import com.auth0.jwt.algorithms.Algorithm
import com.google.common.truth.Truth
import com.google.common.truth.Truth.*
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import marinj.core.config.JwtConfig
import marinj.core.config.ShoppingWarfareConfig
import marinj.feature.account.domain.model.Token
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

private const val JWT_SECRET = "secret"
private const val UUID = "1824901249081204914"
private const val AUDIENCE = "http://0.0.0.0:8080/hello"
private const val ISSUER = "ktor"
private const val CLAIM = "claim"
private const val USER_ID = 123456
private const val ACCESS_TOKEN_LIFE_TIME = 5L
private const val CURRENT_TIME_IN_MILLIS = 1650232231221L

internal class GenerateTokenTest {

    private val jwtCreator: JWTCreator.Builder = JWT.create()
    private val algorithm: Algorithm = Algorithm.HMAC256(JWT_SECRET)
    private val currentTimeProvider: () -> Long = { CURRENT_TIME_IN_MILLIS }
    private val uuidProvider: () -> String = { UUID }

    private val config: ShoppingWarfareConfig = mockk()
    private val jwtConfig: JwtConfig = mockk()

    private lateinit var sut: GenerateToken

    @BeforeEach
    fun setUp() {
        sut = GenerateToken(
            jwtCreator,
            config,
            algorithm,
            currentTimeProvider,
            uuidProvider
        )

        jwtConfig.apply {
            every { accessTokenLifeTime } answers { ACCESS_TOKEN_LIFE_TIME }
            every { issuer } answers { ISSUER }
            every { audience } answers { AUDIENCE }
            every { claim } answers { CLAIM }
        }
        every { config.jwtConfig } answers { jwtConfig }
    }

    @Test
    fun `invoke SHOULD return a valid token object`() = runTest {
        val result = sut(USER_ID)
        val expectedResult = Token(
            userId = USER_ID,
            accessValue = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJodHRwOi8vMC4wLjAuMDo4MDgwL2hlbGxvIiwiaXNzIjoia3RvciIsImNsYWltIjoxMjM0NTYsImV4cCI6MTY1MDIzMjUzMX0.DOpoVzUQ6YX5C52lp8sTTab0tkN0f3A7YcpW-ZbswIs",
            refreshValue = UUID,
            expiresAt = 1650232531221L
        )

        assertThat(result).isEqualTo(expectedResult)
    }
}