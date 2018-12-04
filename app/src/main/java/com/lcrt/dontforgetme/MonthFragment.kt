package com.lcrt.dontforgetme

import android.content.Intent
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
import kotlin.collections.ArrayList

// ToDo: pass this extra with the selected date to SpecificDateActivity
internal const val EXTRA_SPECIFIC_DATE = "com.lcrt.dontforgetme.SPECIFIC_DATE"


class MonthFragment : Fragment() {
    val events = ArrayList<EventDay>()
    var calendarToCheck = ArrayList<Calendar>()
    private lateinit var UsersDB: DataBaseHelperProject

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_month, container, false)

    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        UsersDB = DataBaseHelperProject(activity)
        // ToDo: Get dates of months from database
       // val calendar = Calendar.getInstance()
        //var cur = UsersDB.getMonthTask((calendar.get(Calendar.MONTH)+1).toString())
        var cur = UsersDB.allTasks
        //calendar.set(2018,10,15)
        if(cur.moveToFirst()){
            do {
                var fechaInit = cur.getString(5).substring(0,10).split('-')
                var cal = Calendar.getInstance()
              //  Log.d("Calendar",fechaInit.toString())
                cal.set(fechaInit[0].toInt(),fechaInit[1].toInt()-1,fechaInit[2].toInt())
                //Log.d("Calendar",cal.toString())
                calendarToCheck.add(cal)
                events.add(EventDay(cal, R.mipmap.alarm))
                /*
                Log.d("Calendar -Query", cur.getInt(0).toString())//id Task
                Log.d("Calendar -Query", cur.getInt(1).toString())//id Project
                Log.d("Calendar -Query", cur.getString(2))//Nombre
                Log.d("Calendar -Query", cur.getString(3))//Prioridad
                Log.d("Calendar -Query", cur.getString(4))//Ubicacion
                Log.d("Calendar -Query", cur.getString(5))//FechaInicio
                Log.d("Calendar -Query", cur.getString(6))//Fecha fin
                Log.d("Calendar -Query", cur.getString(7))//Aviso
                Log.d("SEPARADOR", "---------------------")*/
            }while (cur.moveToNext())
        }


        monthCalendar.setEvents(events)


        monthCalendar.setOnDayClickListener { eventDay ->
            //ToDo: Check if a clicked day have a task
            if(calendarToCheck.contains(eventDay.calendar)){
               // val curSpecific =   UsersDB.getSpecificDateTask(eventDay.calendar.get(Calendar.YEAR).toString(),(eventDay.calendar.get(Calendar.MONTH)+1).toString(),eventDay.calendar.get(Calendar.DAY_OF_MONTH).toString())
                val intent = Intent(activity,SpecificDateActivity::class.java).apply {
                    putExtra(EXTRA_SPECIFIC_DATE, sqliteDateFormat.format(eventDay.calendar.time))

                }
                startActivity(intent)
            }

        }




    }

    companion object {
        @JvmStatic
        fun newInstance() =
                MonthFragment()
    }
}
