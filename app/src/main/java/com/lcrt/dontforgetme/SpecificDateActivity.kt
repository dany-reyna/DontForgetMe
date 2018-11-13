package com.lcrt.dontforgetme

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_specific_date.*

class SpecificDateActivity : AppCompatActivity() {

    private val mTasks = ArrayList<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_specific_date)

        if (intent.hasExtra(EXTRA_SPECIFIC_DATE)) {
            //ToDo: assign date as title -> supportActionBar?.title = intent.getStringExtra(EXTRA_SPECIFIC_DATE)
        }

        initLists()
    }

    private fun initLists() {
        // ToDo: read DB to add items from SPECIFIC DATE to mTasks

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

        initRecyclerView()
    }

    private fun initRecyclerView() {
        val adapter = RecyclerViewAdapterTask(this, mTasks)
        recycler_view_specific_date.adapter = adapter
        recycler_view_specific_date.layoutManager = LinearLayoutManager(this)
    }
}
