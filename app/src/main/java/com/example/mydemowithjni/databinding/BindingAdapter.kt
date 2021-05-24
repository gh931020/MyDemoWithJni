package com.example.mydemowithjni.databinding

import android.annotation.TargetApi
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.ImageView
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.*
import androidx.databinding.BindingAdapter
import androidx.databinding.adapters.ListenerUtil
import com.example.mydemowithjni.R
import org.w3c.dom.Text

object BindingAdapter {

    /**
     * 第一个参数用于确定与特性关联的视图类型
     * 第二个参数用于确定在给定特性的绑定表达式中接受的类型。
     */
    // @BindingAdapter("android:paddingLeft")
    // @JvmStatic fun setPaddingLeft(view: View, padding: Int) {
    //     view.setPadding(
    //         padding,
    //         view.paddingTop,
    //         view.paddingRight,
    //         view.paddingBottom
    //     )
    // }

    /**
     * 这种方式,只有同时设置imageUrl和error时,才会调用loadImage方法
     */
    @BindingAdapter("imageUrl", "error")
    @JvmStatic fun loadImage(view: ImageView, url: String, error: Drawable) {
        // Picasso.get().load(url).error(error).into(view)
        // <ImageView app:imageUrl="@{venue.imageUrl}" app:error="@{@drawable/venueError}" />
    }

    /**
     * 如果您希望在设置了任意属性时调用适配器
     */
    @BindingAdapter(value = ["imageUrl", "placeholder"], requireAll = false)
    @JvmStatic fun setImageUrl(imageView: ImageView, url: String?, placeHolder: Drawable?) {
        if (url == null) {
            imageView.setImageDrawable(placeHolder)
        } else {
            // MyImageLoader.loadInto(imageView, url, placeHolder)
        }
    }

    /**
     * 绑定适配器方法可以选择性在处理程序中使用旧值。
     * 同时获取旧值和新值的方法应该先为属性声明所有旧值，然后再声明新值
     */
    @BindingAdapter("android:paddingLeft")
    @JvmStatic fun setPaddingLeft(view: View, oldPadding: Int, newPadding: Int) {
        if (oldPadding != newPadding) {
            view.setPadding(
                newPadding,
                view.paddingTop,
                view.paddingEnd,
                view.paddingBottom
            )
        }
    }

    /**
     * 只能与具有一种抽象方法的接口或抽象类一起使用
     */
    @BindingAdapter("android:onLayoutChange")
    @JvmStatic fun setOnLayoutChangeListener(
        view: View,
        oldValue: View.OnLayoutChangeListener?,
        newValue: View.OnLayoutChangeListener?
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (oldValue != null) {
                view.removeOnLayoutChangeListener(oldValue)
            }
            if (newValue != null) {
                view.addOnLayoutChangeListener(newValue)
            }
        }
    }
    //<View android:onLayoutChange="@{() -> handler.layoutChanged()}"/>

    /**
     * 当监听器有多个方法时，必须将它拆分为多个监听器。例如，View.OnAttachStateChangeListener 有两个方法：
     * onViewAttachedToWindow(View) 和 onViewDetachedFromWindow(View)。
     * 拆分为:interface OnViewDetachedFromWindow
     *       interface OnViewAttachedToWindow
     */
    @BindingAdapter(
        "android:onViewDetachedFromWindow",
        "android:onViewAttachedToWindow", requireAll = false
    )
    @JvmStatic fun setListener(
        view: View,
        detach: OnViewDetachedFromWindow?,
        attach: OnViewAttachedToWindow?
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1){
            val newListener: View.OnAttachStateChangeListener?
            newListener = if (detach == null && attach == null){
                null
            }else{
                object : View.OnAttachStateChangeListener{
                    override fun onViewDetachedFromWindow(v: View) {
                        detach?.onViewDetachedFromWindow(v)
                    }

                    override fun onViewAttachedToWindow(v: View) {
                        attach?.onViewAttachedToWindow(v)
                    }

                }
            }

            val oldListener: View.OnAttachStateChangeListener? =
                ListenerUtil.trackListener(view, newListener, R.id.onAttachStateChangeListener)
            if (oldListener != null) {
                view.removeOnAttachStateChangeListener(oldListener)
            }
            if (newListener != null) {
                view.addOnAttachStateChangeListener(newListener)
            }
        }
    }

    /**
     * 绑定表达式中提供的值类型必须保持一致。您不能在同一个表达式中使用不同的类型
     */
    @BindingConversion
    @JvmStatic fun convertColorToDrawable(color: Int) = ColorDrawable(color)


    /**
     * 双向绑定自定义Adapter
     * @param view TextView
     * @param newValue String
     */
    @BindingAdapter("time")
    @JvmStatic fun setTime(view:TextView, newValue: String){
        if (!view.text.equals(newValue)){
            view.text = newValue
        }
    }

    @InverseBindingAdapter(attribute = "time")
    @JvmStatic fun getTime(view: TextView): String{
        return view.text.toString()
    }

    @BindingAdapter("app:timeAttrChanged")
    @JvmStatic fun setListeners(
        view: TextView,
        attrChange: InverseBindingListener
    ) {
        // Set a listener for click, focus, touch, etca
        //这个方法会回调getTime方法
        attrChange.onChange()
    }

    //转换器
    /**
     * 通过向其中一个转换器添加 @InverseMethod 注释并让此注释引用反向转换器来完成的。
     * @param view EditText
     * @param oldValue Long
     * @param value Long
     * @return String
     */
    // @InverseMethod("stringToDate")
    // @JvmStatic fun dateToString(view: EditText, oldValue: Long, value: Long): String{
    //     // Converts long to String
    //     return "string"
    // }
    //
    // fun stringToDate(view: EditText, oldValue: String, value: String): Long{
    //     // Converts String to Long
    //     return 0L
    // }
}

// Translation from provided interfaces in Java:
@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
interface OnViewDetachedFromWindow {
    fun onViewDetachedFromWindow(v: View)
}

@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
interface OnViewAttachedToWindow {
    fun onViewAttachedToWindow(v: View)
}