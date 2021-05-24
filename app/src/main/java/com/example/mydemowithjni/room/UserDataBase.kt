package com.example.mydemowithjni.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(User::class), version = 1)
abstract class UserDataBase: RoomDatabase() {
    abstract fun userDao(): UserDao
}

//创建数据库
// fun main() {
//     val db = Room.databaseBuilder(applicationContext, UserDataBase::class.java, "database_name").build()
// }