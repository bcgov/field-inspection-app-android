package io.freshworks.eao.screens.login

import io.freshworks.eao.common.base.BaseView

interface LoginContract {

    interface View : BaseView<Presenter> {
        fun setUpLoginBtn()
        fun setUpForgotPasswordBtn()

        fun showForgotPasswordView(loginPresenter: LoginPresenter)
        fun showInternetError()
        fun showLoginError(error: String)

        fun goToInspectionsScreen()
    }

    interface Presenter {
        fun initialize()

        fun onLoginBtnClicked(username: String, password: String)
        fun onForgotPasswordBtnClicked()
    }

}