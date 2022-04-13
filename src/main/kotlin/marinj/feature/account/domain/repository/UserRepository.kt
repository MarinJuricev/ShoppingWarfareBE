package marinj.feature.account.domain.repository

import marinj.core.model.Either
import marinj.core.model.Failure
import marinj.feature.account.domain.model.User

interface UserRepository {
    suspend fun createUser(user: User): Either<Failure, Int>
    suspend fun getUser(id: Int): Either<Failure, User>
}