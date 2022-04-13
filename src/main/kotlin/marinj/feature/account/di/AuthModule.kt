package marinj.feature.account.di

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTCreator
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import marinj.core.config.ShoppingWarfareConfig
import marinj.feature.account.data.dao.Users
import marinj.feature.account.data.dao.UsersDao
import marinj.feature.account.data.repository.UserRepositoryImpl
import marinj.feature.account.domain.repository.UserRepository
import marinj.feature.account.domain.usecase.CreateUser
import marinj.feature.account.domain.usecase.GenerateToken
import marinj.feature.account.infrastructure.service.AuthService
import marinj.feature.account.infrastructure.service.AuthServiceImpl
import org.koin.dsl.module

val authModule = module {
    single<UsersDao> { Users }
    factory<UserRepository> {
        UserRepositoryImpl(
            usersDao = get(),
        )
    }
    factory {
        CreateUser(
            userRepository = get(),
        )
    }
    factory {
        GenerateToken(
            jwtCreator = get(),
            config = get(),
            algorithm = get()
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

    factory<AuthService> {
        AuthServiceImpl(
            createUserUseCase = get(),
        )
    }
}