package com.example.mydemowithjni.proto

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import java.io.InputStream

import java.io.OutputStream

/**
 * 需要配置proto插件,四个位置:根build.gradle, app build.grale的头plugin, proto配置代码块, 添加依赖
 */
object SettingsSerializer: Serializer<MySettings> {
    override val defaultValue: MySettings = MySettings.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): MySettings {
        try {
            return MySettings.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: MySettings, output: OutputStream) = t.writeTo(output)
}
val Context.settingsDataStore: DataStore<MySettings> by dataStore(fileName = "mysettings.pb", serializer = SettingsSerializer)