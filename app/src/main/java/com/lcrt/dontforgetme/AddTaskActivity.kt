package com.lcrt.dontforgetme

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TextInputLayout
import android.support.v4.app.NavUtils
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_add_task.*
import java.util.*
import kotlin.collections.ArrayList

open class AddTaskActivity : AppCompatActivity() {

    protected lateinit var taskNameInput: String
    protected lateinit var taskLocationInput: String
    protected lateinit var taskStartInput: Calendar
    protected lateinit var taskEndInput: Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        setData()
        setSpinnersListeners(spinner_add_task_linked_project, image_view_add_task_linked_project_color,
                spinner_add_task_priority, image_view_add_task_priority)
        setPickersListeners(text_view_add_task_start_date, text_view_add_task_start_time,
                text_view_add_task_end_date, text_view_add_task_end_time)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_task, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                backAlert()
                true
            }
            R.id.item_add_task_done -> {
                validateInput()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        backAlert()
    }

    protected fun backAlert() {
        val builder = AlertDialog.Builder(this).apply {
            setMessage(getString(R.string.discard_changes_prompt))
            setPositiveButton(getString(R.string.ok)) { _, _ ->
                navigateUp()
            }
            setNegativeButton(getString(R.string.cancel), null)
        }
        val alert = builder.create()
        alert.show()
    }

    private fun navigateUp() {
        NavUtils.navigateUpFromSameTask(this)
    }

    private fun setData() {
        taskStartInput = Calendar.getInstance()
        taskEndInput = Calendar.getInstance()
        setProjectData()
        setPriorityData()
        setNotificationData()
    }

    private fun setProjectData() {
        val projectEntries = getProjectEntries()
        val projectsAdapter = ArrayAdapter<Project>(this, android.R.layout.simple_spinner_item, projectEntries).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        spinner_add_task_linked_project.adapter = projectsAdapter

        val color = (spinner_add_task_linked_project.selectedItem as Project).color
        image_view_add_task_linked_project_color.setImageResource(getColorResourceId(color))
    }

    protected fun getProjectEntries(): ArrayList<Project> {
        val projectEntries = ArrayList<Project>()

        // ToDo: replace projectEntries below with all projects from DB
        projectEntries.add(Project(1, "Proyecto 1", "Morado", "Cliente 1", "Un proyecto", "2018-11-08"))
        projectEntries.add(Project(2, "Proyecto 2", "Naranja", "Cliente 2", "Un proyecto", "2018-11-08"))
        projectEntries.add(Project(3, "Proyecto 3", "Azul", "Cliente 3", "Un proyecto", "2018-11-08"))
        projectEntries.add(Project(4, "Proyecto 4", "Cafe", "Cliente 4", "Un proyecto", "2018-11-08"))
        projectEntries.add(Project(5, "Proyecto 5", "Morado", "Cliente 5", "Un proyecto", "2018-11-08"))
        projectEntries.add(Project(6, "Proyecto 6", "Verde", "Cliente 6", "Un proyecto", "2018-11-08"))
        projectEntries.add(Project(7, "Proyecto 7", "Azul", "Cliente 7", "Un proyecto", "2018-11-08"))
        projectEntries.add(Project(8, "Proyecto 8", "Amarillo", "Cliente 8", "Un proyecto", "2018-11-08"))
        projectEntries.add(Project(9, "Proyecto 9", "Cafe", "Cliente 9", "Un proyecto", "2018-11-08"))
        projectEntries.add(Project(10, "Proyecto 10", "Rojo", "Cliente 10", "Un proyecto", "2018-11-08"))
        projectEntries.add(Project(11, "Proyecto 11", "Naranja", "Cliente 11", "Un proyecto", "2018-11-08"))

        return projectEntries
    }

    private fun setPriorityData() {
        val prioritiesAdapter = ArrayAdapter.createFromResource(this, R.array.priorities, android.R.layout.simple_spinner_item).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        spinner_add_task_priority.adapter = prioritiesAdapter

        val priority = spinner_add_task_priority.selectedItem.toString()
        image_view_add_task_priority.setImageResource(getPriorityResourceId(priority))
    }

    private fun setNotificationData() {
        val notificationTimesAdapter = ArrayAdapter.createFromResource(this, R.array.notification_times, android.R.layout.simple_spinner_item).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        spinner_add_task_notification.adapter = notificationTimesAdapter
    }

    protected fun setSpinnersListeners(projectSpinner: Spinner, projectImage: ImageView,
                                       prioritySpinner: Spinner, priorityImage: ImageView) {
        projectSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val color = (parent?.getItemAtPosition(position) as Project).color
                projectImage.setImageResource(getColorResourceId(color))
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        prioritySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val priority = parent?.getItemAtPosition(position).toString()
                priorityImage.setImageResource(getPriorityResourceId(priority))
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    protected fun setPickersListeners(startDate: TextView, startTime: TextView, endDate: TextView, endTime: TextView) {
        setDateListener(startDate, taskStartInput)
        setTimeListener(startTime, taskStartInput)
        setDateListener(endDate, taskEndInput)
        setTimeListener(endTime, taskEndInput)
    }

    private fun setDateListener(textView: TextView, taskDateInput: Calendar) {
        textView.setOnClickListener {
            val dateDialog = DatePickerDialog(
                    this,
                    DatePickerDialog.OnDateSetListener { _, mYear, mMonth, mDay ->
                        taskDateInput[Calendar.YEAR] = mYear
                        taskDateInput[Calendar.MONTH] = mMonth
                        taskDateInput[Calendar.DAY_OF_MONTH] = mDay
                        textView.text = simpleDateFormatter.format(taskDateInput.time)
                    },
                    taskDateInput[Calendar.YEAR],
                    taskDateInput[Calendar.MONTH],
                    taskDateInput[Calendar.DAY_OF_MONTH]
            )
            dateDialog.show()
        }
    }

    private fun setTimeListener(textView: TextView, taskDateInput: Calendar) {
        textView.setOnClickListener {
            val timeDialog = TimePickerDialog(
                    this,
                    TimePickerDialog.OnTimeSetListener { _, mHour, mMinute ->
                        taskDateInput[Calendar.HOUR_OF_DAY] = mHour
                        taskDateInput[Calendar.MINUTE] = mMinute
                        textView.text = simpleTimeFormatter.format(taskDateInput.time)
                    },
                    taskDateInput[Calendar.HOUR_OF_DAY],
                    taskDateInput[Calendar.MINUTE],
                    true
            )
            timeDialog.show()
        }
    }

    protected fun validateName(textInputLayout: TextInputLayout): Boolean {
        textInputLayout.editText?.let {
            taskNameInput = it.text.toString().trim()
        }

        return when {
            taskNameInput.isEmpty() -> {
                textInputLayout.error = getString(R.string.empty_field_error_message)
                false
            }
            taskNameInput.length > TASK_NAME_LENGTH -> {
                textInputLayout.error = getString(R.string.long_task_name_error_message)
                false
            }
            else -> {
                textInputLayout.error = null
                true
            }
        }
    }

    protected fun validateLocation(textInputLayout: TextInputLayout): Boolean {
        textInputLayout.editText?.let {
            taskLocationInput = it.text.toString().trim()
        }

        return when {
            taskLocationInput.isEmpty() -> {
                textInputLayout.error = getString(R.string.empty_field_error_message)
                false
            }
            taskLocationInput.length > TASK_LOCATION_LENGTH -> {
                textInputLayout.error = getString(R.string.long_task_location_error_message)
                false
            }
            else -> {
                textInputLayout.error = null
                true
            }
        }
    }

    protected fun validateDates(): Boolean {
        return validateStartDate() && validateEndDate() && validateDateOrder()
    }

    protected open fun validateStartDate(): Boolean {
        return validateDateSet(getString(R.string.task_start), text_view_add_task_start_date, text_view_add_task_start_time, getString(R.string.task_start_date_hint), getString(R.string.task_start_time_hint))
                && validateDateBeforeNow(getString(R.string.task_start), taskStartInput)
                && validateDateAfterDeadline(spinner_add_task_linked_project, getString(R.string.task_start), taskStartInput)
    }

    protected open fun validateEndDate(): Boolean {
        return validateDateSet(getString(R.string.task_end), text_view_add_task_end_date, text_view_add_task_end_time, getString(R.string.task_end_date_hint), getString(R.string.task_end_time_hint))
                && validateDateBeforeNow(getString(R.string.task_end), taskEndInput)
                && validateDateAfterDeadline(spinner_add_task_linked_project, getString(R.string.task_end), taskEndInput)
    }

    private fun validateDateSet(formatArg: String, dateTextView: TextView, timeTextView: TextView,
                                dateHint: String, timeHint: String): Boolean {
        return when {
            dateTextView.text == dateHint || timeTextView.text == timeHint -> {
                showErrorSnackBar(getString(R.string.no_date_set_error, formatArg))
                false
            }
            else -> true
        }
    }

    protected fun validateDateBeforeNow(formatArg: String, taskDateInput: Calendar): Boolean {
        return when {
            taskDateInput.before(Calendar.getInstance()) -> {
                showErrorSnackBar(getString(R.string.date_before_now_error, formatArg))
                false
            }
            else -> true
        }
    }

    protected fun validateDateAfterDeadline(projectSpinner: Spinner, formatArg: String, taskDateInput: Calendar): Boolean {
        val projectDeadline = Calendar.getInstance().apply {
            time = sqliteDateFormat.parse((projectSpinner.selectedItem as Project).deadline)
        }
        return when {
            taskDateInput.after(projectDeadline) -> {
                showErrorSnackBar(getString(R.string.date_after_deadline_error, formatArg))
                false
            }
            else -> true
        }
    }

    private fun validateDateOrder(): Boolean {
        return when {
            taskStartInput.after(taskEndInput) -> {
                showErrorSnackBar(getString(R.string.date_order_error))
                false
            }
            else -> true
        }
    }

    protected open fun showErrorSnackBar(text: String) {
        Snackbar.make(coordinator_layout_add_task, text, Snackbar.LENGTH_INDEFINITE).apply {
            setAction(getString(R.string.ok)) { dismiss() }
            show()
        }
    }

    protected open fun validateInput() {
        if (!validateName(text_input_layout_add_task_name)
                or !validateLocation(text_input_layout_add_task_location)
                or !validateDates()) {
            return
        }

        // ToDo: INSERT name,priority,location,startDateTime,endDateTime,notificationTime,linkedProjecId on DB
        val name = taskNameInput
        val priority = spinner_add_task_priority.selectedItem.toString()
        val location = taskLocationInput
        val startDate = sqliteDateTimeFormat.format(taskStartInput.time)
        val endDate = sqliteDateTimeFormat.format(taskEndInput.time)
        val notificationTime = spinner_add_task_notification.selectedItem.toString()
        val projectId = (spinner_add_task_linked_project.selectedItem as Project).id

        // ToDo: set Notifications

        finish()
    }
}
