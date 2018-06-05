package io.freshworks.eao.screens.observationelements

import io.freshworks.eao.common.base.BaseView
import io.freshworks.eao.data.model.Inspection

interface ObservationElementsContract {
    interface View : BaseView<ObservationElementsContract.Presenter> {
        fun setUpBackBtnListener()
        fun setUpEditInspectionBtnListener()
        fun setUpInspection(inspection: Inspection)

        fun goToInspectionsScreen()
        fun goToInspectionSetUp(inspectionId: String)
    }

    interface Presenter {
        fun initialize()

        fun onBackBtnClicked()
        fun onSetUpInspectionBtnClicked()
    }
}
