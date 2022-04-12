package marinj.feature.auth.domain.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import marinj.core.model.Failure
import marinj.core.model.buildLeft
import marinj.feature.auth.domain.model.User
import marinj.feature.auth.domain.repository.UserRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

private const val EMAIL = "email"
private const val USER_NAME = "username"
private const val PASSWORD = "password"

internal class CreateUserTest {

    private val userRepository: UserRepository = mockk()

    private lateinit var sut: CreateUser

    @BeforeEach
    fun setUp() {
        sut = CreateUser(
            userRepository
        )
    }

    @Test
    fun `createUser should return FailureErrorMessage when email is null`() = runTest {
        val result = sut(
            email = null,
            userName = USER_NAME,
            password = PASSWORD,
        )
        val expectedResult = Failure.Message("Email is not optional got: null").buildLeft()

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `createUser should return FailureErrorMessage when email is blank`() = runTest {
        val result = sut(
            email = "",
            userName = USER_NAME,
            password = PASSWORD,
        )
        val expectedResult = Failure.Message("Email is not optional got: ").buildLeft()

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `createUser should return FailureErrorMessage when userName is null`() = runTest {
        val result = sut(
            email = EMAIL,
            userName = null,
            password = PASSWORD,
        )
        val expectedResult = Failure.Message("Username is not optional got: null").buildLeft()

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `createUser should return FailureErrorMessage when userName is blank`() = runTest {
        val result = sut(
            email = EMAIL,
            userName = "",
            password = PASSWORD,
        )
        val expectedResult = Failure.Message("Username is not optional got: ").buildLeft()

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `createUser should return FailureErrorMessage when password is null`() = runTest {
        val result = sut(
            email = EMAIL,
            userName = USER_NAME,
            password = null,
        )
        val expectedResult = Failure.Message("Password is not optional got: null").buildLeft()

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `createUser should return FailureErrorMessage when password is blank`() = runTest {
        val result = sut(
            email = EMAIL,
            userName = USER_NAME,
            password = "",
        )
        val expectedResult = Failure.Message("Password is not optional got: ").buildLeft()

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `createUser should return result from authRepository when validation passes`() = runTest {
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
}