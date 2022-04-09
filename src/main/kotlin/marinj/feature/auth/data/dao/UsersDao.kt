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
import org.jetbrains.exposed.sql.select

interface UsersDao {
    suspend fun createUser(user: User): Either<Failure, Int>

    suspend fun getUser(id: Int): Either<Failure, User>
}

object Users : Table(), UsersDao {
    private val id: Column<Int> = integer("id").autoIncrement()
    private val email: Column<String> = varchar("email", 128).uniqueIndex()
    private val userName = varchar("user_name", 256)
    private val password = varchar("password", 64)

    override val primaryKey = PrimaryKey(id)

    override suspend fun createUser(
        user: User,
    ): Either<Failure, Int> {
        val userId: Int = dbQuery {
            Users.insert {
                it[this.email] = user.email
                it[this.userName] = user.userName
                it[this.password] = user.password // TODO hash this in another layer, not here.
            } get id
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
                Users.id eq id
            }.mapNotNull {
                it.rowToUser()
            }.singleOrNull()
        }

        return user?.buildRight() ?: Failure.Message("Unable to get user with id: $id").buildLeft()
    }

    private fun ResultRow.rowToUser(): User =
        User(
            email = this[email],
            userName = this[userName],
            password = this[password],
        )
}