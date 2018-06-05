package io.freshworks.eao.screens.inspections.submitted

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.freshworks.eao.R
import io.freshworks.eao.data.model.Inspection
import io.freshworks.eao.screens.inspections.InspectionClickListener
import io.freshworks.eao.common.adapters.InspectionAdapter
import io.freshworks.eao.di.InjectionUtils
import kotlinx.android.synthetic.main.fragment_submitted.*

class SubmittedFragment : Fragment(), SubmittedContract.View {

    override lateinit var presenter: SubmittedContract.Presenter
    private lateinit var adapter: InspectionAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_submitted, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val inspectionRepo = InjectionUtils.getInspectionRepo(context)
        SubmittedPresenter(this, inspectionRepo)
        presenter.initialize()
    }

    override fun setUpInspectionsList() {
        adapter = InspectionAdapter(object : InspectionClickListener {
            override fun onDeleteBtnClicked(inspection: Inspection) {}
            override fun onEditBtnClicked(inspection: Inspection) {}
            override fun onUploadBtnClicked(inspection: Inspection) {}
            override fun onViewBtnClicked(inspection: Inspection) {}
        })
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    override fun reloadList() {
       presenter.loadInspections()
    }

    override fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    override fun hideNoInspections() {
        recyclerView.visibility = View.VISIBLE
        noInspectionTv.visibility = View.GONE
    }

    override fun showInspections(inspections: List<Inspection>) {
        adapter.clearInspections()
        adapter.addInspections(inspections)
    }

    override fun showNoInspections() {
        recyclerView.visibility = View.GONE
        noInspectionTv.visibility = View.VISIBLE
    }
}
