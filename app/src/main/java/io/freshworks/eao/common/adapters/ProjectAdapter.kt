package io.freshworks.eao.common.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.freshworks.eao.R
import io.freshworks.eao.data.model.Project
import io.freshworks.eao.screens.projectlist.ProjectListListener
import io.freshworks.eao.screens.projectlist.ProjectViewHolder

class ProjectAdapter (
        private val listener: ProjectListListener
) : RecyclerView.Adapter<ProjectViewHolder>(){

    lateinit var view : View
    private val projects = ArrayList<Project>()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ProjectViewHolder {
        view = LayoutInflater.from(parent?.context)
                .inflate(R.layout.item_project,parent,false)
        return ProjectViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder?, position: Int) {
        holder?.bind(projects[position], listener)
    }


    override fun getItemCount(): Int {
        return projects.count()
    }

    fun addProject(project: Project) {
        projects.add(project)
        notifyItemInserted(projects.size - 1)
    }

    fun addProjects(projects: List<Project>) {
        val start = if (this.projects.size == 0) 0 else this.projects.size - 1
        this.projects.addAll(projects)
        notifyItemRangeInserted(start, projects.size)
    }

    fun clearProjects(){
        this.projects.clear()
        notifyDataSetChanged()
    }
}
