package com.lcrt.dontforgetme

import android.content.Context
import android.content.Intent
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

internal const val EXTRA_PROJECT = "com.lcrt.dontforgetme.PROJECT"

class RecyclerViewAdapterProject(private val mContext: Context,
                                 private val mProjects: ArrayList<Project>)
    : RecyclerView.Adapter<RecyclerViewAdapterProject.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.project_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.color.setImageResource(getColorResourceId(mProjects[position].color))
        holder.name.text = mProjects[position].name

        holder.parentLayout.setOnClickListener {
            val intent = Intent(mContext, ViewProjectActivity::class.java).apply {
                putExtra(EXTRA_PROJECT, mProjects[position])
            }
            mContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return mProjects.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val parentLayout: ConstraintLayout = itemView.findViewById(R.id.parent_layout_project)

        val color: ImageView = itemView.findViewById(R.id.image_view_project_color)
        val name: TextView = itemView.findViewById(R.id.text_view_project_name)
    }
}