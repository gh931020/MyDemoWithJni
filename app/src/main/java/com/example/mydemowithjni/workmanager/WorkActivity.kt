package com.example.mydemowithjni.workmanager

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.BoringLayout.make
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.example.mydemowithjni.R
import com.google.android.material.snackbar.Snackbar
import com.google.common.util.concurrent.ListenableFuture
import java.util.concurrent.TimeUnit

class WorkActivity : AppCompatActivity() {
    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, WorkActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work)
        // 不论您选择以何种方式调度工作，请始终使用 WorkRequest。
        // Worker 定义工作单元，WorkRequest（及其子类）则定义工作运行方式和时间。在最简单的情况下，您可以使用 OneTimeWorkRequest
        // val uploadWorkRequest = OneTimeWorkRequest.from(UploadWorker::class.java)

        val uploadWorkRequest: WorkRequest = OneTimeWorkRequestBuilder<UploadWorker>()
            .setInitialDelay(1000, TimeUnit.MILLISECONDS)// 添加运行配置
            .build()

        WorkManager.getInstance(this).enqueue(uploadWorkRequest)

        // 调度定期工作
        // 您的应用有时可能需要定期运行某些工作。例如，您可能要定期备份数据、定期下载应用中的新鲜内容或者定期上传日志到服务器。
        // 运行时间间隔为1小时, 可以定义的最短时间间隔为15分钟
        val saveRequest = PeriodicWorkRequestBuilder<SaveImageToFileWorker>(1, TimeUnit.HOURS)
            // additional configuration
            .build()
        // 创建一个在 每小时的最后15分钟内运行的定期工作
        val myUploadWork = PeriodicWorkRequestBuilder<SaveImageToFileWorker>(
            1, TimeUnit.HOURS,
            15, TimeUnit.MINUTES
        ).build()

    // 您可以对定期工作设置约束。例如，您可以为工作请求添加约束，以便工作仅在用户设备充电时运行。
    // 在这种情况下，除非满足约束条件，否则即使过了定义的重复间隔，PeriodicWorkRequest 也不会运行。
    // 这可能会导致工作在某次运行时出现延迟，甚至会因在相应间隔内未满足条件而被跳过。

        /*
            NetworkType	        约束运行工作所需的网络类型。例如 Wi-Fi (UNMETERED)。
            BatteryNotLow	    如果设置为 true，那么当设备处于“电量不足模式”时，工作不会运行。
            RequiresCharging	如果设置为 true，那么工作只能在设备充电时运行。
            DeviceIdle	        如果设置为 true，则要求用户的设备必须处于空闲状态，才能运行工作。
                                如果您要运行批量操作，否则可能会降低用户设备上正在积极运行的其他应用的性能，
                                建议您使用此约束。
            StorageNotLow	    如果设置为 true，那么当用户设备上的存储空间不足时，工作不会运行。
         */
        // 如果指定了多个约束，工作将仅在满足所有约束时才会运行。
        // 如果在工作运行时不再满足某个约束，WorkManager 将停止工作器。系统将在满足所有约束后重试工作。
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)//要求连接wifi
            .setRequiresCharging(true)//要求接入电源
            .build()
        val myWorkRequest = OneTimeWorkRequestBuilder<UploadWorker>()
            .setConstraints(constraints)//将约束条件添加进Request
            .build()

        // 延迟工作,只有首次运行时会延迟。
        val delayWorkRequest = OneTimeWorkRequestBuilder<UploadWorker>()
            .setInitialDelay(10, TimeUnit.MINUTES)//设置延迟10分钟后开始工作
            .build()

        // Retry and Backoff Policy
        // 如果您需要让 WorkManager 重试工作，可以从工作器返回 Result.retry()。
        // 然后，系统将根据退 backoff delay and backoff policy重新调度工作。
        // 退避延迟时间指定了[首次]尝试后重试工作前的最短等待时间。此值不能超过 10 秒（或 MIN_BACKOFF_MILLIS）。
        // 退避政策定义了在[后续重试]过程中，退避延迟时间随时间以怎样的方式增长。
        // WorkManager 支持 2 个退避政策，即 LINEAR 和 EXPONENTIAL。

        // 例中，最短退避延迟时间设置为允许的最小值，即 10 秒。
        // 由于政策为 LINEAR，每次尝试重试时，重试间隔都会增加约 10 秒。
        // 例如，第一次运行以 Result.retry() 结束并在 10 秒后重试；
        // 然后，如果工作在后续尝试后继续返回 Result.retry()，那么接下来会在 20 秒、30 秒、40 秒后重试，以此类推。
        // 如果退避政策设置为 EXPONENTIAL，那么重试时长序列将接近 20、40、80 秒，以此类推。
        val retryWorkRequest = OneTimeWorkRequestBuilder<UploadWorker>()
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            ).build()

        // 标记工作
        // 每个工作请求都有一个唯一标识符,该标识符可用于在以后标识该工作,以便取消工作,或观察其进度
        // 如果有一组在逻辑上有关系的工作,对这些工作进行标记,可以后续统一处理一组的工作请求
        // 例如，WorkManager.cancelAllWorkByTag(String) 会取消带有特定标记的所有工作请求，
        // WorkManager.getWorkInfosByTag(String) 会返回一个 WorkInfo 对象列表，该列表可用于确定当前工作状态。
        val tagWorkRequest = OneTimeWorkRequestBuilder<UploadWorker>()
            .addTag("cleanUp")//可以向单个工作请求添加多个标记。这些标记在内部以一组字符串的形式进行存储。
                                    // 对于工作请求，您可以通过 WorkRequest.getTags() 检索其标记集。
            .build()

        // 分配输入数据
        // 输入值以键值对的形式存储在Data对象中,并且可以在工作请求中设置.
        // workmanager会在执行工作时,将输入Data传递给工作,Worker类可通过调用Worker.getInputData()访问输入参数
        val dataWorkRequest = OneTimeWorkRequestBuilder<UploadWorker>()
            .setInputData(workDataOf("IMAGE_URI" to "")).build()

        // 管理工作
        // 定义Worker和WorkerRequest后,最后一步是将工作加入队列,调用WorkManager的enqueue方法,传递要运行的workRequest
        WorkManager.getInstance(this).enqueue(dataWorkRequest)
        // 避免重复添加同一个Request, 或者使用唯一工作
        // 确保同一时刻只有一个具有特定名称的工作实例,特定名称由开发者定义
        // WorkManager.getInstance(this).enqueueUniqueWork() 用于一次性工作
        // WorkManager.getInstance(this).enqueueUniquePeriodicWork() 用于定期工作
        // arg1->uniqueWorkName: 用于唯一标识工作请求的String;
        // arg2->existingWorkPolicy: 枚举类,告知WorkManager如果已有使用该名称且尚未完成的唯一工作连,应执行什么操作
        //       REPLACE: 用新的工作替换现有的工作,将取消现有工作; KEEP: 保留现有工作,并忽略新工作;
        //       APPEND: 将新工作附加到现有的工作末尾.现有工作将成为新工作的先决条件。
        //               如果现有工作变为 CANCELLED 或 FAILED 状态，新工作也会变为 CANCELLED 或 FAILED。
        //               如果您希望无论现有工作的状态如何都运行新工作，请改用 APPEND_OR_REPLACE。
        // arg3->work: 要调度的WorkRequest

        val uniqueWorkRequest = PeriodicWorkRequestBuilder<UploadWorker>(24, TimeUnit.HOURS)
            .setConstraints(Constraints.Builder().setRequiresCharging(true).build())
            .build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "sendLogs",
            ExistingPeriodicWorkPolicy.KEEP,// 如果sendLogs 作业已处于队列中的情况下运行，系统会保留现有的作业，并且不会添加新的作业。
            uniqueWorkRequest
        )

        // 观察工作状态, 返回WorkInfo 对象的 ListenableFuture，该值包含工作的 id、其标记、其当前的 State 以及通过 Result.success(outputData) 设置的任何输出数据。
        val workManager = WorkManager.getInstance(this)
        // val workInfoById: ListenableFuture<WorkInfo> = workManager.getWorkInfoById(uniqueWorkRequest.id)// by id
        // val workInfosForUniqueWork: ListenableFuture<List<WorkInfo>> = workManager.getWorkInfosForUniqueWork("sync") //by name
        // val workInfosByTag:  ListenableFuture<List<WorkInfo>> = workManager.getWorkInfosByTag("syncTag") // by tag
        workManager.getWorkInfoByIdLiveData(uniqueWorkRequest.id).observe(this,{ workInfo->
            if (workInfo?.state == WorkInfo.State.SUCCEEDED){
                Toast.makeText(this@WorkActivity, R.string.work_completed, Toast.LENGTH_SHORT).show()
            }
        })

        //组合查询
        val workQuery = WorkQuery.Builder
            .fromTags(listOf("syncTag"))
            .addStates(listOf(WorkInfo.State.FAILED, WorkInfo.State.CANCELLED))
            .addUniqueWorkNames(listOf("preProcess", "sync"))
            .build()
        // WorkQuery 中的每个组件（标记、状态或名称）与其他组件都是 AND 逻辑关系。
        // 组件中的每个值都是 OR 逻辑关系。
        // 例如：<em>(name1 OR name2 OR ...) AND (tag1 OR tag2 OR ...) AND (state1 OR state2 OR ...)

        //取消和停止工作
        // by id
        // workManager.cancelWorkById(uniqueWorkRequest.id)
        // by name
        // workManager.cancelUniqueWork("sync")
        // by tag
        // workManager.cancelAllWorkByTag("syncTag")//会取消具有给定标记的所有工作。

        // 停止正在运行的工作
        // 您明确要求取消它（例如，通过调用 WorkManager.cancelWorkById(UUID) 取消）。
        // 如果是唯一工作,当ExistingWorkPolicy为Replace的新workRequest加入到队列中,旧的WorkRequest会被取消
        // 工作的约束条件改变,不在满足运行要求
        // 系统处于某种原因要求停止工作.
        // 您应该合作地取消正在进行的任何工作，并释放您的工作器保留的所有资源。
        // 例如，此时应该关闭所打开的数据库和文件句柄。有两种机制可让您了解工作器何时停止。
        // onStopped()
        // 在您的工作器停止后，WorkManager 会立即调用 ListenableWorker.onStopped()。替换此方法可关闭您可能保留的所有资源。
        // isStopped()
        // 您可以调用 ListenableWorker.isStopped() 方法以检查工作器是否已停止。
        // 如果您在工作器执行长时间运行的操作或重复操作，您应经常检查此属性，并尽快将其用作停止工作的信号。
    }

}

class SaveImageToFileWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {

        return Result.success()
    }

}
