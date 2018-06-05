package io.freshworks.eao.data.repo.inspection

import io.freshworks.eao.data.model.Inspection
import io.freshworks.eao.data.repo.network.NetworkStateRepo
import java.util.*

class InspectionRepo(
        private val localDataSource: InspectionDataSource,
        private val remoteDataSource: InspectionDataSource,
        private val networkStateRepo: NetworkStateRepo,
        private var firstTimeGetData: Boolean = true
) : InspectionDataSource {

    companion object {

        @Volatile
        private var INSTANCE: InspectionRepo? = null

        fun getInstance(
                inspectionDataSource: InspectionDataSource,
                inspectionRemoteSource: InspectionDataSource,
                networkStateRepo: NetworkStateRepo
        ): InspectionRepo{
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: InspectionRepo(
                        inspectionDataSource,
                        inspectionRemoteSource,
                        networkStateRepo
                ).also { INSTANCE = it }
            }
        }
    }

    override fun getInspection(inspectionId: String, callback: (Inspection?) -> Unit) {
        localDataSource.getInspection(inspectionId) {
            if (it == null) remoteDataSource.getInspection(inspectionId, callback)
            else callback(it)
        }
    }

    override fun getInspections(callback: (List<Inspection>) -> Unit) {

        if (!networkStateRepo.connectedToInternet()) {
            localDataSource.getInspections(callback)
            return
        }

        if (firstTimeGetData) {
            firstTimeGetData = false
            loadFromRemoteToLocal(callback)
            return
        }

        localDataSource.getInspections { inspections ->
            if (inspections.isEmpty()) {
                loadFromRemoteToLocal(callback)
            } else {
                callback(inspections)
            }
        }
    }

    override fun saveInspections(inspections: List<Inspection>) {
        val submitted = inspections.filter { it.isSubmitted!! }
        val unSubmitted = inspections.filter { !it.isSubmitted!! }
        remoteDataSource.saveInspections(submitted)
        localDataSource.saveInspections(unSubmitted)
    }

    override fun saveInspection(inspection: Inspection) {
        if (inspection.isSubmitted!!) remoteDataSource.saveInspection(inspection)
        localDataSource.saveInspection(inspection)
    }

    private fun loadFromRemoteToLocal(callback: (List<Inspection>) -> Unit){
        remoteDataSource.getInspections{
            localDataSource.saveInspections(it)
            localDataSource.getInspections{ projects ->
                callback(projects)
            }
        }
    }
}