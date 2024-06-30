package com.example.hobbyapp.util

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ezper.advuts160421030.model.UserDatabase



val DB_NAME = "project_uas_anmp"

// Untuk Table User
fun buildDB(context: Context): UserDatabase {
    val db = Room.databaseBuilder(
        context,
        UserDatabase::class.java,
        DB_NAME
    )
        .addMigrations(MIGRATION_1_2)
        .addMigrations(MIGRATION_2_3)
        .addMigrations(MIGRATION_3_4)
        .build()
    return db
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE user ADD COLUMN email VARCHAR(45) DEFAULT '' NOT NULL"
        )
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE IF NOT EXISTS `news` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `judul` TEXT NOT NULL, `url` TEXT NOT NULL, `deskripsi` TEXT NOT NULL, `pengarang` TEXT NOT NULL, `detail` TEXT NOT NULL)")
    }
}

val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `News_new` (
                `id` INTEGER NOT NULL PRIMARY KEY, 
                `judul` TEXT NOT NULL, 
                `url` TEXT NOT NULL, 
                `deskripsi` TEXT NOT NULL, 
                `pengarang` TEXT NOT NULL, 
                `detail` TEXT NOT NULL
            )
            """.trimIndent()
        )


        database.execSQL(
            """
            INSERT INTO `News_new` (`id`, `judul`, `url`, `deskripsi`, `pengarang`, `detail`)
            SELECT `id`, `judul`, `url`, `deskripsi`, CAST(`pengarang` AS TEXT), `detail` FROM `News`
            """.trimIndent()
        )
        database.execSQL("DROP TABLE `News`")
        database.execSQL("ALTER TABLE `News_new` RENAME TO `News`")
    }
}

