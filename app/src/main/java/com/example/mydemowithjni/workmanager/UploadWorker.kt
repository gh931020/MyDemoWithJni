package com.example.mydemowithjni.workmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

/**
 * 定义用于上传图片的Worker
 * 如需为 WorkManager 创建一些要运行的工作，扩展 Worker 类并替换 doWork() 方法
 * @constructor
 */
class UploadWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    /**
     * doWork() 方法在 WorkManager 提供的后台线程上异步运行。
     * @return Result
     */
    override fun doWork(): Result {
        val imageUriInput = inputData.getString("IMAGE_URI")?: return Result.failure()

        // Do the work here--in this case, upload the images.
        uploadImages(imageUriInput)
        // Indicate whether the work finished successfully with the Result
        // 通过Data设置返回值
        // Result.success(workDataOf("SUCCESS" to "success"))
        return Result.success()
        // Result.success()：工作成功完成。
        // Result.failure()：工作失败。
        // Result.retry()：工作失败，应根据其重试政策在其他时间尝试。
    }

    private fun uploadImages(imageUriInput: String) {
        //TODO uploadImages
    }
}