package com.example.mydemowithjni.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mydemowithjni.R

/**
 * task任务栈和back stack返回栈
 *
 * 堆栈中的 Activity 永远不会重新排列，只会被送入和退出，在当前 Activity 启动时被送入堆栈，在用户使用返回按钮离开时从堆栈中退出。
 * 因此，返回堆栈按照“后进先出”的对象结构运作。
 * Android 管理任务和返回堆栈的方式是将所有接连启动的 Activity 放到同一任务和一个“后进先出”堆栈中
 */
class TaskActivity : AppCompatActivity() {
    companion object{
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, TaskActivity::class.java)
            context.startActivity(starter)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)
    }
}
/*
Launch Model
"standard"（默认模式）
默认值。系统在启动该 Activity 的任务中创建 Activity 的新实例，并将 intent 传送给该实例。
Activity 可以多次实例化，每个实例可以属于不同的任务，一个任务可以拥有多个实例。

"singleTop"
如果当前任务的顶部已存在 Activity 的实例，则系统会通过调用其 onNewIntent() 方法来将 intent 转送给该实例，而不是创建 Activity 的新实例。
Activity 可以多次实例化，每个实例可以属于不同的任务，一个任务可以拥有多个实例（但前提是返回堆栈顶部的 Activity 不是该 Activity 的现有实例）。
例如，假设任务的返回堆栈包含根 Activity A 以及 Activity B、C 和位于顶部的 D（堆栈为 A-B-C-D；D 位于顶部）。
收到以 D 类型 Activity 为目标的 intent。如果 D 采用默认的 "standard" 启动模式，则会启动该类的新实例，并且堆栈将变为 A-B-C-D-D。
但是，如果 D 的启动模式为 "singleTop"，则 D 的现有实例会通过 onNewIntent() 接收 intent，因为它位于堆栈顶部，堆栈仍为 A-B-C-D。
但是，如果收到以 B 类型 Activity 为目标的 intent，则会在堆栈中添加 B 的新实例，即使其启动模式为 "singleTop" 也是如此。

注意：创建 Activity 的新实例后，用户可以按返回按钮返回到上一个 Activity。但是，当由 Activity 的现有实例处理新 intent 时，
用户将无法通过按返回按钮返回到 onNewIntent() 收到新 intent 之前的 Activity 状态。

"singleTask"
系统会创建新任务，并实例化新任务的根 Activity。但是，如果另外的任务中已存在该 Activity 的实例，
则系统会通过调用其 onNewIntent() 方法将 intent 转送到该现有实例，而不是创建新实例。Activity 一次只能有一个实例存在。

注意：虽然 Activity 在新任务中启动，但用户按返回按钮仍会返回到上一个 Activity。

"singleInstance"
与 "singleTask" 相似，唯一不同的是系统不会将任何其他 Activity 启动到包含该实例的任务中。
该 Activity 始终是其任务唯一的成员；由该 Activity 启动的任何 Activity 都会在其他的任务中打开。

Intent标记

FLAG_ACTIVITY_NEW_TASK
在新任务中启动 Activity。如果您现在启动的 Activity 已经有任务在运行，则系统会将该任务转到前台并恢复其最后的状态，
而 Activity 将在 onNewIntent() 中收到新的 intent。
当启动 Activity 的 intent 包含 FLAG_ACTIVITY_NEW_TASK 标记时。
默认情况下，新 Activity 会启动到调用 startActivity() 的 Activity 的任务中。
它会被推送到调用方 Activity 所在的返回堆栈中。
但是，如果传递给 startActivity() 的 intent 包含 FLAG_ACTIVITY_NEW_TASK 标记，则系统会寻找其他任务来容纳新 Activity。
通常会是一个新任务，但也可能不是。
如果已存在与新 Activity 具有相同亲和性的现有任务，则会将 Activity 启动到该任务中。
如果不存在，则会启动一个新任务。

这与上一节中介绍的 "singleTask" launchMode 值产生的行为相同。

FLAG_ACTIVITY_SINGLE_TOP
如果要启动的 Activity 是当前 Activity（即位于返回堆栈顶部的 Activity），则现有实例会收到对 onNewIntent() 的调用，而不会创建 Activity 的新实例。
这与上一节中介绍的 "singleTop" launchMode 值产生的行为相同。

FLAG_ACTIVITY_CLEAR_TOP
如果要启动的 Activity 已经在当前任务中运行，则不会启动该 Activity 的新实例，而是会销毁位于它之上的所有其他 Activity，
并通过 onNewIntent() 将此 intent 传送给它的已恢复实例（现在位于堆栈顶部）。
launchMode 属性没有可产生此行为的值。



当 Activity 的 allowTaskReparenting 属性设为 "true" 时。
在这种情况下，一旦和 Activity 有亲和性的任务进入前台运行，Activity 就可从其启动的任务转移到该任务。

举例来说，假设一款旅行应用中定义了一个报告特定城市天气状况的 Activity。
该 Activity 与同一应用中的其他 Activity 具有相同的亲和性（默认应用亲和性），并通过此属性支持重新归属。
当您的某个 Activity 启动该天气预报 Activity 时，该天气预报 Activity 最初会和您的 Activity 同属于一个任务。
不过，当旅行应用的任务进入前台运行时，该天气预报 Activity 就会被重新分配给该任务并显示在其中。


alwaysRetainTaskState
    如果在任务的根 Activity 中将该属性设为 "true"，则不会发生上述默认行为。即使经过很长一段时间后，任务仍会在其堆栈中保留所有 Activity。
clearTaskOnLaunch
    如果在任务的根 Activity 中将该属性设为 "true"，那么只要用户离开任务再返回，堆栈就会被清除到只剩根 Activity。
    也就是说，它与 alwaysRetainTaskState 正好相反。用户始终会返回到任务的初始状态，即便只是短暂离开任务也是如此。
finishOnTaskLaunch
    该属性与 clearTaskOnLaunch 类似，但它只会作用于单个 Activity 而非整个任务。
    它还可导致任何 Activity 消失，包括根 Activity。
    如果将该属性设为 "true"，则 Activity 仅在当前会话中归属于任务。
    如果用户离开任务再返回，则该任务将不再存在。

只有当 Activity 具有 ACTION_MAIN 和 CATEGORY_LAUNCHER 过滤器时，才应使用 "singleTask" 和 "singleInstance" 这两种启动模式，
它们会将 Activity 标记为始终启动任务。
 */