package io.freshworks.eao.screens.settings

import io.freshworks.eao.common.base.BaseView
import io.freshworks.eao.screens.login.LoginContract

interface SettingsContract {

    interface View: BaseView<SettingsContract.Presenter> {
        fun setUpBackBtnListener()
        fun setUpWebsiteBtnListener()
        fun setUpLogOutBtn()

        fun loggingOut()

        fun goToPreviousScreen()
    }

    interface Presenter {
        fun initialize()

        fun onBackBtnClicked()
        fun onLogoutClicked()
    }
}
