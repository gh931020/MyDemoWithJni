package com.example.mydemowithjni.animation

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import java.net.MalformedURLException

/*
属性动画
    ValueAnimator: 跟踪动画的时间, ex: 动画的已运行时长一级正在添加动画效果的属性的当前值
        TimeInterpolator: 定义动画插值器,插值器会改变动画的过程,快慢缓急...指定如何根据时间计算动画中的特定值
        TypeEvaluator: 定义如何计算正在添加动画效果的属性的值
        ValueAnimator.ofFloat(-1f,100f).apply {
             duration = 999
             addUpdateListener {
                 it.animatedValue // 获取当前动画的进度值
             }
             start()
         }
    ObjectAnimator:ValueAnimator的子类
        *要添加动画效果的对象属性必须具有set<PropertyName>()形式的setter函数.
        *如果在参数中仅指定了一个值,则系统会假定它时结束值.
        *根据您要添加动画效果的属性或对象，您可能需要对视图调用 invalidate() 方法，以强制屏幕使用添加动画效果之后的值重新绘制自身。
          您可以在 onAnimationUpdate() 回调中执行此操作。
        ObjectAnimator.ofFloat(textView, "translationX", 100f).apply {
              duration = 1000
              start
        }
    AnimatorSet:可指定是同时播放动画,按顺序播放,还是在指定的延迟时间后播放.
        val bouncer = AnimatorSet().apply {
            play(bounceAnim).before(squashAnim1)
            play(squashAnim1).with(squashAnim2)
            play(squashAnim1).with(tretchAnim1)
            play(squashAnim1).with(tretchAnim2)
            play(bounceBackAnim).after(stretchAnim2)
        }
        val fadeAnim = ObjectAnimator.ofFloat(newBall, "alpha",1f, 0f).apply {
            duration = 250
            addListener(object: AnimatorListenerAdapter(){
                override fun onAnimationEnd(animation: Animator){
                    balls.remove((animation as ObjectAnimator).target)
                }
            })
        }
        AnimatorSet().apply {
            play(bouncer).before(fadeAnim)
            start()
        }
    监听器: AnimatorListenerAdapter
 */
/*
为ViewGroup对象的布局更改添加动画效果.
    开启默认效果: android:animateLayoutChanges="true"
    使用StateListAnimator为视图状态更改添加动画效果
        使用<selector>元素和<item>在XML资源中定义StateListAnimator.每个元素都指定一个由StateListAnimator类定义的不同视图状态.
        每个<item>都包含一个属性动画集   res/xml/animate_scale.xml
        在要使用的控件的xml中添加android:stateListAnimator="@xml/animate_scale"
    可以使用AnimatedStateListDrawable在状态更改间播放可绘制动画,而不是为视图的属性添加动画效果
        在res/drawable/myanimstatedrawable.xml中定义资源
 */
/*
使用ViewPropertyAnimator添加动画效果
    使用多个ObjectAnimator对象
    val animX = ObjectAnimator.ofFloat(myView, "x", 50f)
    val animY = ObjectAnimator.ofFloat(myView, "y", 100f)
    AnimatorSet().apply {
        playTogether(animX, animY)
        start()
    }

    使用一个ObjectAnimator+ PropertyValueHolder
    val pvhX = PropertyValuesHolder.ofFloat("x", 50f)
    val pvhY = PropertyValuesHolder.ofFloat("y", 100f)
    ObjectAnimator.ofPropertyValuesHolder(myView, pvhX, pvhY).start()

    ViewPropertyAnimator
    myView.animate().x(50f).y(100f)
 */
fun main() {

}