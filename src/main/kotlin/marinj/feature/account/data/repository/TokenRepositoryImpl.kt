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
        token: Token,
    ): Either<Failure, Token> = tokensDao.saveToken(
        providedToken = token
    )

    override suspend fun getTokenFromAccessToken(
        accessToken: String
    ): Either<Failure, Token> = tokensDao.getTokenFromAccessToken(accessToken)

    override suspend fun getTokenFromRefreshToken(
        refreshToken: String,
    ): Either<Failure, Token> = tokensDao.getTokenFromRefreshToken(refreshToken)

    override suspend fun deleteTokenByUserId(
        userId: Int
    ): Either<Failure, Int> = tokensDao.deleteTokenByUserId(userId)
}