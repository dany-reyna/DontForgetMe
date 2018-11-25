package com.lcrt.dontforgetme

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.EventDay
import kotlinx.android.synthetic.main.fragment_month.*
import java.util.*

// ToDo: pass this extra with the selected date to SpecificDateActivity
internal const val EXTRA_SPECIFIC_DATE = "com.lcrt.dontforgetme.SPECIFIC_DATE"

class MonthFragment : Fragment() {
    val events = ArrayList<EventDay>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_month, container, false)

    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // ToDo: Get dates of months from database
        val calendar = Calendar.getInstance()
        calendar.set(2018,10,15)
        events.add(EventDay(calendar, R.mipmap.alarm))


        monthCalendar.setEvents(events)

        monthCalendar.setOnDayClickListener { eventDay ->
            //ToDo: Check if a clicked day have a task
            Log.d("Clicked",eventDay.calendar.get(Calendar.DAY_OF_MONTH).toString().plus(eventDay.calendar.get(Calendar.MONTH).toString().plus(eventDay.calendar.get(Calendar.YEAR).toString())))

        }


       /* monthCalendar.setOnDayClickListener {
            fun onDayClick(eventDay: EventDay){
                val clickedDayCalendar = eventDay.calendar
                //ToDo: Check if a clicked day have a task
                Log.d("Clicked",eventDay.calendar.toString())
            }
        }*/

    }

    companion object {
        @JvmStatic
        fun newInstance() =
                MonthFragment()
    }
}
