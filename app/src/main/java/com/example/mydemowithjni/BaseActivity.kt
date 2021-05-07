package com.example.mydemowithjni

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.elvishew.xlog.XLog

abstract class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        restroeState(savedInstanceState)
        XLog.i("onCreat()")
        // 您必须在此处调用 setContentView() 来定义 Activity 界面的布局
        setContentView(getLayoutId())
        setListener()
        initData()
    }

    abstract fun restroeState(savedInstanceState: Bundle?)

    abstract fun getLayoutId(): Int

    abstract fun setListener()

    abstract fun initData()

    override fun onStart() {
        super.onStart()
        XLog.i("onStart()")
        // Activity 将进入“已启动”状态，并对用户可见。此回调包含 Activity 进入前台与用户进行互动之前的最后准备工作。
    }

    override fun onResume() {
        super.onResume()
        XLog.i("onResume()")
        // 系统会在 Activity 开始与用户互动之前调用此回调。此时，该 Activity 位于 Activity 堆栈的顶部，并会捕获所有用户输入。
        // 应用的大部分核心功能都是在 onResume() 方法中实现的。
        // 应用会一直保持这种状态，直到某些事件发生，让焦点远离应用。此类事件包括接到来电、用户导航到另一个 Activity，或设备屏幕关闭。
    }

    override fun onPause() {
        super.onPause()
        XLog.i("onPause()")
        // 当 Activity 失去焦点并进入“已暂停”状态时，系统就会调用 onPause().
        // 例如，当用户点按“返回”或“最近使用的应用”按钮时，就会出现此状态。
        // 当系统为您的 Activity 调用 onPause() 时，从技术上来说，这意味着您的 Activity 仍然部分可见，
        // 但大多数情况下，这表明用户正在离开该 Activity，该 Activity 很快将进入“已停止”或“已恢复”状态。
        // 您不应使用 onPause() 来保存应用或用户数据、进行网络呼叫或执行数据库事务。
        // 可以使用 onPause() 方法释放系统资源、传感器（例如 GPS）手柄，或当您的 Activity 暂停且用户不需要它们时仍然可能影响电池续航时间的任何资源。
        // onPause() 执行完毕后，下一个回调为 onStop()或 onResume()，具体取决于 Activity 进入“已暂停”状态后发生的情况。
        // 您不应使用 onPause() 来保存应用或用户数据、进行网络调用或执行数据库事务。
        // 因为在该方法完成之前，此类工作可能无法完成。相反，您应在 onStop() 期间执行高负载的关闭操作
    }

    override fun onStop() {
        super.onStop()
        XLog.i("onStop()")
        // 当 Activity 对用户不再可见时，系统会调用 onStop()。
        // 出现这种情况的原因可能是 Activity 被销毁，新的 Activity 启动，或者现有的 Activity 正在进入“已恢复”状态并覆盖了已停止的 Activity。
        // 在所有这些情况下，停止的 Activity 都将完全不再可见。
        // 在 onStop() 方法中，应用应释放或调整在应用对用户不可见时的无用资源。例如，应用可以暂停动画效果，或从精确位置更新切换到粗略位置更新。
        // 您还应使用 onStop() 执行 CPU 相对密集的关闭操作。例如，如果您无法找到更合适的时机来将信息保存到数据库，可以在 onStop() 期间执行此操作
        // 系统调用的下一个回调将是 onRestart()（如果 Activity 重新与用户互动）或者 onDestroy()（如果 Activity 彻底终止）。
    }

    override fun onRestart() {
        super.onRestart()
        XLog.i("onRestart()")
        // 当处于“已停止”状态的 Activity 即将重启时，系统就会调用此回调。
        // onRestart() 会从 Activity 停止时的状态恢复 Activity。
        // 此回调后面总是跟着 onStart()。
    }

    override fun onDestroy() {
        super.onDestroy()
        XLog.i("onDestroy()")
        // 系统会在销毁 Activity 之前调用此回调。
        // 此回调是 Activity 接收的最后一个回调。通常，实现 onDestroy() 是为了确保在销毁 Activity 或包含该 Activity 的进程时释放该 Activity 的所有资源。
        // onDestroy() 回调应释放先前的回调（例如 onStop()）尚未释放的所有资源。
    }

}
