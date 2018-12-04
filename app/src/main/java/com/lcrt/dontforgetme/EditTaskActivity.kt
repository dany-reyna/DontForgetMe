package com.lcrt.dontforgetme

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_edit_task.*
import java.util.*

class EditTaskActivity : AddTaskActivity() {

    private lateinit var task: Task
    private lateinit var UsersDB: DataBaseHelperProject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_task)
        UsersDB = DataBaseHelperProject(this)

        getIncomingIntent()
        setSpinnersListeners(spinner_edit_task_linked_project, image_view_edit_task_linked_project_color,
                spinner_edit_task_priority, image_view_edit_task_priority)
        setPickersListeners(text_view_edit_task_start_date, text_view_edit_task_start_time,
                text_view_edit_task_end_date, text_view_edit_task_end_time)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_edit_task, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                backAlert()
                true
            }
            R.id.item_edit_task_done -> {
                validateInput()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getIncomingIntent() {
        taskStartInput = Calendar.getInstance()
        taskEndInput = Calendar.getInstance()

        if (intent.hasExtra(EXTRA_TASK)) {
            task = intent.getParcelableExtra(EXTRA_TASK)
            text_input_layout_edit_task_name.editText?.setText(task.name)
            setProjectData(task.linkedProjectId, task.linkedProjectColor)
            setPriorityData(task.priority)
            text_input_layout_edit_task_location.editText?.setText(task.location)
            setDatesData(task.startDateTime, task.endDateTime)
            setNotificationData(task.notificationTime)
        }
    }

    private fun setProjectData(id: Int, color: String) {
        val p = getProject(id)
        val projectEntries = getProjectEntries()

        val projectsAdapter = ArrayAdapter<Project>(this, android.R.layout.simple_spinner_item, projectEntries).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        spinner_edit_task_linked_project.apply {
            adapter = projectsAdapter
            setSelection(projectsAdapter.getPosition(p))
        }

        image_view_edit_task_linked_project_color.setImageResource(getColorResourceId(color))
    }

    private fun getProject(id: Int): Project {
        // ToDo: replace below with the project from DB using parameter 'id'
        return Project(7, "Proyecto 7", "Azul", "Cliente 7", "Un proyecto", "2018-11-08")
    }

    private fun setPriorityData(priority: String) {
        val prioritiesAdapter = ArrayAdapter.createFromResource(this, R.array.priorities, android.R.layout.simple_spinner_item).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        spinner_edit_task_priority.apply {
            adapter = prioritiesAdapter
            setSelection(prioritiesAdapter.getPosition(priority))
        }
        image_view_edit_task_priority.setImageResource(getPriorityResourceId(priority))
    }

    private fun setDatesData(start: String, end: String) {
        text_view_edit_task_start_date.text = simpleDateFormatter.format(sqliteDateTimeFormat.parse(start))
        text_view_edit_task_start_time.text = simpleTimeFormatter.format(sqliteDateTimeFormat.parse(start))
        text_view_edit_task_end_date.text = simpleDateFormatter.format(sqliteDateTimeFormat.parse(end))
        text_view_edit_task_end_time.text = simpleTimeFormatter.format(sqliteDateTimeFormat.parse(end))
    }

    private fun setNotificationData(notificationTime: String) {
        val notificationTimesAdapter = ArrayAdapter.createFromResource(this, R.array.notification_times, android.R.layout.simple_spinner_item).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        spinner_edit_task_notification.apply {
            adapter = notificationTimesAdapter
            setSelection(notificationTimesAdapter.getPosition(notificationTime))
        }
    }

    override fun validateStartDate(): Boolean {
        return validateDateBeforeNow(getString(R.string.task_start), taskStartInput)
                && validateDateAfterDeadline(spinner_edit_task_linked_project, getString(R.string.task_start), taskStartInput)
    }

    override fun validateEndDate(): Boolean {
        return validateDateBeforeNow(getString(R.string.task_end), taskEndInput)
                && validateDateAfterDeadline(spinner_edit_task_linked_project, getString(R.string.task_end), taskEndInput)
    }

    override fun showErrorSnackBar(text: String) {
        Snackbar.make(coordinator_layout_edit_task, text, Snackbar.LENGTH_INDEFINITE).apply {
            setAction(getString(R.string.ok)) { dismiss() }
            show()
        }
    }

    override fun validateInput() {
        if (!validateName(text_input_layout_edit_task_name)
                or !validateLocation(text_input_layout_edit_task_location)
                or !validateDates()) {
            return
        }

        // ToDo: UPDATE name,priority,location,startDateTime,endDateTime,notificationTime,linkedProjecId on DB with 'task.id'
        val name = taskNameInput
        val priority = spinner_edit_task_priority.selectedItem.toString()
        val location = taskLocationInput
        val startDate = sqliteDateTimeFormat.format(taskStartInput.time)
        val endDate = sqliteDateTimeFormat.format(taskEndInput.time)
        val notificationTime = spinner_edit_task_notification.selectedItem.toString()
        val projectId = (spinner_edit_task_linked_project.selectedItem as Project).id
        if (UsersDB.updateTask(projectId.toString(), name, priority, location, startDate, endDate, notificationTime)){
            Toast.makeText(applicationContext, "Tarea editado", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(applicationContext, "Tarea no editado", Toast.LENGTH_SHORT).show()
        }
        // ToDo: set Notifications

        finish()
    }
}
