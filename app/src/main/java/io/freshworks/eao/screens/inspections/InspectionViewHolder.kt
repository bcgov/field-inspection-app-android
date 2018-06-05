package io.freshworks.eao.screens.inspections

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import io.freshworks.eao.common.toLongCanadianDateString
import io.freshworks.eao.data.model.Inspection
import kotlinx.android.synthetic.main.item_inspection.view.*

class InspectionViewHolder(
        itemView: View
) : RecyclerView.ViewHolder(itemView) {

    private val editBtn: ImageButton = itemView.editBtn
    private val uploadBtn: ImageButton = itemView.uploadBtn
    private val viewBtn: ImageButton = itemView.viewBtn
    private val deleteBtn: ImageButton = itemView.deleteBtn
    private val inProgressBtnGroup: LinearLayout = itemView.inProgressBtnGroup
    private val submittedBtnGroup: LinearLayout = itemView.submittedBtnGroup
    private val titleTv: TextView = itemView.titleTv
    private val subtitleTv: TextView = itemView.subtitleTv
    private val dateTv: TextView = itemView.dateTv

    @SuppressLint("SetTextI18n")
    fun bind(inspection: Inspection, listener: InspectionClickListener) {
        editBtn.setOnClickListener { listener.onEditBtnClicked(inspection) }
        uploadBtn.setOnClickListener { listener.onUploadBtnClicked(inspection) }
        viewBtn.setOnClickListener { listener.onViewBtnClicked(inspection) }
        deleteBtn.setOnClickListener { listener.onDeleteBtnClicked(inspection) }

        inProgressBtnGroup.visibility = if (inspection.isSubmitted!!) View.GONE else View.VISIBLE
        submittedBtnGroup.visibility = if (inspection.isSubmitted!!) View.VISIBLE else View.GONE

        titleTv.text = inspection.title
        subtitleTv.text = inspection.project
        dateTv.text = "${inspection.startDate?.toLongCanadianDateString()} - ${inspection.endDate?.toLongCanadianDateString()}"
    }

}
