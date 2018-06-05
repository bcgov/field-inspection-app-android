package io.freshworks.eao.screens.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import io.freshworks.eao.R
import io.freshworks.eao.data.repo.network.NetworkStateRepo
import io.freshworks.eao.screens.inspections.InspectionsActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginContract.View {

    override lateinit var presenter: LoginContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val networkStateRepo = NetworkStateRepo(this)

        LoginPresenter(this, networkStateRepo)
        presenter.initialize()
    }

    override fun setUpLoginBtn() {
        loginBtn.setOnClickListener {
            presenter.onLoginBtnClicked(usernameEt.text.toString(), passwordEt.text.toString())
        }
    }

    override fun setUpForgotPasswordBtn() {
        forgotPasswordBtn.setOnClickListener {
            presenter.onForgotPasswordBtnClicked()
        }
    }

    override fun showForgotPasswordView(loginPresenter: LoginPresenter) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(R.string.error_login)
                .setPositiveButton(R.string.okay, null)
                .create()
                .show()
    }

    override fun showInternetError() {
        Toast.makeText(this, getString(R.string.you_need_internet_to_login), Toast.LENGTH_LONG).show()
    }

    override fun showLoginError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }

    override fun goToInspectionsScreen() {
        val intent = Intent(this, InspectionsActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}
