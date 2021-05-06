package com.example.mydemowithjni.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.mydemowithjni.R

class ExampleFragment : Fragment(R.layout.fragment_example) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //the appropriate Bundle getter methods can be used to retrieve each argument.
        val someInt = requireArguments().getInt("some_int")

        super.onViewCreated(view, savedInstanceState)
    }

}