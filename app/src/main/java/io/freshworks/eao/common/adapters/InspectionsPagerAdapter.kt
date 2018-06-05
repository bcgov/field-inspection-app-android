package io.freshworks.eao.common.adapters

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import io.freshworks.eao.R
import io.freshworks.eao.screens.inspections.inprogress.InProgressFragment
import io.freshworks.eao.screens.inspections.submitted.SubmittedFragment


class InspectionsPagerAdapter(
        fm: FragmentManager,
        private val context: Context
) : FragmentStatePagerAdapter(fm){

    var inProgressFragment: InProgressFragment = InProgressFragment()
    var submittedFragment: SubmittedFragment = SubmittedFragment()

    override fun getItem(position: Int): Fragment? {
        when(position){
            0 -> return inProgressFragment
            1 -> return submittedFragment
        }
        return null
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> context.getString(R.string.in_progress)
            1 -> context.getString(R.string.submitted)
            else -> super.getPageTitle(position)
        }
    }
}
