package marinj.feature.account.domain.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import marinj.core.model.Failure
import marinj.core.model.buildLeft
import marinj.core.model.buildRight
import marinj.feature.account.domain.model.Token
import marinj.feature.account.domain.model.User
import marinj.feature.account.domain.repository.TokenRepository
import marinj.feature.account.domain.repository.UserRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

private const val EMAIL = "email"
private const val USER_NAME = "username"
private const val PASSWORD = "password"
private const val USER_ID = 123

internal class CreateUserTest {

    private val userRepository: UserRepository = mockk()
    private val tokenRepository: TokenRepository = mockk()
    private val generateToken: GenerateToken = mockk()
    private val token: Token = mockk()

    private lateinit var sut: CreateUser

    @BeforeEach
    fun setUp() {
        sut = CreateUser(
            userRepository,
            tokenRepository,
            generateToken,
        )
    }

    @Test
    fun `createUser SHOULD return FailureErrorMessage WHEN email is null`() = runTest {
        val result = sut(
            email = null,
            userName = USER_NAME,
            password = PASSWORD,
        )
        val expectedResult = Failure.Message("Email is not optional got: null").buildLeft()

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `createUser SHOULD return FailureErrorMessage WHEN email is blank`() = runTest {
        val result = sut(
            email = "",
            userName = USER_NAME,
            password = PASSWORD,
        )
        val expectedResult = Failure.Message("Email is not optional got: ").buildLeft()

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `createUser SHOULD return FailureErrorMessage WHEN userName is null`() = runTest {
        val result = sut(
            email = EMAIL,
            userName = null,
            password = PASSWORD,
        )
        val expectedResult = Failure.Message("Username is not optional got: null").buildLeft()

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `createUser SHOULD return FailureErrorMessage WHEN userName is blank`() = runTest {
        val result = sut(
            email = EMAIL,
            userName = "",
            password = PASSWORD,
        )
        val expectedResult = Failure.Message("Username is not optional got: ").buildLeft()

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `createUser SHOULD return FailureErrorMessage WHEN password is null`() = runTest {
        val result = sut(
            email = EMAIL,
            userName = USER_NAME,
            password = null,
        )
        val expectedResult = Failure.Message("Password is not optional got: null").buildLeft()

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `createUser SHOULD return FailureErrorMessage WHEN password is blank`() = runTest {
        val result = sut(
            email = EMAIL,
            userName = USER_NAME,
            password = "",
        )
        val expectedResult = Failure.Message("Password is not optional got: ").buildLeft()

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `createUser SHOULD return result from authRepository WHEN validation passes`() = runTest {
        val expectedUser = User(
            email = EMAIL,
            userName = USER_NAME,
            password = PASSWORD,
        )
        val repositoryResult = Failure.Unknown.buildLeft()
        coEvery {
            userRepository.createUser(expectedUser)
        } coAnswers { repositoryResult }

        val result = sut(
            email = EMAIL,
            userName = USER_NAME,
            password = PASSWORD,
        )

        assertThat(result).isEqualTo(repositoryResult)
    }

    @Test
    fun `createUser SHOULD return tokenRepository saveToken result WHEN createUser is EitherRight`() = runTest {
        val expectedUser = User(
            email = EMAIL,
            userName = USER_NAME,
            password = PASSWORD,
        )
        val userRepositoryResult = USER_ID.buildRight()
        val tokenRepositoryResult = token.buildRight()
        coEvery {
            userRepository.createUser(expectedUser)
        } coAnswers { userRepositoryResult }
        coEvery {
            generateToken(USER_ID)
        } coAnswers { token }
        coEvery {
            tokenRepository.saveToken(token)
        } coAnswers { tokenRepositoryResult }

        val result = sut(
            email = EMAIL,
            userName = USER_NAME,
            password = PASSWORD,
        )

        assertThat(result).isEqualTo(tokenRepositoryResult)
    }
}