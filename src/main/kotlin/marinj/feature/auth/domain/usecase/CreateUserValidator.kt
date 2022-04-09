package marinj.feature.auth.domain.usecase

import marinj.core.model.Either
import marinj.core.model.Failure
import marinj.core.model.Failure.Message
import marinj.core.model.buildLeft
import marinj.core.model.buildRight
import marinj.feature.auth.domain.model.User

class CreateUserValidator {

    operator fun invoke(
        email: String?,
        userName: String?,
        password: String?,
    ): Either<Failure, User> {
        return when {
            email.isNullOrBlank() -> Message("Email is not optional got: $email").buildLeft()
            userName.isNullOrBlank() -> Message("Username is not optional got: $userName").buildLeft()
            password.isNullOrBlank() -> Message("Password is not optional got: $password").buildLeft()
            else -> User(email, userName, password).buildRight()
        }
    }
}