package io.freshworks.eao.screens.projectlist

import io.freshworks.eao.data.model.Project
import io.freshworks.eao.data.repo.project.ProjectDataSource
import java.util.*


class ProjectListPresenter(
        private var view: ProjectListContract.View,
        private val projectRepo: ProjectDataSource
) : ProjectListContract.Presenter {

    init {
        view.presenter = this
    }

    override fun initialize() {
        view.setUpBackBtnListener()
        view.setUpProjectList()
        view.setUpSearchViewListener()
        view.setUpAddBtnListener()
        loadProjects()
    }

    override fun onBackBtnClicked() {
        view.goToPreviousScreen()
    }

    override fun onAddBtnClicked() {
        view.showNewProjectView()
    }

    override fun onSearchViewSubmitted(query: String) {

        projectRepo.getProjects { projects ->

            val projectsSearched: ArrayList<Project> = arrayListOf()
            projects.filterTo(projectsSearched) {
                it.name?.toLowerCase()!!.startsWith(query.toLowerCase())
            }
            if (projectsSearched.size < 1) {
                val project = Project()
                project.name = "No projects found"
                projectsSearched.add(project)
            }

            view.clearProjectList()
            view.addProjectsToList(projectsSearched)
        }
    }

    override fun onNewProjectSubmitted(name: String) {
        val project = Project()
        project.name = name
        project.onlyLocal = true
        projectRepo.saveProject(project)

        view.clearProjectList()
        loadProjects()
    }

    private fun loadProjects() {
        projectRepo.getProjects { projects ->
            view.addProjectsToList(projects)
        }
    }
}
