package marinj.feature.auth.data.dao

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Users : Table() {
    val userId: Column<Int> = integer("id").autoIncrement()
    val email: Column<String> = varchar("email", 128).uniqueIndex()
    val userName = varchar("user_name", 256)
    val password = varchar("password", 64)
}