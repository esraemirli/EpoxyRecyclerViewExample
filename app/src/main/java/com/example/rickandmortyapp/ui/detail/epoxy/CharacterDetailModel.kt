package com.example.rickandmortyapp.ui.detail.epoxy

import android.view.View
import android.view.ViewParent
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.model.entity.Character

class CharacterDetailModel(
    private val character: Character
) : EpoxyModelWithHolder<CharacterDetailModel.Holder>() {

    override fun bind(holder: Holder) {
        super.bind(holder)
        with(character) {
            val options = RequestOptions().placeholder(R.drawable.ic_launcher_background)
            Glide.with(holder.image.context)
                .applyDefaultRequestOptions(options)
                .load(imageUrl).into(holder.image)

            holder.status.text = status
            holder.species.text = species
            holder.location.text = location?.name
        }
    }

    class Holder : EpoxyHolder() {
        lateinit var image: ImageView
        lateinit var status: AppCompatTextView
        lateinit var species: AppCompatTextView
        lateinit var location: AppCompatTextView

        override fun bindView(itemView: View) {
            image = itemView.findViewById(R.id.imageView)
            status = itemView.findViewById(R.id.statusTextView)
            species = itemView.findViewById(R.id.speciesTextView)
            location = itemView.findViewById(R.id.locationTextView)
        }
    }

    override fun getDefaultLayout(): Int {
        return R.layout.detail_character
    }

    override fun createNewHolder(parent: ViewParent): CharacterDetailModel.Holder {
        return Holder()
    }
}