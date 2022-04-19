package marinj.feature.account.data.dao

import marinj.core.database.dbQuery
import marinj.core.model.Either
import marinj.core.model.Failure
import marinj.core.model.Failure.Message
import marinj.core.model.buildLeft
import marinj.core.model.buildRight
import marinj.feature.account.domain.model.Token
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

interface TokensDao {
    suspend fun saveToken(
        providedToken: Token
    ): Either<Failure, Token>

    suspend fun getTokenFromAccessToken(accessToken: String): Either<Failure, Token>
    suspend fun getTokenFromRefreshToken(refreshToken: String): Either<Failure, Token>
    suspend fun deleteTokenByUserId(userId: Int): Either<Failure, Int>
}

object Tokens : Table(), TokensDao {
    private val id: Column<Int> = integer("id").autoIncrement()
    private val userId: Column<Int> = integer("user_id") references Users.id
    private val accessToken = varchar("access_token", 300)
    private val refreshToken = varchar("refresh_token", 300)
    private val expiresAt = long("expires_at")

    override val primaryKey = PrimaryKey(id)

    override suspend fun saveToken(
        providedToken: Token
    ): Either<Failure, Token> {
        val token = dbQuery {
            Tokens.insert { statement ->
                statement[userId] = providedToken.userId
                statement[accessToken] = providedToken.accessValue
                statement[refreshToken] = providedToken.refreshValue
                statement[expiresAt] = providedToken.expiresAt
            }
        }.resultedValues?.firstOrNull()?.rowToToken()


        // Maybe leaking some data which I don't want to return ??? Maybe this is more suited for
        // logging rather than in the response
        return token?.buildRight() ?: Message(
            """
            Error while creating token got:
            userId: $userId
            accessToken: $accessToken
            refreshToken: $refreshToken
            expiresAt: $expiresAt
        """.trimIndent()
        ).buildLeft()
    }

    override suspend fun getTokenFromAccessToken(
        accessToken: String
    ): Either<Failure, Token> {
        val token = dbQuery {
            select {
                Tokens.accessToken eq accessToken
            }.mapNotNull { resultRow ->
                resultRow.rowToToken()
            }.singleOrNull()
        }

        return token?.buildRight() ?: Message(
            "Unable to get token with accessToken: $accessToken"
        ).buildLeft()
    }

    override suspend fun getTokenFromRefreshToken(
        refreshToken: String,
    ): Either<Failure, Token> {
        val token = dbQuery {
            select {
                Tokens.refreshToken eq refreshToken
            }.mapNotNull { resultRow ->
                resultRow.rowToToken()
            }.singleOrNull()
        }

        return token?.buildRight() ?: Message(
            "Unable to get token with refreshToken: $refreshToken"
        ).buildLeft()
    }

    override suspend fun deleteTokenByUserId(
        userId: Int,
    ): Either<Failure, Int> {
        val deleteResult: Int = dbQuery {
            deleteWhere { Tokens.userId eq userId }
        }

        return if (deleteResult > 0) {
            deleteResult.buildRight()
        } else {
            Message("Unable to delete token with id: $userId").buildLeft()
        }
    }

    private fun ResultRow.rowToToken(): Token =
        Token(
            userId = this[userId],
            accessValue = this[accessToken],
            refreshValue = this[refreshToken],
            expiresAt = this[expiresAt],
        )
}