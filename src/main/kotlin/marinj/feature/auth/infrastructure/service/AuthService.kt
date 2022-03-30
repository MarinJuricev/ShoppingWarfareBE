package marinj.feature.auth.infrastructure.service

import marinj.core.model.Either
import marinj.core.model.Failure
import marinj.feature.auth.domain.usecase.CreateUser
import marinj.feature.auth.infrastructure.model.ResponseUser

interface AuthService {
    suspend fun createUser(
        email: String?,
        password: String?,
        userName: String?,
    ): Either<Failure, ResponseUser>
}

class AuthServiceImpl(
    private val createUserUseCase: CreateUser,
) : AuthService {

    override suspend fun createUser(
        email: String?,
        password: String?,
        userName: String?,
    ): Either<Failure, ResponseUser> =
        when (createUserUseCase(email, password, userName)) {
            is Either.Right -> TODO()
            is Either.Left -> TODO()
        }
}
