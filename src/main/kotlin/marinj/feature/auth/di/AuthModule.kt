package marinj.feature.auth.di

import marinj.feature.auth.data.dao.Users
import marinj.feature.auth.data.dao.UsersDao
import marinj.feature.auth.data.repository.AuthRepositoryImpl
import marinj.feature.auth.domain.repository.AuthRepository
import marinj.feature.auth.domain.usecase.CreateUser
import marinj.feature.auth.infrastructure.service.AuthService
import marinj.feature.auth.infrastructure.service.AuthServiceImpl
import org.koin.dsl.module

val authModule = module {
    single<UsersDao> { Users }
    factory<AuthRepository> {
        AuthRepositoryImpl(
            usersDao = get(),
        )
    }
    factory {
        CreateUser(
            authRepository = get(),
        )
    }

    factory<AuthService> {
        AuthServiceImpl(
            createUserUseCase = get(),
        )
    }
}