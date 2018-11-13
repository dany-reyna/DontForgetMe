package com.lcrt.dontforgetme

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_view_project.*


class ViewProjectActivity : AppCompatActivity() {

    private lateinit var project: Project

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_project)

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

            text_view_project_name.text = project.name
            text_view_project_name.setCompoundDrawablesRelativeWithIntrinsicBounds(getColorResourceId(project.color), 0, 0, 0)
            text_view_project_client.text = project.client
            text_view_project_description.text = project.description
            text_view_project_deadline.text = simpleDateFormatter.format(sqliteDateFormat.parse(project.deadline))
        }
    }
}
