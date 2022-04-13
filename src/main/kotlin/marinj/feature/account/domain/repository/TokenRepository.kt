package marinj.feature.account.domain.repository

import ch.qos.logback.core.subst.Token
import marinj.core.model.Either
import marinj.core.model.Failure

interface TokenRepository {
    suspend fun generateAccessToken(userId: String): Either<Failure, Token>
    suspend fun generateRefreshToken(userId: String): Either<Failure, Token>
}