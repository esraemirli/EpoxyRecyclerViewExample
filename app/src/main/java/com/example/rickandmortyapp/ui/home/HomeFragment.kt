package com.example.rickandmortyapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.epoxy.EpoxyRecyclerView
import com.example.rickandmortyapp.databinding.FragmentHomeBinding
import com.example.rickandmortyapp.model.entity.Character
import com.example.rickandmortyapp.ui.home.epoxy.HomeEpoxyController
import com.example.rickandmortyapp.ui.home.epoxy.HomeEpoxyController.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), Callbacks {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var _binding: FragmentHomeBinding
    private lateinit var controller: HomeEpoxyController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controller = HomeEpoxyController(this)
        initViews()
    }

    private fun initViews() {
        viewModel.loader.observe(this as LifecycleOwner) { loading ->
            when (loading) {
                true -> _binding.loader.visibility = View.VISIBLE
                else -> _binding.loader.visibility = View.GONE
            }
        }

        viewModel.characterList.observe(this as LifecycleOwner) { list ->
            if (list.getOrNull() != null) {
                setUpList(_binding.epoxyRecyclerView, list.getOrNull()!!)
            } else {
                Log.d("HOME_FRAGMENT_FAIL:", "${list.isFailure}")
            }
        }
    }

    private fun setUpList(
        view: View?,
        characterList: List<Character>
    ) {
        with(view as EpoxyRecyclerView) {
            layoutManager = LinearLayoutManager(context)
            initEpoxyController(characterList)
        }
    }

    private fun initEpoxyController(characterList: List<Character>) {
        controller.characterList = characterList
        _binding.epoxyRecyclerView.setControllerAndBuildModels(controller)
    }

    override fun onClick(id: Int) {
        Log.d("HOME_FRAGMENT_ONCLICK:", "Character Id : $id")
    }

}