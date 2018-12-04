package com.lcrt.dontforgetme

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_view_task.*

class ViewTaskActivity : AppCompatActivity() {

    private lateinit var task: Task
    private lateinit var UsersDB: DataBaseHelperProject


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_task)
        UsersDB = DataBaseHelperProject(this)

        getIncomingIntent()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_view_task, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.item_edit_task -> {
                val intent = Intent(this, EditTaskActivity::class.java).apply {
                    putExtra(EXTRA_TASK, task)
                }
                startActivity(intent)
                true
            }
            R.id.item_delete_task -> {
                val builder = AlertDialog.Builder(this).apply {
                    setMessage(getString(R.string.delete_task_prompt))
                    setPositiveButton(getString(R.string.ok)) { _, _ ->
                        // ToDo: delete task from DB
                        if(UsersDB.deleteTask(task.id.toString())){
                            Toast.makeText(applicationContext, "Tarea eliminada", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(applicationContext, "Tarea no eliminada", Toast.LENGTH_SHORT).show()
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
        if (intent.hasExtra(EXTRA_TASK)) {

            task = intent.getParcelableExtra(EXTRA_TASK)

            text_view_task_name.text = task.name

            text_view_task_linked_project.setCompoundDrawablesRelativeWithIntrinsicBounds(getColorResourceId(task.linkedProjectColor), 0, 0, 0)
            text_view_task_linked_project.text = task.linkedProjectName

            text_view_task_priority.setCompoundDrawablesRelativeWithIntrinsicBounds(getPriorityResourceId(task.priority), 0, 0, 0)
            text_view_task_priority.text = task.priority

            text_view_task_location.text = task.location

            text_view_task_start.text = simpleDateTimeFormatter.format(sqliteDateTimeFormat.parse(task.startDateTime))
            text_view_task_end.text = simpleDateTimeFormatter.format(sqliteDateTimeFormat.parse(task.endDateTime))

            text_view_task_notification.text = task.notificationTime
        }
    }
}
