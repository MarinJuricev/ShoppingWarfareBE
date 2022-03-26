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
            SchemaUtils.create(UsersDao)
        }
    }

    private fun generateHikariDatasource(): HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = System.getenv("JDBC_DRIVER")
        config.jdbcUrl = System.getenv("JDBC_DATABASE_URL")
        config.maximumPoolSize = 3
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.jdbcUrl = System.getenv("JDBC_DATABASE_URL")
        System.getenv("DB_USER")?.let { username ->
            config.username = username
        }
        System.getenv("DB_PASSWORD")?.let { password ->
            config.password = password
        }
        config.validate()
        return HikariDataSource(config)
    }
}

suspend fun <T> dbQuery(block: () -> T): T =
    withContext(Dispatchers.IO) {
        transaction { block() }
    }