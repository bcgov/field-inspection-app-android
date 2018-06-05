package io.freshworks.eao.screens.observationelements

import io.freshworks.eao.data.repo.inspection.InspectionRepo

class ObservationElementsPresenter(
        private var view: ObservationElementsContract.View,
        private val inspectionRepo: InspectionRepo,
        private val inspectionId: String
) : ObservationElementsContract.Presenter {

    init {
        view.presenter = this
    }

    override fun initialize() {
        view.setUpBackBtnListener()
        view.setUpEditInspectionBtnListener()

        inspectionRepo.getInspection(inspectionId) {
            if (it != null) view.setUpInspection(it)
        }
    }

    override fun onBackBtnClicked() {
        view.goToInspectionsScreen()
    }

    override fun onSetUpInspectionBtnClicked() {
        view.goToInspectionSetUp(inspectionId)
    }
}