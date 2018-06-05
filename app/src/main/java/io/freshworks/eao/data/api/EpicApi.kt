package io.freshworks.eao.data.api

import io.freshworks.eao.data.model.ProjectResponse
import retrofit2.Call
import retrofit2.http.GET

interface EpicApi {

    @GET("/api/projects/published")
    fun getProjects() : Call<List<ProjectResponse>>

}