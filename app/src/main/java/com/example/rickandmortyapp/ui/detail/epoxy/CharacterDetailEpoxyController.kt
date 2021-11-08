package com.example.rickandmortyapp.ui.detail.epoxy

import com.airbnb.epoxy.EpoxyController
import com.example.rickandmortyapp.model.entity.Character
import com.example.rickandmortyapp.ui.HeaderItemModel

class CharacterDetailEpoxyController : EpoxyController() {

    var characterDetail: Character? = null
        set(value) {
            field = value
            if (field != null) {
                requestModelBuild()
            }
        }

    override fun buildModels() {
        HeaderItemModel(
            characterDetail?.name ?: ""
        ).id(HEADER_ID).addTo(this)

        characterDetail?.let { character ->
            CharacterDetailModel(character).id(INFO_ID).addTo(this)
        }
    }

    companion object {
        private const val HEADER_ID = "header"
        private const val INFO_ID = "info"
    }

}