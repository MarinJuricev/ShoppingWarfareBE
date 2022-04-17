package marinj.feature.account.domain.usecase

import marinj.core.model.Either
import marinj.core.model.Either.Left
import marinj.core.model.Either.Right
import marinj.core.model.Failure
import marinj.core.model.buildLeft
import marinj.core.model.buildRight
import marinj.feature.account.domain.model.Token
import marinj.feature.account.domain.model.User
import marinj.feature.account.domain.repository.TokenRepository
import marinj.feature.account.domain.repository.UserRepository

class CreateUser(
    private val userRepository: UserRepository,
    private val tokenRepository: TokenRepository,
    private val generateToken: GenerateToken,
) {

    suspend operator fun invoke(
        email: String?,
        userName: String?,
        password: String?,
    ): Either<Failure, Token> {
        val validationResult = when {
            email.isNullOrBlank() -> Failure.Message("Email is not optional got: $email").buildLeft()
            userName.isNullOrBlank() -> Failure.Message("Username is not optional got: $userName").buildLeft()
            password.isNullOrBlank() -> Failure.Message("Password is not optional got: $password").buildLeft()
            else -> User(email, userName, password).buildRight()
        }

        val userResult = when (validationResult) {
            is Right -> userRepository.createUser(validationResult.value)
            is Left -> validationResult.error.buildLeft()
        }

        return when (userResult) {
            is Right -> {
                generateToken(userResult.value).let {token ->
                    tokenRepository.saveToken(token)
                }
            }
            is Left -> userResult
        }
    }
}