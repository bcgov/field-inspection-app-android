package io.freshworks.eao.screens.newinspection

import com.parse.ParseUser
import io.freshworks.eao.common.toLongCanadianDateString
import io.freshworks.eao.data.model.Inspection
import io.freshworks.eao.data.repo.inspection.InspectionRepo
import java.util.*

class NewInspectionPresenter(
        private val view: NewInspectionContract.View,
        private val inspectionRepo: InspectionRepo
) : NewInspectionContract.Presenter {

    init {
        view.presenter = this
    }

    override fun initialize() {
        view.setUpBackBtnListener()
        view.setUpLinkProjectBtnListener()
        view.setUpStartDateBtnListener()
        view.setUpEndDateBtnListener()
        view.setUpAddInspectionBtnListener()
        view.setInspectorText()
    }

    override fun onBackBtnClicked() {
        view.goToPreviousScreen()
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

    override fun onAddInspectionClicked(project: String,
                                        title: String,
                                        inspector: String,
                                        inspectorNumber: String,
                                        startDate: Date?,
                                        endDate: Date?) {
        when {
            project.isBlank() -> view.showMissingProjectError()
            title.isBlank() -> view.showMissingTitleError()
            inspector.isBlank() -> view.showMissingInspectorError()
            inspectorNumber.isBlank() -> view.showMissingInspectorNumberError()
            startDate == null -> view.showMissingStartDateError()
            endDate == null -> view.showMissingEndDateError()
            else -> {
                val inspection = createInspection(
                        project, title, inspector, inspectorNumber, startDate, endDate
                )
                inspectionRepo.saveInspection(inspection)
                view.goToObservationElementsScreen(inspection.objectId)
            }
        }
    }

    override fun onStartDateSelected(calendar: Calendar) {
        view.setStartDataText(calendar.time.toLongCanadianDateString())
    }

    override fun onEndDateSelected(calendar: Calendar) {
        view.setEndDataText(calendar.time.toLongCanadianDateString())
    }

    override fun onProjectNameReceived(name: String?) {
        view.setProjectNameText(name)
    }

    private fun createInspection(project: String,
                                 title: String,
                                 inspector: String,
                                 inspectorNumber: String,
                                 startDate: Date?,
                                 endDate: Date?) : Inspection {
        val inspection = Inspection()
        inspection.let {
            it.objectId = UUID.randomUUID().toString() + System.currentTimeMillis().toString()
            it.isSubmitted = false
            it.userId = ParseUser.getCurrentUser().objectId
            it.project = project
            it.title = title
            it.inspector = inspector
            it.number = inspectorNumber
            it.startDate = startDate
            it.endDate = endDate
        }
        return inspection
    }

    override fun getParseUser(): String {
        return ParseUser.getCurrentUser().username
    }

}
