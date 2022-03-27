package marinj.core.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import marinj.feature.auth.data.dao.UsersDao
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

interface DatabaseInitializer {
    fun init()
}

class DatabaseInitializerImpl : DatabaseInitializer {

    override fun init() {
        Database.connect(generateHikariDatasource())

        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(
                UsersDao,
            )
        }
    }

    private fun generateHikariDatasource(): HikariDataSource {
        return HikariConfig().apply {
            driverClassName = System.getenv("JDBC_DRIVER")
            jdbcUrl = System.getenv("JDBC_DATABASE_URL")
            maximumPoolSize = 3
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            jdbcUrl = System.getenv("JDBC_DATABASE_URL")
            System.getenv("DB_USER")?.let { envUserName ->
                username = envUserName
            }
            System.getenv("DB_PASSWORD")?.let { envPassword ->
                password = envPassword
            }
            validate()
        }.let { config ->
            HikariDataSource(config)
        }
    }
}

suspend fun <T> dbQuery(block: () -> T): T =
    withContext(Dispatchers.IO) {
        transaction { block() }
    }