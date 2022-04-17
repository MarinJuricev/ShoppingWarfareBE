package marinj.feature.account.data.repository

import marinj.core.model.Either
import marinj.core.model.Failure
import marinj.feature.account.data.dao.TokensDao
import marinj.feature.account.domain.model.Token
import marinj.feature.account.domain.repository.TokenRepository

class TokenRepositoryImpl(
    private val tokensDao: TokensDao,
) : TokenRepository {
    override suspend fun saveToken(
        userId: Int,
        accessToken: String,
        refreshToken: String,
        expiresAt: Long,
    ): Either<Failure, Token> = tokensDao.saveToken(
        userId = userId,
        accessToken = accessToken,
        refreshToken = refreshToken,
        expiresAt = expiresAt,
    )

    override suspend fun getTokenFromRefreshToken(
        refreshToken: String,
    ): Either<Failure, Token> = tokensDao.getTokenFromRefreshToken(refreshToken)

    override suspend fun deleteTokenByUserId(
        userId: Int
    ): Either<Failure, Int> = tokensDao.deleteTokenByUserId(userId)
}