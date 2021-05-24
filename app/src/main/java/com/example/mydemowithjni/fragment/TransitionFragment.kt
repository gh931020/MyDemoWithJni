package com.example.mydemowithjni.fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import com.example.mydemowithjni.R

class TransitionFragment: Fragment(R.layout.fragment_transition) {
    lateinit var inflateTransition: Transition
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inflateTransition =
            TransitionInflater.from(requireContext()).inflateTransition(R.transition.shared_image)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // ViewCompat.setTransitionName(transAnim1Iv, "anim1Iv")
    }

}