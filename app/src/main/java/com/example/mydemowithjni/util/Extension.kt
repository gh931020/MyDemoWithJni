package com.example.mydemowithjni.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

import androidx.datastore.preferences.preferencesDataStore

/**
 * 使用委托的方式为Context添加拓展属性
 */
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name="settings")