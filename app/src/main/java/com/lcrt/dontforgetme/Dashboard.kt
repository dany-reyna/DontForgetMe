package com.lcrt.dontforgetme

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_dashboard.*

class Dashboard : AppCompatActivity() {

    private lateinit var UsersDB: DataBaseHelperProject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        UsersDB = DataBaseHelperProject(this)

        if (intent.hasExtra(EXTRA_USER)) {
            toolbar.title = getString(R.string.greeting, intent.getStringExtra(EXTRA_USER))
        }

        setupViewPager(container)
        tabs.setupWithViewPager(container)

        fab_action_add_project.setOnClickListener {
            val intent = Intent(this, AddProjectActivity::class.java)
            startActivity(intent)
        }

        fab_action_add_task.setOnClickListener {
            // ToDo: check at least one project exists on DB before starting AddTaskActivity
            if(UsersDB.ProjectExists()){
                val intent = Intent(this, AddTaskActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(applicationContext, "No existen los proyectos", Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_dashboard, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.item_logout -> {
                Toast.makeText(this, getString(R.string.logging_out), Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = SectionsPageAdapter(supportFragmentManager)
        adapter.addFragment(TodayFragment.newInstance(), getString(R.string.tab_text_1))
        adapter.addFragment(WeekFragment.newInstance(), getString(R.string.tab_text_2))
        adapter.addFragment(MonthFragment.newInstance(), getString(R.string.tab_text_3))
        adapter.addFragment(ProjectsFragment.newInstance(), getString(R.string.tab_text_4))

        viewPager.adapter = adapter
    }
}
