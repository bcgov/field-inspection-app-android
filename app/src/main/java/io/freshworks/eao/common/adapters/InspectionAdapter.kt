package io.freshworks.eao.common.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.freshworks.eao.R
import io.freshworks.eao.data.model.Inspection
import io.freshworks.eao.screens.inspections.InspectionClickListener
import io.freshworks.eao.screens.inspections.InspectionViewHolder

class InspectionAdapter(
        private val listener: InspectionClickListener
) : RecyclerView.Adapter<InspectionViewHolder>() {

    private val inspections = ArrayList<Inspection>()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): InspectionViewHolder {
        val view = LayoutInflater.from(parent?.context)
                .inflate(R.layout.item_inspection, parent, false)
        return InspectionViewHolder(view)
    }

    override fun onBindViewHolder(holder: InspectionViewHolder?, position: Int) {
        holder?.bind(inspections[position], listener)
    }

    override fun getItemCount(): Int {
        return inspections.count()
    }

    fun addInspections(inspections: List<Inspection>) {
        this.inspections.addAll(inspections)
        notifyDataSetChanged()
    }

    fun clearInspections() {
        this.inspections.clear()
        notifyDataSetChanged()
    }

}
