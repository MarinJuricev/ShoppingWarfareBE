package marinj.feature.auth.domain.usecase

import marinj.core.model.Either
import marinj.core.model.Failure
import marinj.feature.auth.domain.repository.AuthRepository

class CreateUser(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(
        email: String,
        password: String,
    ): Either<Failure, Unit> = authRepository.createUser(email, password)
}