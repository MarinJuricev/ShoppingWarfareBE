package marinj.feature.account.infrastructure.model

import com.google.common.truth.Truth.assertThat
import marinj.feature.account.domain.model.Token
import marinj.feature.account.infrastructure.model.ResponseToken.Companion.mapFromDomain
import org.junit.jupiter.api.Test

private const val USER_ID = 123
private const val EXPIRES_AT = 12345678L
private const val ACCESS_TOKEN = "accessToken"
private const val REFRESH_TOKEN = "refreshToken"

internal class ResponseTokenTest {

    private val origin = Token(
        userId = USER_ID,
        accessValue = ACCESS_TOKEN,
        refreshValue = REFRESH_TOKEN,
        expiresAt = EXPIRES_AT,
    )

    @Test
    fun `mapFromDomain SHOULD map accessToken from Token`() {
        val result = origin.mapFromDomain()

        assertThat(result.accessToken).isEqualTo(ACCESS_TOKEN)
    }

    @Test
    fun `mapFromDomain SHOULD map refreshToken from Token`() {
        val result = origin.mapFromDomain()

        assertThat(result.refreshToken).isEqualTo(REFRESH_TOKEN)
    }
}