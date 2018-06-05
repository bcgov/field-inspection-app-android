package io.freshworks.eao.screens.inspections.inprogress

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import io.freshworks.eao.Constants
import io.freshworks.eao.R
import io.freshworks.eao.data.model.Inspection
import io.freshworks.eao.screens.inspections.InspectionClickListener
import io.freshworks.eao.common.adapters.InspectionAdapter
import io.freshworks.eao.di.Injection
import io.freshworks.eao.di.InjectionUtils
import io.freshworks.eao.screens.inspections.InspectionsActivity
import io.freshworks.eao.screens.inspectionsetup.InspectionSetUpActivity
import io.freshworks.eao.screens.newinspection.NewInspectionActivity
import kotlinx.android.synthetic.main.fragment_in_progress.*

class InProgressFragment : Fragment(), InProgressContract.View {

    override lateinit var presenter: InProgressContract.Presenter

    private lateinit var adapter: InspectionAdapter
    private lateinit var currentInspection: Inspection

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_in_progress, container, false)
    }
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val networkStateRepo = Injection.provideNetworkStateRepo(context)
        val inspectionRepo = InjectionUtils.getInspectionRepo(context)
        InProgressPresenter(this, inspectionRepo, networkStateRepo)
        presenter.initialize()
    }

    override fun setUpInspectionList() {
        adapter = InspectionAdapter(object : InspectionClickListener {
            override fun onViewBtnClicked(inspection: Inspection) {}
            override fun onDeleteBtnClicked(inspection: Inspection) {}
            override fun onEditBtnClicked(inspection: Inspection) {
                currentInspection = inspection
                presenter.onInspectionEditBtnClicked(currentInspection)
            }

            override fun onUploadBtnClicked(inspection: Inspection) {
                currentInspection = inspection
                presenter.onInspectionUploadBtnClicked()
            }
        })
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    override fun setUpNewInspectionBtnListener() {
        newInspectionBtn.setOnClickListener({
            presenter.onNewInspectionBtnClicked()
        })
    }

    override fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    override fun hideNoInspections() {
        recyclerView.visibility = View.VISIBLE
        noInspectionTv.visibility = View.GONE
    }

    override fun showNoInspections() {
        recyclerView.visibility = View.GONE
        noInspectionTv.visibility = View.VISIBLE
    }

    override fun showInspections(inspections: List<Inspection>) {
        adapter.clearInspections()
        adapter.addInspections(inspections)
    }

    override fun showInternetError() {
        Toast.makeText(context, getString(R.string.you_need_internet_to_upload), Toast.LENGTH_LONG).show()
    }

    override fun showConfirmUploadDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(R.string.are_you_sure)
        builder.setMessage(R.string.you_will_not_be_able_to_edit_after_submission)
                .setPositiveButton(R.string.yes) { _, _ ->
                    presenter.onConfirmUploadBtnClicked(currentInspection)
                    (activity as InspectionsActivity).reloadSubmittedList()
                }
                .setNegativeButton(R.string.cancel, null)
                .create()
                .show()
    }

    override fun goToNewInspectionScreen() {
        val intent = Intent(activity, NewInspectionActivity::class.java)
        startActivity(intent)
    }

    override fun goToInspectionSetUp(inspectionId: String) {
        val intent = Intent(activity, InspectionSetUpActivity::class.java)
        intent.putExtra(Constants.EXTRA_INSPECTION_ID, inspectionId)
        startActivity(intent)
    }
}
