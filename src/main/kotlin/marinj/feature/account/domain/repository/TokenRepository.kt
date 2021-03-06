package marinj.feature.account.domain.repository

import marinj.core.model.Either
import marinj.core.model.Failure
import marinj.feature.account.domain.model.Token

interface TokenRepository {
    suspend fun saveToken(token: Token): Either<Failure, Token>

    suspend fun getTokenFromAccessToken(accessToken: String): Either<Failure, Token>

    suspend fun getTokenFromRefreshToken(refreshToken: String): Either<Failure, Token>

    suspend fun deleteTokenByUserId(userId: Int): Either<Failure, Int>
}