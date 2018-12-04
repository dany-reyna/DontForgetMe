package com.lcrt.dontforgetme

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_view_project.*


class ViewProjectActivity : AppCompatActivity() {

    private lateinit var project: Project
    private lateinit var UsersDB: DataBaseHelperProject
    private lateinit var UsersDBTask: DataBaseHelperTask

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_project)
        UsersDB = DataBaseHelperProject(this)
        UsersDBTask = DataBaseHelperTask(this)

        getIncomingIntent()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_view_project, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.item_edit_project -> {
                val intent = Intent(this, EditProjectActivity::class.java).apply {
                    putExtra(EXTRA_PROJECT, project)
                }
                startActivity(intent)
                true
            }
            R.id.item_delete_project -> {
                val builder = AlertDialog.Builder(this).apply {
                    setMessage(getString(R.string.delete_project_prompt))

                    setPositiveButton(getString(R.string.ok)) { _, _ ->
                        // ToDo: delete project and related tasks from DB
                        if(UsersDB.deleteProject(project.id.toString(),UsersDBTask)){
                            Toast.makeText(applicationContext, "Proyecto eliminado", Toast.LENGTH_SHORT).show()
                            cancelAlarm(project.id)
                        }else{
                            Toast.makeText(applicationContext, "Proyecto no eliminado", Toast.LENGTH_SHORT).show()
                        }
                        finish()
                    }
                    setNegativeButton(getString(R.string.cancel), null)
                }

                val alert = builder.create()
                alert.show()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getIncomingIntent() {
        if (intent.hasExtra(EXTRA_PROJECT)) {
            project = intent.getParcelableExtra(EXTRA_PROJECT)
            Log.d("Hey", project.name)
            text_view_project_name.text = project.name
            text_view_project_name.setCompoundDrawablesRelativeWithIntrinsicBounds(getColorResourceId(project.color), 0, 0, 0)
            Log.d("Hey", project.color)
            text_view_project_client.text = project.client
            Log.d("Hey", project.client)
            text_view_project_description.text = project.description
            Log.d("Hey", project.description)
            text_view_project_deadline.text = simpleDateFormatter.format(sqliteDateFormat.parse(project.deadline))
            Log.d("Hey", project.deadline)
        }
    }
    private fun cancelAlarm(id: Int) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlertReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, id, intent, 0)

        alarmManager.cancel(pendingIntent)
    }
}
