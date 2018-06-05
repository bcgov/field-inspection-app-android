package io.freshworks.eao.screens.newinspection

import io.freshworks.eao.common.base.BaseView
import java.util.*


interface NewInspectionContract {

    interface View : BaseView<NewInspectionContract.Presenter> {

        fun setUpBackBtnListener()
        fun setUpLinkProjectBtnListener()
        fun setUpStartDateBtnListener()
        fun setUpEndDateBtnListener()
        fun setUpAddInspectionBtnListener()

        fun setInspectorText()
        fun setStartDataText(date: String)
        fun setEndDataText(date: String)
        fun setProjectNameText(name: String?)

        fun showStartDatePicker()
        fun showEndDatePicker()
        fun showMissingProjectError()
        fun showMissingTitleError()
        fun showMissingInspectorError()
        fun showMissingInspectorNumberError()
        fun showMissingStartDateError()
        fun showMissingEndDateError()

        fun goToLinkProjectScreen()
        fun goToObservationElementsScreen(inspectionId: String)

        fun goToPreviousScreen()
    }

    interface Presenter {
        fun initialize()

        fun onBackBtnClicked()
        fun onLinkProjectClicked()
        fun onStartDateClicked()
        fun onEndDateClicked()
        fun onAddInspectionClicked(project: String, title: String, inspector: String,
                                   inspectorNumber: String, startDate: Date?, endDate: Date?)
        fun onStartDateSelected(calendar: Calendar)
        fun onEndDateSelected(calendar: Calendar)
        fun onProjectNameReceived(name: String?)

        fun getParseUser() : String
    }
}
