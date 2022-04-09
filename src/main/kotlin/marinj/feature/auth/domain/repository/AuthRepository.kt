package marinj.feature.auth.domain.repository

import marinj.core.model.Either
import marinj.core.model.Failure
import marinj.feature.auth.domain.model.Token
import marinj.feature.auth.domain.model.User

interface AuthRepository {
    suspend fun createUser(user: User): Either<Failure, Int>
    suspend fun getUser(id: Int): Either<Failure, User>
    suspend fun generateToken(): Either<Failure, Token>
    suspend fun refreshToken(): Either<Failure, Token>
}