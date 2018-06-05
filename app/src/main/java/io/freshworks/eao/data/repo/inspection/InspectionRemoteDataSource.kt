package io.freshworks.eao.data.repo.inspection

import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser
import io.freshworks.eao.data.model.Inspection
import java.util.*


object InspectionRemoteDataSource : InspectionDataSource{

    override fun getInspection(inspectionId: String, callback: (Inspection?) -> Unit) {
        ParseQuery.getQuery(Inspection::class.java)
                .whereEqualTo("objectId", inspectionId)
                .findInBackground { inspections, e ->
                    if (e != null) callback(null)
                    callback(inspections.firstOrNull())
                }
    }

    override fun getInspections(callback: (List<Inspection>) -> Unit) {
        ParseQuery.getQuery(Inspection::class.java)
                .whereEqualTo("userId", ParseUser.getCurrentUser().objectId)
                .findInBackground{ inspections, e ->
                    if (e != null) callback(ArrayList())
                    if (inspections != null) callback(inspections)
                }
    }

    override fun saveInspections(inspections: List<Inspection>) {
        ParseObject.saveAllInBackground(inspections)
    }

    override fun saveInspection(inspection: Inspection) {
        inspection.saveInBackground()
    }
}