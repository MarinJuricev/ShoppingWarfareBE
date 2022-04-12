package marinj.feature.auth.domain.repository

import marinj.core.model.Either
import marinj.core.model.Failure
import marinj.feature.auth.domain.model.User

interface UserRepository {
    suspend fun createUser(user: User): Either<Failure, Int>
    suspend fun getUser(id: Int): Either<Failure, User>
}