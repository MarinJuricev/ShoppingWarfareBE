package marinj.feature.account.infrastructure.service

import marinj.core.model.Either
import marinj.core.model.Either.*
import marinj.core.model.Failure
import marinj.feature.account.domain.usecase.CreateUser
import marinj.feature.account.infrastructure.model.ResponseToken

interface AccountService {
    suspend fun createUser(
        email: String?,
        password: String?,
        userName: String?,
    ): Either<Failure, ResponseToken>

    suspend fun validateAccessToken(
        accessToken: String
    ): Either<Failure, ResponseToken>
}

class AccountServiceImpl(
    private val createUserUseCase: CreateUser,
) : AccountService {

    override suspend fun createUser(
        email: String?,
        password: String?,
        userName: String?,
    ): Either<Failure, ResponseToken> =
        when (val result = createUserUseCase(email, password, userName)) {
            is Right -> TODO()
            is Left -> result
        }

    override suspend fun validateAccessToken(
        accessToken: String
    ): Either<Failure, ResponseToken> {
        TODO("Not yet implemented")
    }
}
