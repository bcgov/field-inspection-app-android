package io.freshworks.eao.screens.login

import com.parse.LogInCallback
import com.parse.ParseUser
import io.freshworks.eao.data.repo.network.NetworkStateRepo

class LoginPresenter(
        private var view: LoginContract.View,
        private val networkStateRepo: NetworkStateRepo
) : LoginContract.Presenter {

    init {
        view.presenter = this
    }

    override fun initialize() {
        view.setUpLoginBtn()
        view.setUpForgotPasswordBtn()
    }

    override fun onLoginBtnClicked(username: String, password: String) {

        if (!networkStateRepo.connectedToInternet()) {
            view.showInternetError()
            return
        }

        ParseUser.logInInBackground(
                username,
                password,
                LogInCallback { _, e ->
                    if (e != null) {
                        val errMsg = e.message
                        if (errMsg != null) view.showLoginError(errMsg)
                        return@LogInCallback
                    }

                    view.goToInspectionsScreen()
                })
    }

    override fun onForgotPasswordBtnClicked() {
        view.showForgotPasswordView(this)
    }

}