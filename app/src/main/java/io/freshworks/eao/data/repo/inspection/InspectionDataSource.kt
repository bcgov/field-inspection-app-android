package io.freshworks.eao.data.repo.inspection

import io.freshworks.eao.data.model.Inspection
import java.util.*

interface InspectionDataSource {
    fun getInspection(inspectionId: String, callback: (Inspection?) -> Unit)
    fun getInspections(callback: (List<Inspection>) -> Unit)
    fun saveInspections(inspections: List<Inspection>)
    fun saveInspection(inspection: Inspection)
}