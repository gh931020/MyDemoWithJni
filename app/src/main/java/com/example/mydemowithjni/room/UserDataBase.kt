package com.example.mydemowithjni.room

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = arrayOf(User::class), version = 1)
@TypeConverters(Converters::class)
abstract class UserDataBase: RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object{
       private var instance: UserDataBase?=null

        val MIGRATION_1_2 = object : Migration(1, 2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE 'FRUIT' ('id' INTEGER, 'name' TEXT, PRIMARY KEY('id'))")
            }
        }

        val MIGRATION_2_3 = object : Migration(2, 3){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Book ADD COLUMN pub_year INTEGER")
            }
        }

        /**
         * https://developer.android.com/training/data-storage/room/migrating-db-versions
         * 在 Room 2.2.0 及更高版本中，您可以使用注释 @ColumnInfo(defaultValue = "...") 定义列的默认值。
         * 在低于 2.2.0 的版本中，为列定义默认值的唯一方法是直接在执行的 SQL 语句中定义默认值，
         * 这样创建的默认值是 Room 不知道的值。
         * 这意味着，如果数据库最初由版本低于 2.2.0 的 Room 创建，
         * 升级应用以使用 Room 2.2.0 就可能需要您为那些在未使用 Room API 的情况下定义的现有默认值提供特殊的迁移路径。
         *
         * 总结:避免在升级过程中使用@ColumnInfo(defaultValue = "...")的方式添加带有默认值的列
         */
        val MIGRATION_3_4 = object : Migration(3, 4){
            override fun migrate(database: SupportSQLiteDatabase) {
                // 1.创建新表
                database.execSQL("""
                    CREATE TABLE new_song (
                        id INTEGER PRIMARY KEY NOT NULL,
                        name TEXT,
                        tag TEXT NOT NULL DEFAULT ''
                    )
                """.trimIndent())

                // 2.将旧表数据移植到新表
                database.execSQL("""
                   INSERT INTO new_song (id, name, tag)
                       SELECT id, name, tag FROM song
                """.trimIndent())

                // 3.删除旧表
                database.execSQL("DROP TABLE song")

                // 4.将新表重新命名为song
                database.execSQL("ALTER TABLE new_song RENAME TO song")
            }

        }

        @Synchronized
        fun getInstance(context: Context): UserDataBase{
            instance?.let {
                return it
            }
            return Room.databaseBuilder(context, UserDataBase::class.java, "database_name")
                //在应用的数据库构建器中设置此选项意味着 Room 在尝试执行没有定义迁移路径的迁移时会从数据库表中永久删除所有数据。
                .fallbackToDestructiveMigration()
                .build().apply {
                    instance = this
                }
        }


    }
}

//创建数据库
// fun main() {
//     val db = Room.databaseBuilder(applicationContext, UserDataBase::class.java, "database_name").build()
// }