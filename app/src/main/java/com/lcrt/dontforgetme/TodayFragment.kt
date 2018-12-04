package com.lcrt.dontforgetme

import android.content.Context
import android.database.Cursor
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_today.*
import java.util.*
import kotlin.collections.ArrayList

class TodayFragment : Fragment() {

    private val mTasks = ArrayList<Task>()
    private lateinit var UsersDBP: DataBaseHelperProject
    private val projectlist = ArrayList<Project>()
    private val tasklist = ArrayList<TaskAux>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_today, container, false)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        UsersDBP = DataBaseHelperProject(activity)
        initLists()
    }

    private fun initLists() {
        // ToDo: read DB to add items to mTasks list, get all tasks up to Today
        /*private static final String TABLE_NAME = "Tasks";
        private static final String COL0 = "Id_Task";
        private static final String COL1 = "Id_Project";
        private static final String COL2 = "Name";
        private static final String COL3 = "Priority";
        private static final String COL4 = "Location";
        private static final String COL5 = "Init_Date";
        private static final String COL6 = "Final_Date";
        private static final String COL7 = "NotificationTime";*/

        var datosTasks : Cursor = UsersDBP.todayTasks
        //var datosProjects : Cursor = UsersDBP.allProjects
        if(datosTasks.moveToFirst()){
            do{
                //Log.d("COLOR",datosTasks.getString(0));Log.d("COLOR",datosTasks.getString(1));Log.d("COLOR",datosTasks.getString(2));Log.d("COLOR",datosTasks.getString(3));Log.d("COLOR",datosTasks.getString(4));Log.d("COLOR",datosTasks.getString(5));Log.d("COLOR",datosTasks.getString(6));Log.d("COLOR",datosTasks.getString(7));Log.d("COLOR",datosTasks.getString(8));Log.d("COLOR",datosTasks.getString(9));
                mTasks.add(Task(datosTasks.getInt(0), datosTasks.getString(1), datosTasks.getString(2),
                        datosTasks.getString(3),
                        datosTasks.getString(4), datosTasks.getString(5),
                        datosTasks.getString(6),
                        datosTasks.getInt(7), datosTasks.getString(8), datosTasks.getString(9)))

            }while (datosTasks.moveToNext())
        }










        /*if (datosTasks.moveToFirst()){
            //datosProjects.moveToFirst()
            //datosTasks.moveToFirst()
            do{
                projectlist.add(Project(datosProjects.getInt(0),
                        datosProjects.getString(1),datosProjects.getString(5),
                        datosProjects.getString(2), datosProjects.getString(3),
                        datosProjects.getString(4)))
            }while (datosProjects.moveToNext())
            do{
                tasklist.add(TaskAux(datosTasks.getInt(0), datosTasks.getString(2),datosProjects.getString(3),
                        datosProjects.getString(4), datosProjects.getString(5), datosTasks.getString(6), datosTasks.getString(7),
                        datosTasks.getInt(1)))
            }while (datosTasks.moveToNext())
            tasklist.forEach {
                var tarea = it
                projectlist.forEach {
                    var proyecto = it
                    if(tarea.linkedProjectId == proyecto.id){
                        mTasks.add(Task(tarea.id,tarea.name,tarea.priority,tarea.location,tarea.startDateTime,tarea.endDateTime,tarea.notificationTime,tarea.linkedProjectId,proyecto.name,proyecto.color))
                    }
                }
                Log.d("PRUEBA", mTasks.toString());
            }
        }else{
            mTasks.add(Task(0, "No hay tareas disponibles", "Baja", "-",
                    "0000-00-00 00:00", "0000-00-00 00:00",
                    "-",
                    0, "-", "Morado"))
        }*/
        // ToDo: Delete sample tasks below
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
        val adapter = RecyclerViewAdapterTask(activity as Context, mTasks)
        recycler_view_today.adapter = adapter
        recycler_view_today.layoutManager = LinearLayoutManager(activity)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
                TodayFragment()
    }
}
