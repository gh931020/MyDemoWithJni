package com.example.mydemowithjni.touch

import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.VelocityTracker
import androidx.core.view.GestureDetectorCompat
import com.elvishew.xlog.XLog
import com.example.mydemowithjni.BaseActivity
import com.example.mydemowithjni.databinding.ActivityTouchBinding

class TouchActivity: BaseActivity<ActivityTouchBinding>() {

    private lateinit var mDetector: GestureDetectorCompat

    /**
     * 跟踪触摸事件的速度
     */
    private var mVelocityTracker: VelocityTracker ?= null

    override fun initViewBinding(): ActivityTouchBinding = ActivityTouchBinding.inflate(layoutInflater)

    override fun restroeState(savedInstanceState: Bundle?) {

    }

    override fun setListener() {

    }

    override fun initData() {
        mDetector = GestureDetectorCompat(this , gestureListener)
        mDetector.setOnDoubleTapListener(doubleTapListener)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        // val action: Int = MotionEventCompat.getActionMasked(event)
        // 将触摸事件交给detector处理
        // return if (mDetector.onTouchEvent(event)){
        //     true
        // }else{
        //     super.onTouchEvent(event)
        // }


        return when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                XLog.i("MotionEvent.ACTION_DOWN")
                // 将tracker置为初始状态
                mVelocityTracker?.clear()
                // 按需要获取新的tracker
                mVelocityTracker = mVelocityTracker?: VelocityTracker.obtain()
                // add a user's movement to the tracker
                mVelocityTracker?.addMovement(event)
                true
            }
            MotionEvent.ACTION_MOVE -> {
                XLog.i("MotionEvent.ACTION_MOVE")
                // 根据tracker处理事件
                mVelocityTracker?.apply {
                    val pointerId = event.getPointerId(event.actionIndex)
                    addMovement(event)
                    // When you want to determine the velocity, call
                    // computeCurrentVelocity(). Then call getXVelocity()
                    // and getYVelocity() to retrieve the velocity for each pointer ID.
                    computeCurrentVelocity(1000)
                    // Log velocity of pixels per second
                    // Best practice to use VelocityTrackerCompat where possible.
                    XLog.d("", "X velocity: ${getXVelocity(pointerId)}")
                    XLog.d("", "Y velocity: ${getYVelocity(pointerId)}")
                }
                true
            }
            MotionEvent.ACTION_UP -> {
                XLog.i("MotionEvent.ACTION_UP")
                // Return a VelocityTracker object back to be re-used by others
                mVelocityTracker?.recycle()
                mVelocityTracker = null
                true
            }
            MotionEvent.ACTION_CANCEL -> {
                XLog.i("MotionEvent.ACTION_CANCEL")
                true
            }
            MotionEvent.ACTION_OUTSIDE -> {
                XLog.i("Movement occurred outside bounds of current screen element")
                true
            }
            else -> {
                super.onTouchEvent(event)
            }
        }
    }

    val gestureListener = object : GestureDetector.OnGestureListener{
        override fun onDown(event: MotionEvent): Boolean {
            XLog.d("onDown: $event")
            return true
        }

        override fun onShowPress(event: MotionEvent) {
            XLog.d("onShowPress: $event")
        }

        override fun onSingleTapUp(event: MotionEvent): Boolean {
            XLog.d("onSingleTapUp: $event")
            return true
        }

        override fun onScroll(
            event1: MotionEvent,
            event2: MotionEvent,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            XLog.d("onScroll: $event1 $event2")
            return true
        }

        override fun onLongPress(event: MotionEvent) {
            XLog.d("onLongPress: $event")
        }

        override fun onFling(
            event1: MotionEvent,
            event2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            XLog.d("onFling: $event1 $event2")
            return true
        }

    }

    val doubleTapListener = object : GestureDetector.OnDoubleTapListener{
        override fun onSingleTapConfirmed(event: MotionEvent): Boolean {
            XLog.d("onSingleTapConfirmed: $event")
            return true
        }

        override fun onDoubleTap(event: MotionEvent): Boolean {
            XLog.d("onDoubleTap: $event")
            return true
        }

        override fun onDoubleTapEvent(event: MotionEvent): Boolean {
            XLog.d("onDoubleTapEvent: $event")
            return true
        }
    }

    /**
     * GestureDetector.SimpleOnGestureListener 通过针对所有 on<TouchEvent> 方法返回 false，提供对所有这些方法的实现。
     * 因此，您可以仅替换您关注的方法。
     * 例如，以下代码段会创建一个扩展 GestureDetector.SimpleOnGestureListener 的类并替换 onFling() 和 onDown()。
     * 无论您是否使用 GestureDetector.OnGestureListener，最佳做法都是实现返回 true 的 onDown() 方法。
     * 这是因为所有手势都以 onDown() 消息开头。
     *
     */
    private class MySimpleGestureListener : GestureDetector.SimpleOnGestureListener(){
        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent?,
            velocityX: Float,
            velocityY: Float
        ): Boolean {

            return true
        }
    }
}