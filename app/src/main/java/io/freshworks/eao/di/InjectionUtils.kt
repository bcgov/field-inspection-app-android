package io.freshworks.eao.di

import android.content.Context
import io.freshworks.eao.BuildConfig
import io.freshworks.eao.data.api.EpicApi
import io.freshworks.eao.data.model.Inspection
import io.freshworks.eao.data.repo.inspection.InspectionRepo
import io.freshworks.eao.data.repo.project.ProjectRepo

object InjectionUtils {

    fun getEpicApi() : EpicApi {
        val apiDomain = BuildConfig.API_DOMAIN
        return Injection.provideEpicApi(
                Injection.provideRetrofit(
                        apiDomain,
                        Injection.provideOkHttpClient(
                                20L
                        )
                )
        )
    }

    fun getProjectRepo(): ProjectRepo {
        return Injection.provideProjectRepo(
                Injection.provideProjectLocalDataSource(),
                Injection.provideProjectRemoteDataSource(
                        getEpicApi()
                )
        )
    }

    fun getInspectionRepo(context: Context): InspectionRepo {
        return Injection.provideInspectionRepo(
                Injection.provideInspectionLocalDataSource(),
                Injection.provideInspectionRemoteDataSource(),
                Injection.provideNetworkStateRepo(context)
        )
    }

}
