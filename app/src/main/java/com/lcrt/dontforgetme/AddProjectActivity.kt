package com.lcrt.dontforgetme

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
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
import kotlinx.android.synthetic.main.activity_add_project.*
import java.util.*

open class AddProjectActivity : AppCompatActivity() {

    protected lateinit var projectNameInput: String
    protected lateinit var projectClientInput: String
    protected lateinit var projectDescriptionInput: String
    protected lateinit var projectDeadlineInput: Calendar
    private lateinit var UsersDB: DataBaseHelperProject


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_project)
        UsersDB = DataBaseHelperProject(this)
        setData()
        setSpinnerListener(spinner_add_project_color, image_view_add_project_color)
        setPickerListener(text_view_add_project_deadline, projectDeadlineInput)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_project, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                backAlert()
                true
            }
            R.id.item_add_project_done -> {
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
        projectDeadlineInput = Calendar.getInstance()
        setColorData()
    }

    private fun setColorData() {
        val colorAdapter = ArrayAdapter.createFromResource(this, R.array.colors, android.R.layout.simple_spinner_item).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        spinner_add_project_color.adapter = colorAdapter

        val color = spinner_add_project_color.selectedItem.toString()
        image_view_add_project_color.setImageResource(getColorResourceId(color))
    }

    protected fun setSpinnerListener(colorSpinner: Spinner, colorImage: ImageView) {
        colorSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val color = parent?.getItemAtPosition(position).toString()
                colorImage.setImageResource(getColorResourceId(color))
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    protected fun setPickerListener(textView: TextView, deadlineInput: Calendar) {
        textView.setOnClickListener {
            val dateDialog = DatePickerDialog(
                    this,
                    DatePickerDialog.OnDateSetListener { _, mYear, mMonth, mDay ->
                        deadlineInput[Calendar.YEAR] = mYear
                        deadlineInput[Calendar.MONTH] = mMonth
                        deadlineInput[Calendar.DAY_OF_MONTH] = mDay
                        textView.text = simpleDateFormatter.format(deadlineInput.time)
                    },
                    deadlineInput[Calendar.YEAR],
                    deadlineInput[Calendar.MONTH],
                    deadlineInput[Calendar.DAY_OF_MONTH]
            )
            dateDialog.show()
        }
    }

    protected fun validateName(textInputLayout: TextInputLayout): Boolean {
        textInputLayout.editText?.let {
            projectNameInput = it.text.toString().trim()
        }

        return when {
            projectNameInput.isEmpty() -> {
                textInputLayout.error = getString(R.string.empty_field_error_message)
                false
            }
            projectNameInput.length > PROJECT_NAME_LENGTH -> {
                textInputLayout.error = getString(R.string.long_project_name_error_message)
                false
            }
            else -> {
                textInputLayout.error = null
                true
            }
        }
    }

    protected fun validateClient(textInputLayout: TextInputLayout): Boolean {
        textInputLayout.editText?.let {
            projectClientInput = it.text.toString().trim()
        }

        return when {
            projectClientInput.isEmpty() -> {
                textInputLayout.error = getString(R.string.empty_field_error_message)
                false
            }
            projectClientInput.length > PROJECT_CLIENT_LENGTH -> {
                textInputLayout.error = getString(R.string.long_project_client_error_message)
                false
            }
            else -> {
                textInputLayout.error = null
                true
            }
        }
    }

    protected fun validateDescription(textInputLayout: TextInputLayout): Boolean {
        textInputLayout.editText?.let {
            projectDescriptionInput = it.text.toString().trim()
        }
        return when {
            projectDescriptionInput.isEmpty() -> {
                textInputLayout.error = getString(R.string.empty_field_error_message)
                false
            }
            projectDescriptionInput.length > PROJECT_DESC_LENGTH -> {
                textInputLayout.error = getString(R.string.long_project_desc_error_message)
                false
            }
            else -> {
                textInputLayout.error = null
                true
            }
        }
    }

    protected fun validateDeadline(): Boolean {
        return validateDateSet() && validateDateBeforeNow()
    }

    private fun validateDateSet(): Boolean {
        return when {
            text_view_add_project_deadline.text == getString(R.string.project_deadline_hint) -> {
                showErrorSnackBar(getString(R.string.no_deadline_set_error))
                false
            }
            else -> true
        }
    }

    private fun validateDateBeforeNow(): Boolean {
        return when {
            projectDeadlineInput.before(todayDate()) -> {
                showErrorSnackBar(getString(R.string.deadline_before_now_error))
                false
            }
            else -> true
        }
    }

    private fun todayDate(): Calendar {
        val today = Calendar.getInstance()
        today[Calendar.HOUR_OF_DAY] = 0
        today[Calendar.MINUTE] = 0
        today[Calendar.SECOND] = 0
        today[Calendar.MILLISECOND] = 0
        return today
    }

    protected open fun showErrorSnackBar(text: String) {
        Snackbar.make(coordinator_layout_add_project, text, Snackbar.LENGTH_INDEFINITE).apply {
            setAction(getString(R.string.ok)) { dismiss() }
            show()
        }
    }

    protected open fun validateInput() {
        if (!validateName(text_input_layout_add_project_name)
                or !validateClient(text_input_layout_add_project_client)
                or !validateDescription(text_input_layout_add_project_description)
                or !validateDeadline()) {
            return
        }

        // ToDo: INSERT name,color,client,description,deadline on DB
        val name = projectNameInput
        val color = spinner_add_project_color.selectedItem.toString()
        val client = projectClientInput
        val description = projectDescriptionInput
        val deadline = sqliteDateFormat.format(projectDeadlineInput.time)
        var index = ""
        if(UsersDB.addProject(name,color,client,description,deadline)){
            Toast.makeText(applicationContext, "Proyecto agregado", Toast.LENGTH_SHORT).show()
            val cur = this.UsersDB.lastString
            cur.moveToFirst()
            index =cur.getString(0)
        }else{
            Toast.makeText(applicationContext, "Proyecto no agregado", Toast.LENGTH_SHORT).show()
        }
        // ToDo: set Notifications

        finish()
    }
    private fun startAlarm(c: Calendar, id: Int, title: String, message: String, icon: Int) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlertReceiver::class.java).apply {
            putExtra(EXTRA_TASK_NOTIFICATION_ID, id)
            putExtra(EXTRA_NOTIFICATION_TITLE, title)
            putExtra(EXTRA_NOTIFICATION_MESSAGE, message)
            putExtra(EXTRA_NOTIFICATION_ICON, icon)
        }
        val pendingIntent = PendingIntent.getBroadcast(this, id, intent, 0)

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.timeInMillis, pendingIntent)
    }

    private fun cancelAlarm(id: Int) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlertReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, id, intent, 0)

        alarmManager.cancel(pendingIntent)
    }
}