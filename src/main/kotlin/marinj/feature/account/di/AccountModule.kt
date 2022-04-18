package marinj.feature.account.di

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTCreator
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import marinj.core.config.ShoppingWarfareConfig
import marinj.feature.account.data.dao.Tokens
import marinj.feature.account.data.dao.TokensDao
import marinj.feature.account.data.dao.Users
import marinj.feature.account.data.dao.UsersDao
import marinj.feature.account.data.repository.TokenRepositoryImpl
import marinj.feature.account.data.repository.UserRepositoryImpl
import marinj.feature.account.domain.repository.TokenRepository
import marinj.feature.account.domain.repository.UserRepository
import marinj.feature.account.domain.usecase.CreateUser
import marinj.feature.account.domain.usecase.GenerateToken
import marinj.feature.account.infrastructure.service.AccountService
import marinj.feature.account.infrastructure.service.AccountServiceImpl
import org.koin.dsl.module
import java.util.UUID

val authModule = module {
    single<UsersDao> { Users }
    single<TokensDao> { Tokens }

    factory<UserRepository> {
        UserRepositoryImpl(
            usersDao = get(),
        )
    }
    factory<TokenRepository> {
        TokenRepositoryImpl(
            tokensDao = get(),
        )
    }

    factory {
        CreateUser(
            userRepository = get(),
            tokenRepository = get(),
            generateToken = get()
        )
    }
    factory {
        GenerateToken(
            jwtCreator = get(),
            config = get(),
            algorithm = get(),
            //TODO if the need arises to reuse currentTime/uuid provider, then we can move them into separate module or provide them as named dependencies
            currentTimeProvider = { System.currentTimeMillis() },
            uuidProvider = { UUID.randomUUID().toString() }
        )
    }

    factory<JWTVerifier> {
        val config = get<ShoppingWarfareConfig>()
        val algorithm = get<Algorithm>()

        JWT
            .require(algorithm)
            .withIssuer(config.jwtConfig.issuer)
            .withAudience(config.jwtConfig.audience)
            .build()
    }

    factory<JWTCreator.Builder> {
        JWT.create()
    }

    factory<Algorithm> {
        val config = get<ShoppingWarfareConfig>()

        Algorithm.HMAC256(config.jwtConfig.secret)
    }

    factory<AccountService> {
        AccountServiceImpl(
            createUserUseCase = get(),
            jwtVerifier = get(),
        )
    }
}