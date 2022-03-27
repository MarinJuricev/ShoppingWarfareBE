package marinj.feature.auth.data.repository

import marinj.core.model.Either
import marinj.core.model.Failure
import marinj.feature.auth.data.dao.UsersDao
import marinj.feature.auth.domain.model.Token
import marinj.feature.auth.domain.model.User
import marinj.feature.auth.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val usersDao: UsersDao
) : AuthRepository {

    override suspend fun createUser(
        email: String,
        userName: String,
        password: String,
    ): Either<Failure, Int> {
        TODO("Not yet implemented")
    }

    override suspend fun getUser(id: Int): Either<Failure, User> {
        TODO("Not yet implemented")
    }


    override suspend fun generateToken(): Either<Failure, Token> {
        TODO("Not yet implemented")
    }

    override suspend fun refreshToken(): Either<Failure, Token> {
        TODO("Not yet implemented")
    }
}