package io.freshworks.eao.screens.inspections

import io.freshworks.eao.data.model.Inspection

interface InspectionClickListener {
    fun onDeleteBtnClicked(inspection: Inspection)
    fun onEditBtnClicked(inspection: Inspection)
    fun onUploadBtnClicked(inspection: Inspection)
    fun onViewBtnClicked(inspection: Inspection)
}
