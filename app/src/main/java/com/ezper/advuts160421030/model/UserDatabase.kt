package com.ezper.advuts160421030.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.hobbyapp.util.buildDB

@Database(entities = [User::class, News::class], version = 4)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun newsDao(): NewsDao
    companion object {
        @Volatile private var instance: UserDatabase ?= null
        private val LOCK = Any()

        fun buildDatabase(context: Context) = buildDB(context)

        operator fun invoke(context: Context) {
            if (instance != null) {
                synchronized(LOCK) {
                    instance ?: buildDatabase(context).also {
                        instance = it
                    }
                }
            }
        }
    }

}