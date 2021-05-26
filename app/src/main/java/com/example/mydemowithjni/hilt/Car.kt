package com.example.mydemowithjni.hilt

import android.speech.tts.TextToSpeech

/**
 * 人工依赖注入(手动依赖注入)
 * 1. 构造函数  2.setter
 */
//1. 构造函数 注入
// class Car(private val engine: Engine) {
//
//     fun start(){
//         engine.start()
//     }
// }

// 2.setter注入
// class Car {
//     lateinit var engine: Engine
//
//     fun start(){
//         engine.start()
//     }
// }

/**
 * 自动依赖注入
 * Dagger
 * Hilt: 推荐用于Android中实现依赖注入的JetPack库,通过为项目中的每个Android类提供容器并自动为您管理其生命周期,
 *       定义了一种在应用中执行DI的标准方法
 * 1.将 hilt-android-gradle-plugin 插件添加到项目的根级 build.gradle 文件中：
 *      classpath 'com.google.dagger:hilt-android-gradle-plugin:2.28-alpha'
 * 2.应用 Gradle 插件并在 app/build.gradle 文件中添加以下依赖项：
 *      apply plugin: 'dagger.hilt.android.plugin'
 *      dependencies {
 *          implementation "com.google.dagger:hilt-android:2.28-alpha"
 *          kapt "com.google.dagger:hilt-android-compiler:2.28-alpha"
 *      }
 */


