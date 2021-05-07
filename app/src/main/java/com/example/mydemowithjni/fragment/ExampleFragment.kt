package com.example.mydemowithjni.fragment

import android.app.ProgressDialog.show
import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import com.example.mydemowithjni.R
import kotlinx.android.synthetic.main.fragment_example.*

class ExampleFragment : Fragment(R.layout.fragment_example) {
   // lateinit var exitTransition:Transition
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // val inflater = TransitionInflater.from(requireContext())
        // exitTransition = inflater.inflateTransition(R.transition.fade)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //the appropriate Bundle getter methods can be used to retrieve each argument.
        val someInt = requireArguments().getInt("some_int")

        super.onViewCreated(view, savedInstanceState)
        // 为每个要共享的view设置唯一的name, 方便跳转的fragment从map中获取.
        // 使用 ViewCompat.setTransitionName()设置name
        // 也可以在xml中定义
        // ViewCompat.setTransitionName(anim1Iv, "anim1Iv")

        // anim1Iv.setOnClickListener {
        //     parentFragmentManager.commit {
        //         addSharedElement(it, "anim1Iv")
        //         parentFragmentManager.findFragmentByTag("tag")?.let { hide(it) }
        //         if (parentFragmentManager.findFragmentByTag("trans") == null) {
        //             add<TransitionFragment>(R.id.fragContainer, tag = "trans")
        //         } else {
        //             parentFragmentManager.findFragmentByTag("trans")?.let { show(it) }
        //         }
        //         addToBackStack(null)
        //     }
        // }
    }

}