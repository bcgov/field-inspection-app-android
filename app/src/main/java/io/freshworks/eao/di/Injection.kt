package io.freshworks.eao.di

import android.content.Context
import io.freshworks.eao.data.api.EpicApi
import io.freshworks.eao.data.repo.inspection.InspectionDataSource
import io.freshworks.eao.data.repo.inspection.InspectionLocalDataSource
import io.freshworks.eao.data.repo.inspection.InspectionRemoteDataSource
import io.freshworks.eao.data.repo.inspection.InspectionRepo
import io.freshworks.eao.data.repo.network.NetworkStateRepo
import io.freshworks.eao.data.repo.project.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Injection {

    fun provideOkHttpClient(timeOut: Long): OkHttpClient {
        return OkHttpClient.Builder()
                .readTimeout(timeOut, TimeUnit.SECONDS)
                .connectTimeout(timeOut, TimeUnit.SECONDS)
                // For Testing
                //.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()
    }

    fun provideRetrofit(apiDomain: String, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(apiDomain)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
    }

    @Throws(IllegalStateException::class)
    fun provideEpicApi(retrofit: Retrofit): EpicApi {
        return retrofit.create(EpicApi::class.java)
    }

    fun provideProjectRepo(cacheDataSource: ProjectDataSource,
                           remoteDataSource: ProjectDataSource) : ProjectRepo {
        return ProjectRepo.getInstance(cacheDataSource, remoteDataSource)
    }

    fun provideProjectRemoteDataSource(epicApi: EpicApi) : ProjectRemoteDataSource {
        return ProjectRemoteDataSource.getInstance(epicApi)
    }

    fun provideProjectLocalDataSource() : ProjectLocalDataSource {
        return ProjectLocalDataSource.getInstance()
    }

    fun provideProjectCacheDataSource() : ProjectCacheDataSource {
        return ProjectCacheDataSource.getInstance()
    }

    fun provideInspectionRepo(localDataSource: InspectionDataSource,
                              remoteDataSource: InspectionDataSource,
                              networkStateRepo: NetworkStateRepo) : InspectionRepo{
        return  InspectionRepo.getInstance(localDataSource, remoteDataSource, networkStateRepo)
    }

    fun provideInspectionRemoteDataSource() : InspectionRemoteDataSource {
        return InspectionRemoteDataSource
    }

    fun provideInspectionLocalDataSource() : InspectionLocalDataSource {
        return InspectionLocalDataSource
    }

    fun provideNetworkStateRepo(context: Context) : NetworkStateRepo {
        return NetworkStateRepo(context)
    }

}
