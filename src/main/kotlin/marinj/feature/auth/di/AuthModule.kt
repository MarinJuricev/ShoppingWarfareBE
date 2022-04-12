package marinj.feature.auth.di

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTCreator
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import marinj.core.config.ShoppingWarfareConfig
import marinj.feature.auth.data.dao.Users
import marinj.feature.auth.data.dao.UsersDao
import marinj.feature.auth.data.repository.UserRepositoryImpl
import marinj.feature.auth.domain.repository.UserRepository
import marinj.feature.auth.domain.usecase.CreateUser
import marinj.feature.auth.domain.usecase.GenerateToken
import marinj.feature.auth.infrastructure.service.AuthService
import marinj.feature.auth.infrastructure.service.AuthServiceImpl
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