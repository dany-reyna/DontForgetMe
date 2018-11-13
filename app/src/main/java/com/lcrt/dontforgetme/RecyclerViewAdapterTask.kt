package com.lcrt.dontforgetme

import android.content.Context
import android.content.Intent
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import java.util.*

internal const val EXTRA_TASK = "com.lcrt.dontforgetme.TASK"

class RecyclerViewAdapterTask(private val mContext: Context,
                              private val mTasks: ArrayList<Task>)
    : RecyclerView.Adapter<RecyclerViewAdapterTask.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.priority.setImageResource(getPriorityResourceId(mTasks[position].priority))
        holder.name.text = mTasks[position].name

        val parsedDate = sqliteDateTimeFormat.parse(mTasks[position].startDateTime)
        holder.startDate.text = simpleDateTimeFormatter.format(parsedDate)
        val taskCal = Calendar.getInstance().apply {
            time = parsedDate
        }
        if (taskCal.before(Calendar.getInstance())) {
            holder.name.setTextColor(ContextCompat.getColor(mContext, R.color.colorWarning))
        }

        holder.location.text = mTasks[position].location
        holder.projectColor.setImageResource(getColorResourceId(mTasks[position].linkedProjectColor))

        holder.parentLayout.setOnClickListener {
            val intent = Intent(mContext, ViewTaskActivity::class.java).apply {
                putExtra(EXTRA_TASK, mTasks[position])
            }
            mContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return mTasks.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val parentLayout: ConstraintLayout = itemView.findViewById(R.id.parent_layout_task)

        val priority: ImageView = itemView.findViewById(R.id.image_view_priority)
        val name: TextView = itemView.findViewById(R.id.text_view_task_name)
        val startDate: TextView = itemView.findViewById(R.id.text_view_date)
        val location: TextView = itemView.findViewById(R.id.text_view_location)
        val projectColor: ImageView = itemView.findViewById(R.id.image_view_linked_project)
    }
}