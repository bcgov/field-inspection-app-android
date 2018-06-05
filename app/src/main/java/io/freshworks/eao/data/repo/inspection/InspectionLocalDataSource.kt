package io.freshworks.eao.data.repo.inspection

import com.parse.ParseQuery
import com.parse.ParseUser
import io.freshworks.eao.data.model.Inspection
import java.text.SimpleDateFormat
import java.util.*

object InspectionLocalDataSource : InspectionDataSource {

    override fun getInspection(inspectionId: String, callback: (Inspection?) -> Unit) {
        ParseQuery.getQuery(Inspection::class.java)
                .fromLocalDatastore()
                .whereEqualTo("objectId", inspectionId)
                .findInBackground { inspections, e ->
                    if (e != null) callback(null)
                    callback(inspections.firstOrNull())
                }
    }

    override fun getInspections(callback: (List<Inspection>) -> Unit) {
        ParseQuery.getQuery(Inspection::class.java)
                .fromLocalDatastore()
                .whereEqualTo("userId", ParseUser.getCurrentUser().objectId)
                .findInBackground { inspections, e ->
                    if (e != null) callback(ArrayList())
                    if (inspections != null) callback(inspections)
                }
    }

    override fun saveInspections(inspections: List<Inspection>) {
        ParseUser.pinAllInBackground(inspections)
    }

    override fun saveInspection(inspection: Inspection) {
        inspection.pin()
    }

}