package com.example.mydemowithjni.viewpager2

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter


class DemoCollectionAdapter(activity: ViewPage2Activity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 10

    override fun createFragment(position: Int): Fragment {
        // returen a new fragment instance in createFragment(int)
        val fragment = DemoObjectFragment()
        fragment.arguments = Bundle().apply {
            putInt(ARG_OBJECT, position +1)
        }
        return fragment
    }
}