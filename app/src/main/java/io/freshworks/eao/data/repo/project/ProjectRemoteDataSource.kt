package io.freshworks.eao.data.repo.project

import android.util.Log
import io.freshworks.eao.data.api.EpicApi
import io.freshworks.eao.data.model.Project
import io.freshworks.eao.data.model.ProjectResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProjectRemoteDataSource(private val epicApi: EpicApi) : ProjectDataSource {
    companion object {

        @Volatile private var INSTANCE : ProjectRemoteDataSource? = null

        fun getInstance(epicApi: EpicApi) =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: ProjectRemoteDataSource(epicApi)
                            .also { INSTANCE = it }
                }
    }

    override fun getProjects(callback: (List<Project>) -> Unit) {
        epicApi.getProjects().enqueue(object : Callback<List<ProjectResponse>> {

            override fun onFailure(call: Call<List<ProjectResponse>>?, t: Throwable?) {
                t?.printStackTrace()
            }

            override fun onResponse(call: Call<List<ProjectResponse>>?,
                                    response: Response<List<ProjectResponse>>?) {
                val resp = response ?: return
                if (resp.isSuccessful) {
                    val projects = resp.body()!!.map {
                        val project = Project()
                        project.name = it.name
                        project.onlyLocal = false
                        project
                    }
                    callback(projects)
                } else {
                    Log.d("ProjectRemote", response.errorBody().toString())
                }
            }
        })
    }

    override fun saveProject(project: Project) {
        throw Throwable("Function for Local Data Source Only.")
    }

    override fun saveProjects(projects: List<Project>) {
        throw Throwable("Function for Local Data Source Only.")
    }

    override fun deleteProjects() {
        throw Throwable("Function for Local Data Source Only.")
    }

}
