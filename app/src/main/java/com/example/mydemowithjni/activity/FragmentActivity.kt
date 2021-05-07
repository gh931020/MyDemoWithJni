package com.example.mydemowithjni.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.mydemowithjni.R
import com.example.mydemowithjni.fragment.Example2Fragment
import com.example.mydemowithjni.fragment.ExampleFragment
import com.example.mydemowithjni.fragment.TransitionFragment
import kotlinx.android.synthetic.main.activity_fragment.*
import kotlinx.android.synthetic.main.fragment_example.*

class FragmentActivity : AppCompatActivity(R.layout.activity_fragment) {
    companion object{
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, FragmentActivity::class.java)

            context.startActivity(starter)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // This is to ensure that the fragment is added only once, when the activity is first created.
        if (savedInstanceState== null) {
            val bundle = bundleOf("some_int" to 0)
            // Calling commit() doesn't perform the transaction immediately.
            // If necessary, however, you can call commitNow() to run the fragment transaction on your UI thread immediately.
            supportFragmentManager.commit {
                // you should always use setReorderingAllowed(true) when performing a FragmentTransaction.
                // 可优化事务中涉及的 Fragment 的状态变化，以使动画和过渡正常运行。
                setReorderingAllowed(true)
                // 泛型实化
                // add<ExampleFragment>(R.id.fragContainer)
                // If your fragment requires some initial data, arguments can be passed to your fragment by providing a Bundle in the call to FragmentTransaction.add()
                add<ExampleFragment>(R.id.fragContainer, tag = "tag", args = bundle)
                // add<Example2Fragment>(R.id.fragContainer, tag = "tag2", args = bundle)

                // The arguments Bundle can then be retrieved from within your fragment by calling requireArguments()
                // 调用 addToBackStack() 会将事务提交到返回堆栈。用户稍后可以通过按“返回”按钮反转事务并恢复上一个 Fragment。
                // 如果您在一个事务中添加或移除了多个 Fragment，弹出返回堆栈时，所有这些操作都会撤消。
                // 如果您在执行移除 Fragment 的事务时未调用 addToBackStack()，则提交事务时会销毁已移除的 Fragment，用户无法返回到该 Fragment。
                // 如果您在移除某个 Fragment 时调用了 addToBackStack()，则该 Fragment 只会 STOPPED，稍后当用户返回时它会 RESUMED。
                // 请注意，在这种情况下，其视图会被销毁。
                // addToBackStack("name")// name can be null
                // Calling replace() is equivalent to calling remove() with a fragment in a container and adding a new fragment to that same container.
            }
        }else{
            // When a configuration change occurs and the activity is recreated, savedInstanceState is no longer null,
            // and the fragment does not need to be added a second time,
            // as the fragment is automatically restored from the savedInstanceState.
        }

        // showFragment(tabRg.checkedRadioButtonId)

        // 使用 findFragmentById() 获取对布局容器中当前 Fragment 的引用。
        // val exampleFragmentById = supportFragmentManager.findFragmentById(R.id.fragContainer) as ExampleFragment
        // 使用 findFragmentByTag() 获取引用。在 FragmentTransaction 中的 add() 或 replace() 操作期间分配标记。
        // val exampleFragmentByTag = supportFragmentManager.findFragmentByTag("tag") as ExampleFragment

        tabRg.setOnCheckedChangeListener { group, checkedId ->
            showFragment(checkedId)
        }


    }

    private fun showFragment(checkId: Int) {

            when (checkId) {
                R.id.frag1Rbtn -> {
                    supportFragmentManager.commit {
                        setCustomAnimations(
                            R.anim.slide_in,
                            R.anim.fade_out,
                            R.anim.fade_in,
                            R.anim.slide_out
                        )
                        supportFragmentManager.findFragmentByTag("tag2")?.let { hide(it) }
                        supportFragmentManager.findFragmentByTag("tag")?.let { show(it) }
                    }
                }
                R.id.frag2Rbtn -> {
                    supportFragmentManager.commit {
                        setCustomAnimations(
                            R.anim.slide_in,
                            R.anim.fade_out,
                            R.anim.fade_in,
                            R.anim.slide_out
                        )

                        addSharedElement(anim1Iv, "anim1_iv")
                        supportFragmentManager.findFragmentByTag("tag")?.let { hide(it) }
                        if (supportFragmentManager.findFragmentByTag("tag2") == null) {
                            add<Example2Fragment>(R.id.fragContainer, tag = "tag2")
                        } else {
                            supportFragmentManager.findFragmentByTag("tag2")?.let { show(it) }
                        }
                    }
                }
                else -> {
                }
            }
        }

}