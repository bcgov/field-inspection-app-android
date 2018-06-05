package io.freshworks.eao.data.repo.project

import com.parse.*
import io.freshworks.eao.data.model.Project
import com.parse.ParseObject


class ProjectLocalDataSource : ProjectDataSource {
    companion object {

        @Volatile
        private var INSTANCE: ProjectLocalDataSource? = null

        fun getInstance() =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: ProjectLocalDataSource()
                            .also { INSTANCE = it }
                }

    }

    override fun getProjects(callback: (List<Project>) -> Unit) {
        ParseQuery.getQuery(Project::class.java)
                .fromLocalDatastore()
                .findInBackground { projects, e ->
                    if (e != null) return@findInBackground
                    if (projects != null) callback(projects)
                }
    }

    override fun saveProjects(projects: List<Project>) {
        ParseObject.pinAllInBackground(projects)
    }

    override fun saveProject(project: Project) {
        project.pinInBackground()
    }

    override fun deleteProjects() {
        ParseQuery.getQuery(Project::class.java)
                .whereEqualTo("onlyLocal", false)
                .fromLocalDatastore()
                .findInBackground { projects, e ->
                    if (e != null) return@findInBackground
                    ParseObject.unpinAllInBackground(projects)
                }
    }
}
