package marinj.feature.account.data.repository

import marinj.core.model.Either
import marinj.core.model.Failure
import marinj.feature.account.data.dao.UsersDao
import marinj.feature.account.domain.model.User
import marinj.feature.account.domain.repository.UserRepository

class UserRepositoryImpl(
    private val usersDao: UsersDao,
) : UserRepository {

    override suspend fun createUser(
        user: User,
    ): Either<Failure, Int> = usersDao.createUser(user)

    override suspend fun getUser(id: Int): Either<Failure, User> =
        usersDao.getUser(id = id)
}