package marinj.feature.account.data.repository

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import marinj.core.model.Failure
import marinj.core.model.buildLeft
import marinj.core.model.buildRight
import marinj.feature.account.data.dao.UsersDao
import marinj.feature.account.domain.model.User
import marinj.feature.account.domain.repository.UserRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

private const val EMAIL = "email"
private const val USER_NAME = "userName"
private const val PASSWORD = "password"
private const val USER_ID = 123

internal class UserRepositoryImplTest {

    private val usersDao: UsersDao = mockk()

    private lateinit var sut: UserRepository

    @BeforeEach
    fun setUp() {
        sut = UserRepositoryImpl(
            usersDao
        )
    }

    @Test
    fun `createUser should return result from userDao createUser`() = runTest {
        val user = User(EMAIL, USER_NAME, PASSWORD)
        val daoResult = USER_ID.buildRight()
        coEvery {
            usersDao.createUser(user)
        } coAnswers { daoResult }

        val result = sut.createUser(user)

        assertThat(result).isEqualTo(daoResult)
    }

    @Test
    fun `createUser should return failure result from userDao createUser`() = runTest {
        val user = User(EMAIL, USER_NAME, PASSWORD)
        val daoResult = Failure.Unknown.buildLeft()
        coEvery {
            usersDao.createUser(user)
        } coAnswers { daoResult }

        val result = sut.createUser(user)

        assertThat(result).isEqualTo(daoResult)
    }
}