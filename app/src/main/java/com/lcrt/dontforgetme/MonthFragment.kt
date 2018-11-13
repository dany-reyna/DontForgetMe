package com.lcrt.dontforgetme

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

// ToDo: pass this extra with the selected date to SpecificDateActivity
internal const val EXTRA_SPECIFIC_DATE = "com.lcrt.dontforgetme.SPECIFIC_DATE"

class MonthFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_month, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
                MonthFragment()
    }
}
