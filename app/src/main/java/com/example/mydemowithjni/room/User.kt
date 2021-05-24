package com.example.mydemowithjni.room

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 * 默认情况下,Room将类形成作为数据库表名称.
 * 可通过tableName自定义表名[表名不区分大小写]
 * @property uid Int
 * @property firstName String
 * @property lastName String
 * @constructor
 */
@Entity(primaryKeys = arrayOf("uid", "firstName"), tableName = "myuser")
data class User(
    /**
     * 每隔实体必须将至少1个字段定义为主键, 即使只有1个字段,您仍然需要添加@PrimaryKey注释.
     * 如果想让Room为实体分配自动ID, 可以设置autoGenerate属性
     * 如果具有复合主键,可以使用@Entity注释的primaryKeys属性
     */
    @PrimaryKey(autoGenerate = true)
    var uid: Int,
    @ColumnInfo(name = "first_name")
    var firstName: String,
    @ColumnInfo(name = "last_name")
    var lastName: String,
    @ColumnInfo(name = "age")
    var age: Int,
    // 使用@Ignore忽略该属性,,如果要忽略继承的父类的属性,使用@Entity(ignoredColumns = arrayof("name"))
    @Ignore
    var avtar: Bitmap)

