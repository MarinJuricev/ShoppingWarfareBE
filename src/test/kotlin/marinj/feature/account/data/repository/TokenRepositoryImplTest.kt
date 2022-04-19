package marinj.feature.account.data.repository

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import marinj.core.model.Failure
import marinj.core.model.buildLeft
import marinj.feature.account.data.dao.TokensDao
import marinj.feature.account.domain.model.Token
import marinj.feature.account.domain.repository.TokenRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

private const val USER_ID = 1
private const val ACCESS_TOKEN = "access_token"
private const val REFRESH_TOKEN = "refresh_token"
private const val EXPIRES_AT = 1253489253L

internal class TokenRepositoryImplTest {

    private val tokensDao: TokensDao = mockk()

    private lateinit var sut: TokenRepository

    @BeforeEach
    fun setUp() {
        sut = TokenRepositoryImpl(
            tokensDao
        )
    }

    @Test
    fun `saveToken SHOULD return result from tokensDao saveToken`() = runTest {
        val daoResult = Failure.Unknown.buildLeft()
        val token = Token(
            userId = USER_ID,
            accessValue = ACCESS_TOKEN,
            refreshValue = REFRESH_TOKEN,
            expiresAt = EXPIRES_AT,
        )

        coEvery {
            tokensDao.saveToken(token)
        } coAnswers { daoResult }

        val result = sut.saveToken(token)

        assertThat(result).isEqualTo(daoResult)
    }

    @Test
    fun `getTokenFromAccessToken SHOULD return result from tokensDao getTokenFromAccessToken`() = runTest {
        val daoResult = Failure.Unknown.buildLeft()
        coEvery {
            tokensDao.getTokenFromAccessToken(ACCESS_TOKEN)
        } coAnswers { daoResult }

        val result = sut.getTokenFromAccessToken(ACCESS_TOKEN)

        assertThat(result).isEqualTo(daoResult)
    }

    @Test
    fun `getTokenFromRefreshToken SHOULD return result from tokensDao getTokenFromRefreshToken`() = runTest {
        val daoResult = Failure.Unknown.buildLeft()
        coEvery {
            tokensDao.getTokenFromRefreshToken(REFRESH_TOKEN)
        } coAnswers { daoResult }

        val result = sut.getTokenFromRefreshToken(REFRESH_TOKEN)

        assertThat(result).isEqualTo(daoResult)
    }

    @Test
    fun `deleteTokenByUserId SHOULD return result from tokensDao deleteTokenByUserId`() = runTest {
        val daoResult = Failure.Unknown.buildLeft()
        coEvery {
            tokensDao.deleteTokenByUserId(USER_ID)
        } coAnswers { daoResult }

        val result = sut.deleteTokenByUserId(USER_ID)

        assertThat(result).isEqualTo(daoResult)
    }
}