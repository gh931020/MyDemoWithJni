package com.example.mydemowithjni.workmanager

import android.content.Context
import androidx.concurrent.futures.CallbackToFutureAdapter
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.google.common.util.concurrent.ListenableFuture
import java.io.IOException

/**
 * 开始工作信号在主线程上调用，因此请务必手动转到您选择的后台线程。
 *
 * @constructor
 */
// class CallbackWorker(appContext: Context, workerParams: WorkerParameters) :
//     ListenableWorker(appContext, workerParams) {
//     override fun startWork(): ListenableFuture<Result> {
//         return CallbackToFutureAdapter.getFuture {
//             completer->
//             val callback = object : Callback {
//                 var successes = 0
//
//                 override fun onFailure(call: Call, e: IOException) {
//                     completer.setException(e)
//                 }
//
//                 override fun onResponse(call: Call, response: Response) {
//                     successes++
//                     if (successes == 100) {
//                         completer.set(Result.success())
//                     }
//                 }
//             }
//             取消监听器
//             completer.addCancellationListener(cancelDownloadsRunnable, executor)
//             repeat(100) {
//                 downloadAsynchronously("https://example.com", callback)
//             }
//             callback
//         }
//     }
// }