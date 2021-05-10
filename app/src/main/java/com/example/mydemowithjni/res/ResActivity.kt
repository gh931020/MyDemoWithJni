package com.example.mydemowithjni.res

import android.animation.Animator
import android.animation.AnimatorInflater
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Animatable
import android.graphics.drawable.ClipDrawable
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.view.animation.AnimationUtils
import com.example.mydemowithjni.BaseActivity
import com.example.mydemowithjni.R
import com.example.mydemowithjni.databinding.ActivityResBinding

class ResActivity: BaseActivity<ActivityResBinding>() {

    companion object{
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, ResActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun restroeState(savedInstanceState: Bundle?) {

    }

    @SuppressLint("ResourceType")
    override fun initData() {
        //使用属性动画
        val set: Animator? = AnimatorInflater.loadAnimator(this,
            R.anim.property_animator
        )
            .apply {
                setTarget(viewBinding.sampleText)
                start()
            }
        // 设置Tween Animator
        val hyperspace_jump = AnimationUtils.loadAnimation(this,
            R.anim.hyperspace_jump
        )
        viewBinding.imageView.startAnimation(hyperspace_jump)
        // 设置帧动画
        viewBinding.imageViewAnim.setBackgroundResource(R.drawable.rocket)
        val background = viewBinding.imageViewAnim.background
        if (background is Animatable){
            background.start()
        }
        var level = 0
        Thread(Runnable {
            while (true){
                Thread.sleep(1000)
                runOnUiThread {
                    viewBinding.levelListIv.setImageLevel(level%4)
                    level+=1
                }
            }
        }).start()

        val drawable = viewBinding.transitionIv.drawable
        if (drawable is TransitionDrawable){
            drawable.startTransition(500)
        }

        //默认级别为 0，即完全裁剪，使图像不可见。当级别为 10,000 时，图像不会裁剪，而是完全可见。
        //注意background和src的区别
        val clipBackground = viewBinding.clipIv.background
        if (clipBackground is ClipDrawable){
            clipBackground.level = clipBackground.level + 7000
        }


    }

    override fun initViewBinding(): ActivityResBinding = ActivityResBinding.inflate(layoutInflater)

    override fun setListener() {
    }
}