package com.example.mydemowithjni.ext

import android.content.Context
import android.content.Intent

/**
 * 使用泛型实化启动Activity
 * @receiver Context
 * @param block [@kotlin.ExtensionFunctionType] Function1<Intent, Unit>
 */
inline fun <reified T> Context.start(block: Intent.()->Unit){
    val intent = Intent(this, T::class.java)
    intent.block()
    startActivity(intent)
}