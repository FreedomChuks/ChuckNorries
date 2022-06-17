package com.example.chucknorries.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.chucknorries.databinding.FragmentSearchJokeBinding
import com.example.chucknorries.domain.JokesEntity
import com.example.chucknorries.ui.adapter.SearchAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchJokeFragment : Fragment() {

    private var _binding:FragmentSearchJokeBinding?=null
    private val binding  get() = _binding!!

    private val adapter  = SearchAdapter{data->onClick(data)}

    private fun onClick(data:JokesEntity) {

    }

    private val viewModel by viewModels<JokesVM>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchJokeBinding.inflate(layoutInflater,container,false)
        subscribeObserver()
        setUpRecycleriew()
        return binding.root
    }

    private fun setUpRecycleriew() {

    }

    private fun subscribeObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.collect{

                }
            }
        }
    }

}