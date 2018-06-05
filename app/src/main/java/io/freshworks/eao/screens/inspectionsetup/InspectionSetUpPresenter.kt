package io.freshworks.eao.screens.inspectionsetup

import io.freshworks.eao.common.toLongCanadianDateString
import io.freshworks.eao.data.repo.inspection.InspectionRepo
import java.util.*

class InspectionSetUpPresenter(
        private var view : InspectionSetUpContract.View,
        private val inspectionRepo: InspectionRepo,
        private val inspectionId: String
) : InspectionSetUpContract.Presenter{

    init {
        view.presenter = this
    }

    override fun initialize() {
        view.setUpBackBtnClicked()
        view.setUpOnSaveBtnClicked()
        view.setUpStartDateBtnListener()
        view.setUpEndDateBtnListener()
        view.setUpLinkProjectBtnListener()

        inspectionRepo.getInspection(inspectionId) {
            if (it != null) {
                view.currentInspection = it
                view.setInspectionFields(it)
            }
        }
    }

    override fun onBackBtnClicked() {
        view.goToPreviousScreen()
    }

    override fun onSaveBtnClicked(title: String, inspector: String, number: String) {

        val inspection = view.currentInspection ?: return

        inspection.title = title
        inspection.inspector = inspector
        inspection.number = number

        when {
            inspection.title!!.isBlank() -> view.showMissingTitleError()
            inspection.inspector!!.isBlank() -> view.showMissingInspectorError()
            inspection.number!!.isBlank() -> view.showMissingInspectorNumberError()
            inspection.project!!.isBlank() -> view.showMissingProjectError()
            inspection.startDate == null -> view.showMissingStartDateError()
            inspection.endDate == null -> view.showMissingEndDateError()
            else -> {
                inspectionRepo.saveInspection(inspection)
                view.goToInspectionsScreen()
            }
        }
    }

    override fun onLinkProjectClicked() {
        view.goToLinkProjectScreen()
    }

    override fun onStartDateClicked() {
        view.showStartDatePicker()
    }

    override fun onEndDateClicked() {
        view.showEndDatePicker()
    }

    override fun onStartDateSelected(calendar: Calendar) {
        view.currentInspection?.startDate = calendar.time
        view.setStartDataText(calendar.time.toLongCanadianDateString())
    }

    override fun onEndDateSelected(calendar: Calendar) {
        view.currentInspection?.endDate = calendar.time
        view.setEndDataText(calendar.time.toLongCanadianDateString())
    }

    override fun onProjectNameReceived(name: String?) {
        val project = name ?: return

        view.currentInspection?.project = project
        view.setProjectNameText(project)
    }
}