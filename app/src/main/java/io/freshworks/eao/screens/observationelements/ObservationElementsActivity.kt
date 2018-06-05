package io.freshworks.eao.screens.observationelements

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.freshworks.eao.Constants
import io.freshworks.eao.R
import io.freshworks.eao.data.model.Inspection
import io.freshworks.eao.di.InjectionUtils
import io.freshworks.eao.screens.inspections.InspectionsActivity
import io.freshworks.eao.screens.inspectionsetup.InspectionSetUpActivity
import kotlinx.android.synthetic.main.activity_observation_elements.*
import kotlinx.android.synthetic.main.toolbar_with_back_and_save.*
import kotlinx.android.synthetic.main.toolbar_with_back_and_save.view.*

class ObservationElementsActivity : AppCompatActivity(), ObservationElementsContract.View{
    override lateinit var presenter: ObservationElementsContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_observation_elements)
        toolbar.titleToolbar.text = getString(R.string.observation_elements)

        val inspectionRepo = InjectionUtils.getInspectionRepo(this)
        val inspectionId = intent.extras.getString(Constants.EXTRA_INSPECTION_ID, "")

        ObservationElementsPresenter(this, inspectionRepo, inspectionId)
        presenter.initialize()
    }

    override fun setUpBackBtnListener() {
        backBtn.setOnClickListener {
            presenter.onBackBtnClicked()
        }
    }

    override fun setUpEditInspectionBtnListener() {
        editInspectionSetUpBtn.setOnClickListener{
            presenter.onSetUpInspectionBtnClicked()
        }
    }

    override fun setUpInspection(inspection: Inspection) {

    }

    override fun goToInspectionsScreen() {
        val intent = Intent(this, InspectionsActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun goToInspectionSetUp(inspectionId: String) {
        val intent = Intent(this, InspectionSetUpActivity::class.java)
        intent.putExtra(Constants.EXTRA_INSPECTION_ID, inspectionId)
        startActivity(intent)
    }
}
