package io.freshworks.eao.data.repo.project

import io.freshworks.eao.data.model.Project

class ProjectRepo(
        private val localDataSource: ProjectDataSource,
        private val remoteDataSource: ProjectDataSource,
        private var firstTimeGetData: Boolean = true
) : ProjectDataSource {

    companion object {

        @Volatile
        private var INSTANCE: ProjectRepo? = null

        fun getInstance(cacheDataSource: ProjectDataSource, remoteDataSource: ProjectDataSource): ProjectRepo {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: ProjectRepo(cacheDataSource, remoteDataSource)
                        .also { INSTANCE = it }
            }
        }

    }
    override fun saveProjects(projects: List<Project>) {
        localDataSource.saveProjects(projects)
    }

    override fun saveProject(project: Project) {
        localDataSource.saveProject(project)
    }

    override fun getProjects(callback: (List<Project>) -> Unit) {
        if(firstTimeGetData) {
            loadFromRemoteToLocal(callback)
            firstTimeGetData = false
        } else {
            localDataSource.getProjects { projects ->
                if (projects.isEmpty()) {
                    loadFromRemoteToLocal(callback)
                } else {
                    callback(sortProjects(projects))
                }
            }
        }
    }

    override fun deleteProjects() {
        localDataSource.deleteProjects()
    }

    private fun loadFromRemoteToLocal(callback: (List<Project>) -> Unit){
        localDataSource.deleteProjects()
        remoteDataSource.getProjects {
            localDataSource.saveProjects(it)
            localDataSource.getProjects { projects ->
                callback(sortProjects(projects))
            }
        }
    }

    private fun sortProjects(projects: List<Project>): List<Project> {
        return projects.sortedWith(compareBy({ it.name?.toLowerCase() }))
    }

}