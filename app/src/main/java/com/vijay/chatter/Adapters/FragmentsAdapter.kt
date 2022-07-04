package com.vijay.chatter.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.vijay.chatter.Fragments.CallsFragment
import com.vijay.chatter.Fragments.ChatsFragment
import com.vijay.chatter.Fragments.StatusFragment

class FragmentsAdapter(fm: FragmentManager): FragmentPagerAdapter(fm)  {
    private val count = 3

    override fun getCount(): Int {
        return count
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 ->{
                ChatsFragment()
            }
            1 ->{
                StatusFragment()
            }
            2 ->{
                CallsFragment()
            }else -> ChatsFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> {
                "Chats"
            }
            1 -> {
                "Status"
            }
            else -> {
                "Calls"
            }
        }
        return super.getPageTitle(position)
    }
}