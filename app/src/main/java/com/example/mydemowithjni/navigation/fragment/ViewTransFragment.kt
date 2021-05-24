package com.example.mydemowithjni.navigation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.elvishew.xlog.XLog
import com.example.mydemowithjni.R


/**
 * A simple [Fragment] subclass.
 * Use the [ViewTransFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ViewTransFragment : Fragment() {

    val args: ViewTransFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nav_view_trans, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fromName = args.fromName
        val otherArgs = args.otherArg

        XLog.i("fromName = $fromName ; otherArgs = $otherArgs")
    }

}