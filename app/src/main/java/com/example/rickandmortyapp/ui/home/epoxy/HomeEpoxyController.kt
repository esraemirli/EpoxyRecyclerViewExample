package com.example.rickandmortyapp.ui.home.epoxy

import com.airbnb.epoxy.*
import com.example.rickandmortyapp.model.entity.Character
import com.example.rickandmortyapp.ui.HeaderItemModel

class HomeEpoxyController(
    private var callbacks: Callbacks
) : EpoxyController() {

    interface Callbacks {
        fun onClick(id: Int)
    }

    var characterList: List<Character>? = null
        set(value) {
            field = value
            if (field != null) {
                requestModelBuild()
            }
        }

    override fun buildModels() {
        HeaderItemModel(
            "Character List"
        ).id(HEADER_ID).addTo(this)

        characterList!!.forEach { character ->
            CharacterItemModel(character).also { itemModel ->
                itemModel.callbacks = callbacks
            }.id(INFO_ID).addTo(this)
        }
    }

    companion object {
        private const val HEADER_ID = "header"
        private const val INFO_ID = "info"
    }

}