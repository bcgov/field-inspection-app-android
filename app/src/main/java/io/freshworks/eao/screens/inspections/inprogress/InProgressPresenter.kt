package io.freshworks.eao.screens.inspections.inprogress

import io.freshworks.eao.data.model.Inspection
import io.freshworks.eao.data.repo.inspection.InspectionDataSource
import io.freshworks.eao.data.repo.inspection.InspectionRepo
import io.freshworks.eao.data.repo.network.NetworkStateRepo

class InProgressPresenter (
        private val view: InProgressContract.View,
        private val inspectionRepo: InspectionRepo,
        private val networkStateRepo: NetworkStateRepo
) : InProgressContract.Presenter {

    init {
        view.presenter = this
    }

    override fun initialize() {
        view.setUpInspectionList()
        view.setUpNewInspectionBtnListener()
        loadInspections()
    }

    override fun onNewInspectionBtnClicked() {
        view.goToNewInspectionScreen()
    }

    override fun onInspectionUploadBtnClicked() {
        if (!networkStateRepo.connectedToInternet()) {
            view.showInternetError()
            return
        }

        view.showConfirmUploadDialog()
    }

    override fun onInspectionEditBtnClicked(inspection: Inspection) {
        view.goToInspectionSetUp(inspection.objectId)
    }

    override fun onConfirmUploadBtnClicked(inspection: Inspection) {
        inspection.isSubmitted = true
        inspectionRepo.saveInspection(inspection)
        loadInspections()
    }

    private fun loadInspections() {
        inspectionRepo.getInspections{ inspections ->

            val inProgressInspections = inspections.filter { it.isSubmitted == false }

            view.hideNoInspections()
            view.showInspections(inProgressInspections)

            if (inProgressInspections.isEmpty()) view.showNoInspections()
            view.hideProgressBar()
        }
    }

}
