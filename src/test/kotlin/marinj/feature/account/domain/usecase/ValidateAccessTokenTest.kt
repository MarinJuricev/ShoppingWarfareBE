package marinj.feature.account.domain.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import marinj.core.model.buildRight
import marinj.feature.account.domain.model.Token
import marinj.feature.account.domain.repository.TokenRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

private const val ACCESS_TOKEN = "accessToken"

internal class ValidateAccessTokenTest {

    private val tokenRepository: TokenRepository = mockk()

    private lateinit var sut: ValidateAccessToken

    @BeforeEach
    fun setUp() {
        sut = ValidateAccessToken(
            tokenRepository
        )
    }

    @Test
    fun `invoke SHOULD return result from getTokenFromAccessToken`() = runTest {
        val repositoryResult = mockk<Token>().buildRight()
        coEvery {
            tokenRepository.getTokenFromAccessToken(ACCESS_TOKEN)
        } coAnswers { repositoryResult }

        val result = sut(ACCESS_TOKEN)

        assertThat(result).isEqualTo(repositoryResult)
    }
}