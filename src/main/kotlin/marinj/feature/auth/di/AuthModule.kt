package marinj.feature.auth.di

import marinj.feature.auth.data.dao.Users
import marinj.feature.auth.data.dao.UsersDao
import marinj.feature.auth.data.repository.UserRepositoryImpl
import marinj.feature.auth.domain.repository.UserRepository
import marinj.feature.auth.domain.usecase.CreateUser
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

    factory<AuthService> {
        AuthServiceImpl(
            createUserUseCase = get(),
        )
    }
}