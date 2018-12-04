package com.lcrt.dontforgetme

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_specific_date.*

class SpecificDateActivity : AppCompatActivity() {

    private val mTasks = ArrayList<Task>()
    private lateinit var UsersDB: DataBaseHelperProject
    private lateinit var specificDate: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_specific_date)
        UsersDB = DataBaseHelperProject(this)
        if (intent.hasExtra(EXTRA_SPECIFIC_DATE)) {
            //ToDo: assign date as title -> supportActionBar?.title = intent.getStringExtra(EXTRA_SPECIFIC_DATE)
            specificDate = intent.getStringExtra(EXTRA_SPECIFIC_DATE)
            supportActionBar?.title = specificDate
        }

        initLists()
    }

    private fun initLists() {
        // ToDo: read DB to add items from SPECIFIC DATE to mTasks
        val datosTasks = UsersDB.getSpecificDateTask(specificDate)
        // ToDo: Delete sample tasks below
        if(datosTasks.moveToFirst()){
            do{

                mTasks.add(Task(datosTasks.getInt(0), datosTasks.getString(1), datosTasks.getString(2),
                        datosTasks.getString(3),
                        datosTasks.getString(4), datosTasks.getString(5),
                        datosTasks.getString(6),
                        datosTasks.getInt(7), datosTasks.getString(8), datosTasks.getString(9)))
            }while(datosTasks.moveToNext())
        }
        /*mTasks.add(Task(1, "Reunión", "Baja", "Calle YYY",
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
        */
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val adapter = RecyclerViewAdapterTask(this, mTasks)
        recycler_view_specific_date.adapter = adapter
        recycler_view_specific_date.layoutManager = LinearLayoutManager(this)
    }
}
