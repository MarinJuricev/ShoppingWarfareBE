package marinj.feature.auth.data.repository

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import marinj.core.model.Failure
import marinj.core.model.buildLeft
import marinj.core.model.buildRight
import marinj.feature.auth.data.dao.UsersDao
import marinj.feature.auth.domain.repository.AuthRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

private const val EMAIL = "email"
private const val USER_NAME = "userName"
private const val PASSWORD = "password"
private const val USER_ID = 123

internal class AuthRepositoryImplTest {

    private val usersDao: UsersDao = mockk()

    private lateinit var sut: AuthRepository

    @BeforeEach
    fun setUp() {
        sut = AuthRepositoryImpl(
            usersDao
        )
    }

    @Test
    fun `createUser should return result from userDao createUser`() = runTest {
        val daoResult = USER_ID.buildRight()
        coEvery {
            usersDao.createUser(EMAIL, USER_NAME, PASSWORD)
        } coAnswers { daoResult }

        val result = sut.createUser(
            email = EMAIL,
            userName = USER_NAME,
            password = PASSWORD,
        )

        assertThat(result).isEqualTo(daoResult)
    }

    @Test
    fun `createUser should return failure result from userDao createUser`() = runTest {
        val daoResult = Failure.Unknown.buildLeft()
        coEvery {
            usersDao.createUser(EMAIL, USER_NAME, PASSWORD)
        } coAnswers { daoResult }

        val result = sut.createUser(
            email = EMAIL,
            userName = USER_NAME,
            password = PASSWORD,
        )

        assertThat(result).isEqualTo(daoResult)
    }
}