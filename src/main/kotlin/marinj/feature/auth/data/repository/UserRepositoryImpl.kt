package marinj.feature.auth.data.repository

import marinj.core.model.Either
import marinj.core.model.Failure
import marinj.feature.auth.data.dao.UsersDao
import marinj.feature.auth.domain.model.User
import marinj.feature.auth.domain.repository.UserRepository

class UserRepositoryImpl(
    private val usersDao: UsersDao,
) : UserRepository {

    override suspend fun createUser(
        user: User,
    ): Either<Failure, Int> = usersDao.createUser(user)

    override suspend fun getUser(id: Int): Either<Failure, User> =
        usersDao.getUser(id = id)
}