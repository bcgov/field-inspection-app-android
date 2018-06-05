package io.freshworks.eao.screens.inspections.inprogress

import io.freshworks.eao.common.base.BaseView
import io.freshworks.eao.data.model.Inspection


interface InProgressContract {

    interface View : BaseView<InProgressContract.Presenter>{
        fun setUpInspectionList()
        fun setUpNewInspectionBtnListener()

        fun hideProgressBar()
        fun hideNoInspections()

        fun showInspections(inspections: List<Inspection>)
        fun showNoInspections()
        fun showInternetError()
        fun showConfirmUploadDialog()

        fun goToNewInspectionScreen()
        fun goToInspectionSetUp(inspectionId: String)
    }

    interface Presenter{
        fun initialize()

        fun onNewInspectionBtnClicked()
        fun onInspectionEditBtnClicked(inspection: Inspection)
        fun onInspectionUploadBtnClicked()
        fun onConfirmUploadBtnClicked(inspection: Inspection)
    }

}
