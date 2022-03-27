package marinj.feature.auth.data.dao

import marinj.core.database.dbQuery
import marinj.core.model.Either
import marinj.core.model.Failure
import marinj.core.model.buildLeft
import marinj.core.model.buildRight
import marinj.feature.auth.domain.model.User
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert

interface UsersDao {
    suspend fun createUser(
        email: String,
        userName: String,
        password: String,
    ): Either<Failure, Int>

    suspend fun getUser(id: Int): Either<Failure, User>
}

object Users : Table(), UsersDao {
    val id: Column<Int> = integer("id").autoIncrement()
    val email: Column<String> = varchar("email", 128).uniqueIndex()
    val userName = varchar("user_name", 256)
    val password = varchar("password", 64)

    override val primaryKey = PrimaryKey(id)

    override suspend fun createUser(
        email: String,
        userName: String,
        password: String,
    ): Either<Failure, Int> {
        val userId: Int = dbQuery {
            Users.insert {
                it[this.email] = email
                it[this.userName] = userName
                it[this.password] = password // TODO hash this
            } get UsersDao.id
        }

        return if (userId > 0) {
            userId.buildRight()
        } else {
            Failure.Message("Error while creating userName: $userName").buildLeft()
        }
    }

    override suspend fun getUser(id: Int): Either<Failure, User> {
        val user = dbQuery {
            select {
                UsersDao.id eq id
            }.mapNotNull {
                it.rowToUser()
            }.singleOrNull()
        }

        return user?.buildRight() ?: Failure.Message("Unable to get user with id: $id").buildLeft()
    }

    private fun ResultRow.rowToUser(): User =
        User(
            email = this[usersDao.email],
            userName = this[usersDao.userName],
            password = this[usersDao.password],
        )
}