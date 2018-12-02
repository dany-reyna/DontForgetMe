package com.lcrt.dontforgetme

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_edit_project.*
import java.util.*

class EditProjectActivity : AddProjectActivity() {

    private lateinit var project: Project

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_project)

        getIncomingIntent()
        setSpinnerListener(spinner_edit_project_color, image_view_edit_project_color)
        setPickerListener(text_view_edit_project_deadline, projectDeadlineInput)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_edit_project, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                backAlert()
                true
            }
            R.id.item_edit_project_done -> {
                validateInput()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getIncomingIntent() {
        projectDeadlineInput = Calendar.getInstance()

        if (intent.hasExtra(EXTRA_PROJECT)) {
            project = intent.getParcelableExtra(EXTRA_PROJECT)
            text_input_layout_edit_project_name.editText?.setText(project.name)
            setColorData(project.color)
            text_input_layout_edit_project_client.editText?.setText(project.client)
            text_input_layout_edit_project_description.editText?.setText(project.description)
            text_view_edit_project_deadline.text = simpleDateFormatter.format(sqliteDateFormat.parse(project.deadline))
        }
    }

    private fun setColorData(color: String) {
        val colorsAdapter = ArrayAdapter.createFromResource(this, R.array.colors, android.R.layout.simple_spinner_item).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        spinner_edit_project_color.apply {
            adapter = colorsAdapter
            setSelection(colorsAdapter.getPosition(color))
        }
        image_view_edit_project_color.setImageResource(getColorResourceId(color))
    }

    override fun showErrorSnackBar(text: String) {
        Snackbar.make(coordinator_layout_edit_project, text, Snackbar.LENGTH_INDEFINITE).apply {
            setAction(getString(R.string.ok)) { dismiss() }
            show()
        }
    }

    override fun validateInput() {
        if (!validateName(text_input_layout_edit_project_name)
                or !validateClient(text_input_layout_edit_project_client)
                or !validateDescription(text_input_layout_edit_project_description)
                or !validateDeadline()) {
            return
        }

        // ToDo: UPDATE name,color,client,description,deadline on DB with 'project.id'
        val name = projectNameInput
        val color = spinner_edit_project_color.selectedItem.toString()
        val client = projectClientInput
        val description = projectDescriptionInput
        val deadline = sqliteDateFormat.format(projectDeadlineInput.time)

        // ToDo: set Notifications

        finish()
    }
}