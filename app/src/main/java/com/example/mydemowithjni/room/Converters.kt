package com.example.mydemowithjni.room

import androidx.room.TypeConverter
import com.google.android.material.internal.ManufacturerUtils
import java.util.*

/**
 * 类型转换器
 * 当使用自定义的类型中包含需要存储到数据库中的属性值时,需要使用TypeConverter,
 * 在自定义类与Room可以保留的已知类型之间来回转换
 */
class Converters {
    /**
     * 保留 Date 的实例，编写TypeConverter 将等效的 Unix 时间戳存储在数据库中
     * @param value Long?
     * @return Date?
     */
    @TypeConverter
    fun fromTimestamp(value: Long?): Date?{
        return value?.let { Date(value) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long?{
        return date?.time?.toLong()
    }
}