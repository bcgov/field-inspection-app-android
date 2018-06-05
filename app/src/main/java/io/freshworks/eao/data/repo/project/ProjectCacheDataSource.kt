package io.freshworks.eao.data.repo.project

import io.freshworks.eao.data.model.Project

class ProjectCacheDataSource : ProjectDataSource {
    private val projects = ArrayList<Project>()

    companion object {

        @Volatile private var INSTANCE : ProjectCacheDataSource? = null

        fun getInstance() =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: ProjectCacheDataSource()
                            .also { INSTANCE = it }
                }
    }

    override fun saveProjects(projects: List<Project>) {
        this.projects.addAll(projects)
    }
    override fun getProjects(callback: (List<Project>) -> Unit) {
        callback(projects)
    }

    override fun saveProject(project: Project) {
        throw Throwable("Function for Local Data Source Only.")
    }

    override fun deleteProjects() {
        throw Throwable("Function for Local Data Source Only.")
    }

}
