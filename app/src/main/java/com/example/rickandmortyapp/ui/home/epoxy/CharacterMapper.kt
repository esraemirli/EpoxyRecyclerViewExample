package com.example.rickandmortyapp.ui.home.epoxy

import com.example.rickandmortyapp.model.entity.Character
import javax.inject.Inject

class CharacterMapper @Inject constructor() : Function1<List<Character>, List<Character>> {
    override fun invoke(list: List<Character>): List<Character> {
        return list.map {
            Character(id = it.id, name = it.name, species = it.species, imageUrl = it.imageUrl)
        }
    }
}