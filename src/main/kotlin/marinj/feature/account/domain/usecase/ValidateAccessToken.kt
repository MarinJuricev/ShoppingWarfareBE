package marinj.feature.account.domain.usecase

import marinj.core.model.Either
import marinj.core.model.Failure
import marinj.feature.account.domain.model.Token
import marinj.feature.account.domain.repository.TokenRepository

class ValidateAccessToken(
    private val tokenRepository: TokenRepository
) {

    suspend operator fun invoke(
        accessToken: String
    ): Either<Failure, Token> =
        tokenRepository.getTokenFromAccessToken(accessToken)
}