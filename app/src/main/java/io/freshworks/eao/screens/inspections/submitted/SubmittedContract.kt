package io.freshworks.eao.screens.inspections.submitted

import io.freshworks.eao.common.base.BaseView
import io.freshworks.eao.data.model.Inspection

interface SubmittedContract {
    interface View : BaseView<SubmittedContract.Presenter>{
        fun setUpInspectionsList()

        fun reloadList()

        fun hideProgressBar()
        fun hideNoInspections()

        fun showInspections(inspections: List<Inspection>)
        fun showNoInspections()
    }

    interface Presenter{
        fun initialize()
        fun loadInspections()
    }
}
