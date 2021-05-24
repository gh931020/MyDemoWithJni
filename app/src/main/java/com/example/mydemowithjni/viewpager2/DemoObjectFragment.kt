package com.example.mydemowithjni.viewpager2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mydemowithjni.R
import com.example.mydemowithjni.databinding.FragmentCollectionObjectBinding

class DemoObjectFragment: Fragment() {
    lateinit var mBinding: FragmentCollectionObjectBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentCollectionObjectBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.takeIf {
            it.containsKey(ARG_OBJECT)
        }?.apply {
            mBinding.collectionTv.text = getInt(ARG_OBJECT).toString()
        }
    }
}