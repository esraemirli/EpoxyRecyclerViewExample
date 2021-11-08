package com.example.rickandmortyapp.ui.home.epoxy

import android.view.View
import android.view.ViewParent
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.model.entity.Character
import com.example.rickandmortyapp.ui.home.epoxy.HomeEpoxyController.*

@EpoxyModelClass
class CharacterItemModel(
    private var character: Character
) : EpoxyModelWithHolder<CharacterItemModel.Holder>() {

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var callbacks: Callbacks? = null

    override fun bind(holder: Holder) {
        super.bind(holder)
        with(character) {
            val options = RequestOptions().placeholder(R.drawable.ic_launcher_background)
            Glide.with(holder.image.context)
                .applyDefaultRequestOptions(options)
                .load(imageUrl).into(holder.image)
            holder.name.text = name
            holder.species.text = species

            holder.root.setOnClickListener {
                callbacks?.onClick(character.id)
            }
        }
    }

    class Holder : EpoxyHolder() {
        lateinit var root: LinearLayout
        lateinit var image: ImageView
        lateinit var name: AppCompatTextView
        lateinit var species: AppCompatTextView

        override fun bindView(itemView: View) {
            root = itemView.findViewById(R.id.root)
            image = itemView.findViewById(R.id.imageView)
            name = itemView.findViewById(R.id.nameTextView)
            species = itemView.findViewById(R.id.speciesTextView)
        }
    }

    override fun getDefaultLayout(): Int {
        return R.layout.item_character
    }

    override fun createNewHolder(parent: ViewParent): Holder {
        return Holder()
    }
}