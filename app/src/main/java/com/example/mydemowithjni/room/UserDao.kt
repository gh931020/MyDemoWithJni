package com.example.mydemowithjni.room

import androidx.room.*

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
    @Insert
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
}