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
import kotlinx.android.synthetic.main.fragment_projects.*

class ProjectsFragment : Fragment() {

    private lateinit var UsersDB: DataBaseHelperProject
    private val mProjects = ArrayList<Project>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_projects, container, false)
        UsersDB = DataBaseHelperProject(activity)
       // initLists()
        return view
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initLists()
    }

    private fun initLists() {
        // ToDo: read DB to add items to mProjects list
        var datos : Cursor = UsersDB.getAllProjects();
        if (datos.moveToFirst()){
            do{
                mProjects.add(Project(datos.getInt(0),datos.getString(1),
                        datos.getString(5),datos.getString(2),datos.getString(3),
                        datos.getString(4)))
            }while (datos.moveToNext());
        }else{
        }
        // ToDo: Delete sample projects below
        /*mProjects.add(Project(1, "Proyecto 1", "Morado", "Cliente 1",
                "Un proyecto", "2018-11-08"))
        mProjects.add(Project(2, "Proyecto 2", "Naranja", "Cliente 2",
                "Un proyecto", "2018-11-08"))
        mProjects.add(Project(3, "Proyecto 3", "Azul", "Cliente 3",
                "Un proyecto", "2018-11-08"))
        mProjects.add(Project(4, "Proyecto 4", "Cafe", "Cliente 4",
                "Un proyecto", "2018-11-08"))
        mProjects.add(Project(5, "Proyecto 5", "Morado", "Cliente 5",
                "Un proyecto", "2018-11-08"))
        mProjects.add(Project(6, "Proyecto 6", "Verde", "Cliente 6",
                "Un proyecto", "2018-11-08"))
        mProjects.add(Project(7, "Proyecto 7", "Azul", "Cliente 7",
                "Un proyecto", "2018-11-08"))
        mProjects.add(Project(8, "Proyecto 8", "Amarillo", "Cliente 8",
                "Un proyecto", "2018-11-08"))
        mProjects.add(Project(9, "Proyecto 9", "Cafe", "Cliente 9",
                "Un proyecto", "2018-11-08"))
        mProjects.add(Project(10, "Proyecto 10", "Rojo", "Cliente 10",
                "Un proyecto", "2018-11-08"))
        mProjects.add(Project(11, "Proyecto 11", "Naranja", "Cliente 11",
                "Un proyecto", "2018-11-08"))
        */
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val adapter = RecyclerViewAdapterProject(activity as Context, mProjects)
        recycler_view_projects.adapter = adapter
        recycler_view_projects.layoutManager = LinearLayoutManager(activity)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
                ProjectsFragment()
    }
}
