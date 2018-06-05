package io.freshworks.eao.screens.projectlist

import io.freshworks.eao.data.model.Project

interface ProjectListListener {
    fun onProjectClicked(project: Project)
}
