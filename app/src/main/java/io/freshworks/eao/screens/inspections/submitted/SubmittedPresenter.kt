package io.freshworks.eao.screens.inspections.submitted

import io.freshworks.eao.data.repo.inspection.InspectionRepo

class SubmittedPresenter(
        private var view : SubmittedContract.View,
        private var inspectionRepo: InspectionRepo
) : SubmittedContract.Presenter{

    init {
        view.presenter = this
    }

    override fun initialize() {
        view.setUpInspectionsList()
        loadInspections()
    }

    override fun loadInspections() {
        view.hideNoInspections()
        inspectionRepo.getInspections{ inspections ->

            val submittedInspections = inspections.filter { it.isSubmitted == true }
            view.showInspections(submittedInspections)

            if (submittedInspections.isEmpty()) view.showNoInspections()
            view.hideProgressBar()
        }
    }
}
