package com.example.mydemowithjni.room

import androidx.room.*
import java.util.*

/**
 * Dao类既可以是接口,又可以是抽象类.
 * 如果是抽象类,则该Dao可以选择有一个以RoomDatabase为唯一参数的构造函数.
 * 除非已对构造器调用allowMainThreadQueries()否则Room不支持在主线程上访问数据库.
 *
 */
@Dao
interface UserDao {
    @Query("SELECT * FROM myuser")
    fun getAll(): List<User>

    @Query("SELECT * FROM myuser WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>

    @Query("SELECT * FROM myuser WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): User

    @Query("SELECT * FROM myuser WHERE age BETWEEN :minAge AND :maxAge")
    fun loadAllUsersBetweenAges(minAge: Int, maxAge: Int): List<User>

    @Query("SELECT * FROM myuser WHERE first_name LIKE :search OR last_name LIKE :search")
    fun findUserWithName(search: String): List<User>

    /**
     * 大多数情况下，您只需获取实体的几个字段。例如，您的界面可能仅显示用户的名字和姓氏，而不是用户的每一条详细信息。
     * 借助 Room，您可以从查询中返回任何基于 Java 的对象，前提是结果列集合会映射到返回的对象。
     * 例如，您可以创建以下基于 Java 的普通对象 (POJO) 来获取用户的名字和姓氏：
     * @return List<NameTuple>
     */
    @Query("SELECT first_name, last_name FROM myuser")
    fun loadFullName(): List<NameTuple>

    /**
     * 如果 @Insert 方法只接收 1 个参数，则它可以返回 long，这是插入项的新 rowId。
     * 如果参数是数组或集合，则应返回 long[] 或 List<Long>
     * @param users Array<out User>
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: User)

    /**
     * 会修改数据库中以参数形式给出的一组实体。它使用与每个实体的主键匹配的查询。
     * @param users Array<out User>
     */
    @Update
    fun updateUsers(vararg users: User)

    /**
     * 从数据库中删除一组以参数形式给出的实体。它使用主键查找要删除的实体。
     * @param user User
     */
    @Delete
    fun delete(user: User)

    /**
     * 用于返回将父实体与子实体配对的数据类的所有实例。
     * 该方法需要 Room 运行两次查询，因此应向该方法添加 @Transaction 注释，以确保整个操作以原子方式执行。
     * @return List<UserAndLibrary>
     */
    @Transaction
    @Query("SELECT * FROM myuser")
    fun getUsersAndLibraries(): List<UserAndLibrary>

    @Transaction
    @Query("SELECT * FROM myuser")
    fun getUsersAndPlaylists(): List<UserAndPlaylists>

    @Transaction
    @Query("SELECT * FROM playlist")
    fun getPlaylistWithSongs(): List<PlaylistWithSongs>

    @Transaction
    @Query("SELECT * FROM song")
    fun getSongsWithPlaylists(): List<SongWithPlaylists>

    @Transaction
    @Query("SELECT * FROM myuser")
    fun getUsersWithPlaylistsAndSongs(): List<UserWithPlaylistsAndSongs>

    @Query("SELECT * FROM myuser WHERE birthday BETWEEN :from AND :to")
    fun findUsersBornBetweenDates(from: Date, to: Date): List<User>
}