package com.example.rickandmortyapp.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.epoxy.EpoxyRecyclerView
import com.example.rickandmortyapp.databinding.FragmentCharacterDetailBinding
import com.example.rickandmortyapp.model.entity.Character
import com.example.rickandmortyapp.ui.detail.epoxy.CharacterDetailEpoxyController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailFragment : Fragment() {
    private val viewModel: CharacterDetailViewModel by viewModels()
    private lateinit var _binding: FragmentCharacterDetailBinding
    private val args: CharacterDetailFragmentArgs by navArgs()
    private lateinit var controller: CharacterDetailEpoxyController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controller = CharacterDetailEpoxyController()
        initViews()
    }

    private fun initViews() {
        viewModel.loader.observe(this as LifecycleOwner) { loading ->
            when (loading) {
                true -> _binding.loader.visibility = View.VISIBLE
                else -> _binding.loader.visibility = View.GONE
            }
        }
        viewModel.characterId = args.characterId
        viewModel.characterDetail.observe(this as LifecycleOwner) { response ->
            if (response.getOrNull() != null) {
                setUpList(_binding.epoxyRecyclerView, response.getOrNull()!!)

            } else {
                Log.d("DETAIL_FRAGMENT_FAIL:", "${response.isFailure}")
            }
        }
    }

    private fun setUpList(
        view: View?,
        characterDetail: Character
    ) {
        with(view as EpoxyRecyclerView) {
            layoutManager = LinearLayoutManager(context)
            initEpoxyController(characterDetail)
        }
    }

    private fun initEpoxyController(characterDetail: Character) {
        controller.characterDetail = characterDetail
        _binding.epoxyRecyclerView.setControllerAndBuildModels(controller)
    }

}