package io.freshworks.eao.screens.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.v7.app.AppCompatActivity
import io.freshworks.eao.R
import io.freshworks.eao.screens.login.LoginActivity
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.toolbar_with_back.*
import kotlinx.android.synthetic.main.toolbar_with_back.view.*

class SettingsActivity : AppCompatActivity(), SettingsContract.View {

    override lateinit var presenter: SettingsContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        toolbar.title.text = getString(R.string.settings)

        SettingsPresenter(this)
        presenter.initialize()
        backBtn.setOnClickListener { finish() }
    }

    override fun setUpBackBtnListener() {
        backBtn.setOnClickListener{
            presenter.onBackBtnClicked()
        }
    }

    override fun setUpWebsiteBtnListener() {
        projectWebsiteBtn.setOnClickListener {
            val builder = CustomTabsIntent.Builder()
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(this, Uri.parse(getString(R.string.eao_website)))
        }
    }

    override fun setUpLogOutBtn() {
        logoutBtn.setOnClickListener {
            presenter.onLogoutClicked()
        }
    }

    override fun loggingOut() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun goToPreviousScreen() {
        finish()
    }
}
