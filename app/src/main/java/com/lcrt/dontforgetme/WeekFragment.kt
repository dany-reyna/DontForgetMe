package com.lcrt.dontforgetme

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_week.*

class WeekFragment : Fragment() {

    private val mTasks = ArrayList<Task>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_week, container, false)
        //initLists()
        return view
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initLists()
    }
    private fun initLists() {
        // ToDo: read DB to add items to mTasks, get all tasks from Now to +7 days

        // ToDo: Delete sample tasks below
        mTasks.add(Task(1, "Reunión", "Baja", "Calle YYY",
                "2018-10-15 13:00", "2018-10-16 13:00",
                "1 hora antes",
                1, "Proyecto 1", "Morado"))

        mTasks.add(Task(2, "Entrega", "Alta", "Av. ZZZ",
                "2018-10-15 15:30", "2018-10-16 15:30",
                "2 horas antes",
                2, "Proyecto 2", "Naranja"))

        mTasks.add(Task(3, "Entrega", "Baja", "Calle XXX",
                "2018-10-15 17:15", "2018-10-16 17:30",
                "1 dia antes",
                2, "Proyecto 2", "Azul"))


        mTasks.add(Task(4, "Revisión", "Media", "Calle YYY",
                "2018-10-15 18:20", "2018-10-16 20:30",
                "1 semana antes",
                3, "Proyecto 3", "Cafe"))

        mTasks.add(Task(5, "Reunión", "Baja", "Calle YYY",
                "2018-10-15 18:20", "2018-10-16 20:30",
                "1 semana antes",
                4, "Proyecto 4", "Morado"))

        mTasks.add(Task(6, "Entrega", "Alta", "Av. ZZZ",
                "2018-10-16 15:30", "2018-10-17 15:30",
                "1 semana antes",
                4, "Proyecto 4", "Naranja"))

        mTasks.add(Task(7, "Entrega", "Baja", "Calle XXX",
                "2018-10-16 17:15", "2018-10-17 17:15",
                "1 semana antes",
                4, "Proyecto 4", "Azul"))

        mTasks.add(Task(8, "Revisión", "Media", "Calle YYY",
                "2018-10-16 18:20", "2018-10-17 18:20",
                "1 semana antes",
                5, "Proyecto 5", "Cafe"))

        mTasks.add(Task(9, "Reunión", "Baja", "Calle YYY",
                "2018-10-17 13:00", "2018-10-18 13:00",
                "1 semana antes",
                5, "Proyecto 5", "Morado"))

        mTasks.add(Task(10, "Entrega", "Alta", "Av. ZZZ",
                "2018-10-17 15:30", "2018-10-18 15:30",
                "1 semana antes",
                5, "Proyecto 5", "Naranja"))

        mTasks.add(Task(11, "Entrega", "Baja", "Calle XXX",
                "2018-10-17 17:15", "2018-10-18 17:15",
                "1 semana antes",
                5, "Proyecto 5", "Azul"))

        mTasks.add(Task(12, "Revisión", "Media", "Calle YYY",
                "2018-10-17 18:20", "2018-10-18 18:20",
                "1 semana antes",
                6, "Proyecto 6", "Cafe"))

        initRecyclerView()
    }

    private fun initRecyclerView() {
        val adapter = RecyclerViewAdapterTask(activity as Context, mTasks)
        recycler_view_week.adapter = adapter
        recycler_view_week.layoutManager = LinearLayoutManager(activity)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
                WeekFragment()
    }
}
