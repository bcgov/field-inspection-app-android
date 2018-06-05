package io.freshworks.eao.screens.newinspection

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import io.freshworks.eao.Constants
import io.freshworks.eao.R
import io.freshworks.eao.di.InjectionUtils
import io.freshworks.eao.dialogs.DatePickerFragment
import io.freshworks.eao.screens.observationelements.ObservationElementsActivity
import io.freshworks.eao.screens.projectlist.ProjectListActivity
import kotlinx.android.synthetic.main.activity_new_inspection.*
import kotlinx.android.synthetic.main.toolbar_with_back.view.*
import kotlinx.android.synthetic.main.toolbar_with_back_and_save.*
import java.util.*

class NewInspectionActivity : AppCompatActivity(), NewInspectionContract.View {
    var startDate : Date? = null
    var endDate : Date? = null
    override lateinit var presenter: NewInspectionContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_inspection)

        toolbar.title.text = getString(R.string.inspection_set_up)

        val inspectionRepo = InjectionUtils.getInspectionRepo(this)
        NewInspectionPresenter(this, inspectionRepo)
        presenter.initialize()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Constants.REQUEST_CODE_PROJECT_NAME) {
            presenter.onProjectNameReceived(data?.getStringExtra(Constants.EXTRA_PROJECT_NAME))
        }
    }
    override fun setUpBackBtnListener() {
        backBtn.setOnClickListener {
            presenter.onBackBtnClicked()
        }
    }

    override fun setUpLinkProjectBtnListener() {
        linkProjectTv.setOnClickListener {
            presenter.onLinkProjectClicked()
        }
    }

    override fun setUpStartDateBtnListener() {
        inspectionStartDateTv.setOnClickListener {
            presenter.onStartDateClicked()
        }
    }

    override fun setUpEndDateBtnListener() {
        inspectionEndDateTv.setOnClickListener {
            presenter.onEndDateClicked()
        }
    }

    override fun setUpAddInspectionBtnListener() {
        addInspectionBtn.setOnClickListener{
            presenter.onAddInspectionClicked(
                    linkProjectTv.text.toString(),
                    titleEt.text.toString(),
                    inspectorEt.text.toString(),
                    inspectionNumEt.text.toString(),
                    startDate,
                    endDate)
        }
    }

    override fun setInspectorText() {
        inspectorEt.setText(presenter.getParseUser())
    }

    override fun setStartDataText(date: String) {
        inspectionStartDateTv.text = date
    }

    override fun setEndDataText(date: String) {
        inspectionEndDateTv.text = date
    }

    override fun setProjectNameText(name: String?) {
        linkProjectTv.text = name
    }

    override fun goToLinkProjectScreen() {
        val intent = Intent(this, ProjectListActivity::class.java)
        startActivityForResult(intent, Constants.REQUEST_CODE_PROJECT_NAME)
    }

    override fun showStartDatePicker() {
        val datePickerFragment = DatePickerFragment()
        val bundle = Bundle()
        bundle.putLong(Constants.EXTRA_START_DATE, 0L)
        datePickerFragment.arguments = bundle

        datePickerFragment.show(supportFragmentManager, "start")

        datePickerFragment.setListener(object : DatePickerFragment.Listener {
            override fun onDateSelected(calendar: Calendar) {
                startDate = calendar.time
                presenter.onStartDateSelected(calendar)
            }
        })
    }

    override fun showEndDatePicker() {
        val datePickerFragment = DatePickerFragment()
        val bundle = Bundle()
        bundle.putLong(Constants.EXTRA_START_DATE, startDate?.time ?: 0L)
        datePickerFragment.arguments = bundle

        datePickerFragment.show(supportFragmentManager, "end")
        datePickerFragment.setListener(object : DatePickerFragment.Listener {
            override fun onDateSelected(calendar: Calendar) {
                endDate = calendar.time
                presenter.onEndDateSelected(calendar)
            }
        })
    }

    override fun showMissingProjectError() {
        Toast.makeText(this, getString(R.string.please_fill_out_project_fields), Toast.LENGTH_LONG).show()
    }

    override fun showMissingTitleError() {
        Toast.makeText(this, getString(R.string.please_fill_out_title_fields), Toast.LENGTH_LONG).show()
    }

    override fun showMissingInspectorError() {
        Toast.makeText(this, getString(R.string.please_fill_out_inspector_fields), Toast.LENGTH_LONG).show()
    }

    override fun showMissingInspectorNumberError() {
        Toast.makeText(this, getString(R.string.please_fill_out_inspector_number_fields), Toast.LENGTH_LONG).show()
    }

    override fun showMissingStartDateError() {
        Toast.makeText(this, getString(R.string.please_fill_out_start_date_fields), Toast.LENGTH_LONG).show()
    }

    override fun showMissingEndDateError() {
        Toast.makeText(this, getString(R.string.please_fill_out_end_date_fields), Toast.LENGTH_LONG).show()
    }

    override fun goToObservationElementsScreen(inspectionId: String) {
       val intent = Intent(this, ObservationElementsActivity::class.java)
        intent.putExtra(Constants.EXTRA_INSPECTION_ID, inspectionId)
        startActivity(intent)
    }

    override fun goToPreviousScreen() {
        finish()
    }
}
