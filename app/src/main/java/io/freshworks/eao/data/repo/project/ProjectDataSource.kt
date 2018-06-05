package io.freshworks.eao.data.repo.project

import io.freshworks.eao.data.model.Project


interface ProjectDataSource {
    fun getProjects(callback: (List<Project>) -> Unit)
    fun saveProjects(projects: List<Project>)
    fun saveProject(project: Project)
    fun deleteProjects()
}