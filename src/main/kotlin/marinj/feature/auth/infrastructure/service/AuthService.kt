package marinj.feature.auth.infrastructure.service

import marinj.core.model.Either
import marinj.core.model.Failure
import marinj.feature.auth.infrastructure.model.ResponseUser

interface AuthService {
    suspend fun createUser(
        id: String?,
        username: String?
    ): Either<Failure, ResponseUser>
}