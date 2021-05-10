package com.example.mydemowithjni.databinding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class NameViewModel: ViewModel() {

    /*
    在大多数情况下，应用组件的 onCreate() 方法是开始观察 LiveData 对象的正确着手点，原因如下：
        确保系统不会从 Activity 或 Fragment 的 onResume() 方法进行冗余调用。
        确保 Activity 或 Fragment 变为活跃状态后具有可以立即显示的数据。
        一旦应用组件处于 STARTED 状态，就会从它正在观察的 LiveData 对象接收最新值。只有在设置了要观察的 LiveData 对象时，才会发生这种情况。
    通常，LiveData 仅在数据发生更改时才发送更新，并且仅发送给活跃观察者。
    此行为的一种例外情况是，观察者从非活跃状态更改为活跃状态时也会收到更新。
    此外，如果观察者第二次从非活跃状态更改为活跃状态，则只有在自上次变为活跃状态以来值发生了更改时，它才会收到更新。

    LiveData 没有公开可用的方法来更新存储的数据。MutableLiveData 类将公开 setValue(T) 和 postValue(T) 方法，
    如果您需要修改存储在 LiveData 对象中的值，则必须使用这些方法。
    通常情况下会在 ViewModel 中使用 MutableLiveData，
    然后 ViewModel 只会向观察者公开不可变的 LiveData 对象。
     */
    //创建LiveData
    val currentName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    //转换liveData
    /*
        您可能希望在将 LiveData 对象分派给观察者之前对存储在其中的值进行更改，
        或者您可能需要根据另一个实例的值返回不同的 LiveData 实例。
        Lifecycle 软件包会提供 Transformations 类，该类包括可应对这些情况的辅助程序方法。

        Transformations.map()
     */
    val userLiveData: LiveData<User> = UserLiveData()
    //对存储在 LiveData 对象中的值应用函数，并将结果传播到下游。
    val userName: LiveData<String> = Transformations.map(userLiveData) {
            user -> "${user.firstName} ${user.lastName}"
    }
    //与 map() 类似，对存储在 LiveData 对象中的值应用函数，并将结果解封和分派到下游。
    // 传递给 switchMap() 的函数必须返回 LiveData 对象
    val userId: LiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val user = Transformations.switchMap(userId){ id->
        getUser(id)
    }

    private fun getUser(id: String): LiveData<User> {
        return MutableLiveData<User>()
    }

    /*

    postalCode 字段定义为 addressInput 的转换。只要您的应用具有与 postalCode 字段关联的活跃观察者，
    就会在 addressInput 发生更改时重新计算并检索该字段的值。

    此机制允许较低级别的应用创建以延迟的方式按需计算的 LiveData 对象。
    ViewModel 对象可以轻松获取对 LiveData 对象的引用，然后在其基础之上定义转换规则。
    class MyViewModel(private val repository: PostalCodeRepository) : ViewModel() {
        private val addressInput = MutableLiveData<String>()
        // 当address改变时会重新查询,因此观察者会收到数据更新的通知
        val postalCode: LiveData<String> = Transformations.switchMap(addressInput) {
                address -> repository.getPostCode(address) }

        private fun setInput(address: String) {
            //改变时会触发switchMap的调用
            addressInput.value = address
        }
    }
     */

}

class UserLiveData: LiveData<User>(){

}