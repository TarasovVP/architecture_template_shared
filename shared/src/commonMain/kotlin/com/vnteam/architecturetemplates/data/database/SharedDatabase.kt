package com.vnteam.architecturetemplates.data.database

import app.cash.sqldelight.db.SqlDriver
import com.vnteam.architecturetemplates.AppDatabase

class SharedDatabase(
    private val databaseDriverFactory: DatabaseDriverFactory,
) {
    private var database: AppDatabase? = null

    private suspend fun initDatabase() {
        if (database == null) {
            database = databaseDriverFactory.createDriver().createDatabase()
        }
    }

    suspend operator fun <R> invoke(block: suspend (AppDatabase) -> R): R {
        initDatabase()
        return block(database!!)
    }

    private fun SqlDriver.createDatabase(): AppDatabase { return AppDatabase(this)  }
}