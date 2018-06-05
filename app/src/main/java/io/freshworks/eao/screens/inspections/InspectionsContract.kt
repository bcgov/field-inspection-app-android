package io.freshworks.eao.screens.inspections

import io.freshworks.eao.common.base.BaseView
import io.freshworks.eao.screens.login.LoginContract

interface InspectionsContract{

    interface View : BaseView<Presenter> {
        fun setUpTabs()
        fun setUpSettingBtn()
        fun setUpGoogleMap()

        fun reloadSubmittedList()

        fun goToSettingsScreen()
    }

    interface Presenter {
        fun initialize()
        fun onSettingsBtnClicked()
        fun onPageSelected(position: Int)
        fun onTabSelected(position: Int)
    }
}
