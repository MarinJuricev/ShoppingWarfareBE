package marinj.feature.account.data.repository

import ch.qos.logback.core.subst.Token
import marinj.core.model.Either
import marinj.core.model.Failure
import marinj.feature.account.data.dao.UsersDao
import marinj.feature.account.domain.repository.TokenRepository

class TokenRepositoryImpl(
    private val tokensDao: TokensDao,
) : TokenRepository {
    override suspend fun generateAccessToken(
        userId: String,
    ): Either<Failure, Token> {
        TODO("Not yet implemented")
    }

    override suspend fun generateRefreshToken(
        userId: String,
    ): Either<Failure, Token> {
        TODO("Not yet implemented")
    }
}