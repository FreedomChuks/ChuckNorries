package com.example.chucknorries.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chucknorries.R
import com.example.chucknorries.databinding.FragmentJokeCategoryBinding
import com.example.chucknorries.databinding.FragmentSearchJokeBinding
import com.example.chucknorries.ui.adapter.CategoryAdapter
import com.example.chucknorries.ui.adapter.SearchAdapter
import com.example.chucknorries.ui.viewState.JokeEvent
import com.example.chucknorries.ui.viewState.showError
import com.example.chucknorries.ui.viewState.showJokeDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class JokeCategoryFragment : Fragment() {

    private var _binding: FragmentJokeCategoryBinding?=null
    private val binding  get() = _binding!!

    private val viewModel by viewModels<JokesVM>()

    private val adapter  = CategoryAdapter{ data->onClick(data)}

    private fun onClick(data: String) {
        viewModel.onTriggerEvent(JokeEvent.FetchByCategory(data))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJokeCategoryBinding.inflate(layoutInflater,container,false)
        subscriberObserver()
        setUpUi()
        return binding.root
    }


    private fun subscriberObserver() {
        viewModel.onTriggerEvent(JokeEvent.FetchCategory)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.collect{

                    adapter.submitList(it.categories)
                    binding.categoryList.adapter=adapter

                    if (it.jokeData.isNotEmpty()){
                        requireContext().showJokeDialog(it.jokeData[0].value) {viewModel.jokeShown()}
                    }


                    it.errorMessage?.let { e->
                        context?.showError(e){viewModel.errorShown()}
                    }


                }
            }
        }
    }

    private fun setUpUi() {
        binding.categoryList.layoutManager= LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}