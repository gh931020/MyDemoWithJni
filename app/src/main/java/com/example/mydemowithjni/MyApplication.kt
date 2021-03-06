package com.example.mydemowithjni

import android.app.Application
import androidx.startup.AppInitializer
import com.elvishew.xlog.LogConfiguration
import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.XLog
import com.elvishew.xlog.printer.AndroidPrinter
import com.elvishew.xlog.printer.Printer
import com.elvishew.xlog.printer.file.FilePrinter
import com.elvishew.xlog.printer.file.clean.FileLastModifiedCleanStrategy
import com.elvishew.xlog.printer.file.naming.DateFileNameGenerator
import com.example.mydemowithjni.initializer.WorkManagerInitializer
import com.example.mydemowithjni.util.Constants
import dagger.hilt.android.HiltAndroidApp
import java.lang.RuntimeException
import java.util.concurrent.*

const val MAX_TIME = 1000 * 60 * 60 * 24 * 30L

/**
 * 所有使用 Hilt 的应用都必须包含一个带有 @HiltAndroidApp 注释的 Application 类。
 * @HiltAndroidApp 会触发 Hilt 的代码生成操作，生成的代码包括应用的一个基类，该基类充当应用级依赖项容器。
 */
@HiltAndroidApp
class MyApplication: Application() {
    /**
     * 创建包含四个线程的线程池
     */
    val executorService: ExecutorService = Executors.newFixedThreadPool(4)
    // 配置线程池
    /*
     * Gets the number of available cores
     * (not always the same as the maximum number of cores)
     */
    private val NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors()

    /**
     * Instantiates the queue of Runnables as a LinkedBlockingQueue
     */
    private val workQueue: BlockingDeque<Runnable> = LinkedBlockingDeque<Runnable>()

    // Sets the amount of time an idle thread waits before terminating
    private val KEEP_ALIVE_TIME = 1L

    // Sets the Time Unit to seconds
    private val KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS

    /**
     * 根据处理器核心总数指定线程池大小,指定保持活跃的时间为1秒,并指定了一个输入队列
     */
    private val threadPoolExecutor = ThreadPoolExecutor(NUMBER_OF_CORES, NUMBER_OF_CORES, KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT, workQueue)

    override fun onCreate() {
        super.onCreate()
        // 也可以通过在manifest中注册provider进行初始化
        // AppInitializer.getInstance(this).initializeComponent(WorkManagerInitializer::class.java)
        initXLog()
    }

    private fun initXLog() {
        val config = LogConfiguration.Builder()
                .logLevel(if (BuildConfig.DEBUG) LogLevel.ALL // Specify log level, logs below this level won't be printed, default: LogLevel.ALL
                else LogLevel.NONE)
                .tag("MyDemo") // Specify TAG, default: "X-LOG"
                .enableThreadInfo() // Enable thread info, disabled by default
                .enableStackTrace(2) // Enable stack trace info with depth 2, disabled by default
                .build()
        // .enableBorder()                                        // Enable border, disabled by default
        // .jsonFormatter(new MyJsonFormatter())                  // Default: DefaultJsonFormatter
        // .xmlFormatter(new MyXmlFormatter())                    // Default: DefaultXmlFormatter
        // .throwableFormatter(new MyThrowableFormatter())        // Default: DefaultThrowableFormatter
        // .threadFormatter(new MyThreadFormatter())              // Default: DefaultThreadFormatter
        // .stackTraceFormatter(new MyStackTraceFormatter())      // Default: DefaultStackTraceFormatter
        // .borderFormatter(new MyBoardFormatter())               // Default: DefaultBorderFormatter
        // .addObjectFormatter(AnyClass.class,                    // Add formatter for specific class of object
        //         new AnyClassObjectFormatter())                     // Use Object.toString() by default
        // .addInterceptor(new BlacklistTagsFilterInterceptor(    // Add blacklist tags filter
        //         "blacklist1", "blacklist2", "blacklist3"))
        // .addInterceptor(new MyInterceptor())                   // Add other log interceptor

        // .enableBorder()                                        // Enable border, disabled by default
        // .jsonFormatter(new MyJsonFormatter())                  // Default: DefaultJsonFormatter
        // .xmlFormatter(new MyXmlFormatter())                    // Default: DefaultXmlFormatter
        // .throwableFormatter(new MyThrowableFormatter())        // Default: DefaultThrowableFormatter
        // .threadFormatter(new MyThreadFormatter())              // Default: DefaultThreadFormatter
        // .stackTraceFormatter(new MyStackTraceFormatter())      // Default: DefaultStackTraceFormatter
        // .borderFormatter(new MyBoardFormatter())               // Default: DefaultBorderFormatter
        // .addObjectFormatter(AnyClass.class,                    // Add formatter for specific class of object
        //         new AnyClassObjectFormatter())                     // Use Object.toString() by default
        // .addInterceptor(new BlacklistTagsFilterInterceptor(    // Add blacklist tags filter
        //         "blacklist1", "blacklist2", "blacklist3"))
        // .addInterceptor(new MyInterceptor())                   // Add other log interceptor
        val androidPrinter: Printer = AndroidPrinter(true) // Printer that print the log using android.util.Log

        // Printer consolePrinter = new ConsolePrinter();             // Printer that print the log to console using System.out
        // Printer consolePrinter = new ConsolePrinter();             // Printer that print the log to console using System.out
        // val filePrinter: Printer = FilePrinter.Builder(Constants.PATH_LOG) // Specify the directory path of log file(s)
        //         .fileNameGenerator(DateFileNameGenerator()) // Default: ChangelessFileNameGenerator("log")
        //         //.backupStrategy(new NeverBackupStrategy())             // Default: FileSizeBackupStrategy(1024 * 1024)
        //         .cleanStrategy(FileLastModifiedCleanStrategy(MAX_TIME)) // Default: NeverCleanStrategy()
        //         //.flattener(new MyFlattener())                          // Default: DefaultFlattener
        //         .build()

        XLog.init( // Initialize XLog
                config,  // Specify the log configuration, if not specified, will use new LogConfiguration.Builder().build()
                androidPrinter)
            // ,
            //     filePrinter)
    }
}