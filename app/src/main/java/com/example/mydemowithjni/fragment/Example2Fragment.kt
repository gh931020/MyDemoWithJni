package com.example.mydemowithjni.fragment

import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.TransitionInflater
import android.view.View
import androidx.fragment.app.Fragment
import com.example.mydemowithjni.R

class Example2Fragment : Fragment(R.layout.fragment_example2) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        // val enterTransition = inflater.inflateTransition(R.transition.slide_right)
        // enterTransition =  inflater.inflateTransition(R.transition.slide_right)
        // 设置过渡动画
        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.shared_image)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //the appropriate Bundle getter methods can be used to retrieve each argument.
        // val someInt = requireArguments().getInt("some_int")
        super.onViewCreated(view, savedInstanceState)
    }

}