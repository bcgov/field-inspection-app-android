package io.freshworks.eao.screens.projectlist

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import io.freshworks.eao.data.model.Project
import kotlinx.android.synthetic.main.item_project.view.*

class ProjectViewHolder (itemView: View
) : RecyclerView.ViewHolder(itemView) {

    private val titleTv: TextView = itemView.titleTv
    private val projectCl: ConstraintLayout = itemView.projectCl
    
    fun bind(project: Project, listener: ProjectListListener){

        projectCl.setOnClickListener{listener.onProjectClicked(project)}
        titleTv.text = project.name
    }
}
