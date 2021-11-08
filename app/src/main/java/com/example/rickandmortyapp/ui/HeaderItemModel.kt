package com.example.rickandmortyapp.ui

import android.view.View
import android.view.ViewParent
import androidx.appcompat.widget.AppCompatTextView
import com.airbnb.epoxy.*
import com.example.rickandmortyapp.R

@EpoxyModelClass
class HeaderItemModel(private val title: String) : EpoxyModelWithHolder<HeaderItemModel.Holder>() {

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.title.text = title
    }

    class Holder : EpoxyHolder() {

        lateinit var title: AppCompatTextView

        override fun bindView(itemView: View) {
            title = itemView.findViewById(R.id.headerTextView)
        }
    }

    override fun getDefaultLayout(): Int {
        return R.layout.item_header
    }

    override fun createNewHolder(parent: ViewParent): Holder {
        return Holder()
    }
}