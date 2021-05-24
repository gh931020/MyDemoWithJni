package com.example.mydemowithjni.intent

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.gesture.GestureOverlayView
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.*
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.example.mydemowithjni.BaseActivity
import com.example.mydemowithjni.databinding.ActivityIntentBinding
import java.io.File
import java.util.*

const val REQUEST_IMAGE_CAPTURE = 1
const val REQUEST_SELECT_CONTACT = 2
const val REQUEST_SELECT_PHONE_NUMBER = 3
const val REQUEST_IMAGE_GET = 4

/**
 * 隐式intent,当调用startActivity()或startActivityForResult()并传入intent时,系统会将intent解析为可处理该
 * intent的应用并启动其对应的Activity.
 * 如果设备上没有可以接收隐式intent的应用,在调用startActivity的时候会崩溃,需要提前通过调用resolveActivity(),
 * 判断结果是否为空.
 */
class IntentActivity : BaseActivity<ActivityIntentBinding>() {
    companion object{
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, IntentActivity::class.java)
            context.startActivity(starter)
        }
    }
    val locationForPhotos: Uri = Uri.fromFile(
        File(
            Environment
                .getExternalStorageDirectory().getAbsolutePath().toString() + "/pic"
        )
    )

    override fun initViewBinding(): ActivityIntentBinding {
        return ActivityIntentBinding.inflate(layoutInflater)
    }

    override fun restroeState(savedInstanceState: Bundle?) {

    }

    override fun setListener() {

    }

    override fun initData() {

    }

    fun onBtnClick(view: View) {

    }

    /**
     * 创建闹钟,需要添加SET_ALARM权限
     * @param view View
     */
    fun createAlarm(view: View) {
        val intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
            putExtra(AlarmClock.EXTRA_MESSAGE, "我是一个闹钟")
            putExtra(AlarmClock.EXTRA_HOUR, 12)
            putExtra(AlarmClock.EXTRA_MINUTES, 59)
            putExtra(AlarmClock.EXTRA_DAYS, arrayOf(Calendar.SUNDAY, Calendar.MONDAY))
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    /**
     * 创建定时器,需要添加SET_ALARM权限
     * @param view View
     */
    fun createTimer(view: View) {
        val intent = Intent(AlarmClock.ACTION_SET_TIMER).apply {
            putExtra(AlarmClock.EXTRA_MESSAGE, "定时器")
            putExtra(AlarmClock.EXTRA_LENGTH, 100)
            putExtra(AlarmClock.EXTRA_SKIP_UI, true)
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    /**
     * 创建日历事件
     * @param view View
     */
    fun addEvent(view: View) {
        val intent = Intent(Intent.ACTION_INSERT).apply {
            data = CalendarContract.Events.CONTENT_URI
            putExtra(CalendarContract.Events.TITLE, "日历事件")
            putExtra(CalendarContract.Events.EVENT_LOCATION, "公司")
            putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, 151513215321)
            putExtra(CalendarContract.EXTRA_EVENT_END_TIME, 151513216321)
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }

    }

    /**
     * 拍摄照片或视频并将其返回
     * @param view View
     */
    fun capturePhoto(view: View) {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            putExtra(
                MediaStore.EXTRA_OUTPUT,
                Uri.withAppendedPath(locationForPhotos, "photo.jpg")
            )
        }

        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
        }
    }

    /**
     * 以静态图像模式打开相机应用
     */
    fun capturePhoto() {
        val intent = Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA)
        // 视频模式
        // val intent = Intent(MediaStore.INTENT_ACTION_VIDEO_CAMERA)
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val thumbnail = data?.getParcelableExtra<Bitmap>("data")
            // Do other work with full size photo saved in locationForPhotos
        }

        if (requestCode == REQUEST_SELECT_CONTACT && resultCode == RESULT_OK) {
            val contactUri = data?.data
            // Do something with the selected contact at contactUri
        }
        if (requestCode == REQUEST_SELECT_PHONE_NUMBER && resultCode == RESULT_OK) {
            val contactUri: Uri = data?.data!!
            val projection: Array<String> = arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER)
            contentResolver.query(contactUri, projection, null, null, null, null).use { cursor ->
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        val numberIndex =
                            cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                        val number = cursor.getString(numberIndex)
                        // Do something with the phone number
                    }
                }
            }
        }

        if (requestCode == REQUEST_IMAGE_GET && resultCode == RESULT_OK) {
            val thumbnail: Bitmap = data?.getParcelableExtra("data")!!
            val fullPhotoUri: Uri = data.data!!
            // Do work with photo saved at fullPhotoUri
        }
    }

    /**
     * 获取联系人信息
     * @param view View
     */
    fun selectContact(view: View) {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = ContactsContract.Contacts.CONTENT_TYPE
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, REQUEST_SELECT_CONTACT)
        }
    }

    /**
     * 选择特定联系人数据
     */
    fun selectContact() {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE //从有电话号码的联系人中选取
            // ContactsContract.CommonDataKinds.Email.CONTENT_TYPE //从有电子邮件地址的联系人中选取
            // ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_TYPE //从有邮政地址的联系人中选取
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, REQUEST_SELECT_PHONE_NUMBER)
        }
    }

    /**
     * 查看联系人信息
     * @param view View
     */
    fun viewContact(view: View) {
        // ACTION_PICK 返回的联系人Uri
        val contactUri: Uri = Uri.EMPTY
        val intent = Intent(Intent.ACTION_VIEW, contactUri)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    /**
     * 编辑现有联系人信息
     * @param view View
     */
    fun editContact(view: View) {
        // ACTION_PICK 返回的联系人Uri
        val contactUri: Uri = Uri.EMPTY
        val intent = Intent(Intent.ACTION_EDIT).apply {
            data = contactUri
            putExtra(ContactsContract.Intents.Insert.EMAIL, "email")
        }

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    /**
     * 插入联系人
     * @param view View
     */
    fun insertContact(view: View) {
        val intent = Intent(Intent.ACTION_INSERT).apply {
            type = ContactsContract.Contacts.CONTENT_TYPE
            putExtra(ContactsContract.Intents.Insert.NAME, "name")
            putExtra(ContactsContract.Intents.Insert.EMAIL, "email")
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    /**
     * 发送邮件
     * ACTION_SENDTO（适用于不带附件）
     * ACTION_SEND（适用于带一个附件）
     * ACTION_SEND_MULTIPLE（适用于带多个附件）
     * @param view View
     */
    fun composeEmail(view: View) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "*/*"
            // 如果您想确保 Intent 只由电子邮件应用（而非其他短信或社交应用）进行处理，
            // 则需使用 ACTION_SENDTO 操作并加入 "mailto:" 数据架构。
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf("1491389476@qq.com"))
            putExtra(Intent.EXTRA_SUBJECT, "subject")
            putExtra(Intent.EXTRA_STREAM, "")
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    /**
     * 选择相册图片
     * @param view View
     */
    fun selectImage(view: View) {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_GET)
        }
    }

    /**
     * 打开wifi设置页面
     * ACTION_SETTINGS
     * ACTION_WIRELESS_SETTINGS
     * ACTION_AIRPLANE_MODE_SETTINGS
     * ACTION_WIFI_SETTINGS
     * ACTION_APN_SETTINGS
     * ACTION_BLUETOOTH_SETTINGS
     * ACTION_DATE_SETTINGS
     * ACTION_LOCALE_SETTINGS
     * ACTION_INPUT_METHOD_SETTINGS
     * ACTION_DISPLAY_SETTINGS
     * ACTION_SECURITY_SETTINGS
     * ACTION_LOCATION_SOURCE_SETTINGS
     * ACTION_INTERNAL_STORAGE_SETTINGS
     * ACTION_MEMORY_CARD_SETTINGS
     * @param view View
     */
    fun openWifiSettings(view: View) {
        val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
        if (intent.resolveActivity(packageManager) != null){
            startActivity(intent)
        }
    }

    /**
     * 打开指定网页
     * @param view View
     */
    fun openWebPage(view: View) {
        val webpage: Uri = Uri.parse("www.baidu.com")
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }
}