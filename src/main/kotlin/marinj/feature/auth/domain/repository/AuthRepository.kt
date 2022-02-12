package marinj.feature.auth.domain.repository

import marinj.core.model.Either
import marinj.core.model.Failure
import marinj.feature.auth.domain.model.Token

interface AuthRepository {
    suspend fun createUser(email: String, password: String): Either<Failure, Unit>
    suspend fun getUser(email: String, password: String): Either<Failure, Unit>
    suspend fun generateToken(): Either<Failure, Token>
    suspend fun refreshToken(): Either<Failure, Token>
}