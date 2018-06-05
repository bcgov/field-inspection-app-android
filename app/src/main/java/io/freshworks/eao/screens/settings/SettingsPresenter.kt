package io.freshworks.eao.screens.settings

import com.parse.ParseUser

class SettingsPresenter ( private var view: SettingsContract.View ) : SettingsContract.Presenter{

    init {
        view.presenter = this
    }

    override fun initialize() {
        view.setUpLogOutBtn()
        view.setUpWebsiteBtnListener()
    }

    override fun onBackBtnClicked() {
        view.goToPreviousScreen()
    }

    override fun onLogoutClicked() {
        ParseUser.logOutInBackground {
            view.loggingOut()
        }
    }
}
