package com.example.mydemowithjni.databinding

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.mydemowithjni.BR

class BindUser : BaseObservable() {
    /*
    unresolved reference: BR
    https://blog.csdn.net/weixin_40929353/article/details/102911137
    app下build.gradle

    apply plugin: 'kotlin-kapt'

    kapt {
        generateStubs = true
    }

    dependencies {
        implementation fileTree(dir: 'libs', include: ['*.jar'])
        implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
        //版本要与gradle版本一致
        kapt  "com.android.databinding:compiler:3.5.0"
    }
     */
    @get:Bindable
    var firstName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.firstName)
        }

    @get:Bindable
    var lastName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.lastName)
        }
}