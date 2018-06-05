package io.freshworks.eao.screens.projectlist

import io.freshworks.eao.common.base.BaseView
import io.freshworks.eao.data.model.Project

interface ProjectListContract{

    interface View : BaseView<ProjectListContract.Presenter> {
        fun setUpBackBtnListener()
        fun setUpSearchViewListener()
        fun setUpAddBtnListener()
        fun setUpProjectList()

        fun clearProjectList()
        fun addProjectToList(project: Project)
        fun addProjectsToList(projects: List<Project>)

        fun showNewProjectView()

        fun goToPreviousScreen()
    }

    interface Presenter{
        fun initialize()

        fun onBackBtnClicked()
        fun onAddBtnClicked()

        fun onSearchViewSubmitted(query : String)
        fun onNewProjectSubmitted(name : String)
    }
}
