package io.freshworks.eao.screens.inspectionsetup

import io.freshworks.eao.common.base.BaseView
import io.freshworks.eao.data.model.Inspection
import java.util.*

interface InspectionSetUpContract {
    interface View : BaseView<InspectionSetUpContract.Presenter>{

        var currentInspection: Inspection?

        fun setUpBackBtnClicked()
        fun setUpOnSaveBtnClicked()
        fun setUpLinkProjectBtnListener()
        fun setUpStartDateBtnListener()
        fun setUpEndDateBtnListener()
        fun setInspectionFields(inspection: Inspection)

        fun showMissingProjectError()
        fun showMissingTitleError()
        fun showMissingInspectorError()
        fun showMissingInspectorNumberError()
        fun showMissingStartDateError()
        fun showMissingEndDateError()

        fun setStartDataText(date: String)
        fun setEndDataText(date: String)
        fun setProjectNameText(name: String)

        fun showStartDatePicker()
        fun showEndDatePicker()

        fun goToPreviousScreen()
        fun goToInspectionsScreen()
        fun goToLinkProjectScreen()
    }

    interface Presenter {
        fun initialize()

        fun onBackBtnClicked()
        fun onLinkProjectClicked()
        fun onStartDateClicked()
        fun onEndDateClicked()

        fun onSaveBtnClicked(title: String, inspector: String, number: String)
        fun onStartDateSelected(calendar: Calendar)
        fun onEndDateSelected(calendar: Calendar)
        fun onProjectNameReceived(name: String?)
    }
}