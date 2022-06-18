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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.example.chucknorries.databinding.FragmentSearchJokeBinding
import com.example.chucknorries.domain.entities.JokesEntity
import com.example.chucknorries.ui.adapter.SearchAdapter
import com.example.chucknorries.ui.uIState.JokeEvent
import com.example.chucknorries.ui.uIState.hapticFeedback
import com.example.chucknorries.ui.uIState.showError
import com.example.chucknorries.ui.uIState.toggleIcon
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class SearchJokeFragment : Fragment() {

    private var _binding:FragmentSearchJokeBinding?=null
    private val binding  get() = _binding!!

    private val adapter  = SearchAdapter{data,v->onClick(data,v)}

    private fun onClick(data: JokesEntity,view: View) {
        view.hapticFeedback()
        viewModel.onTriggerEvent(JokeEvent.SaveFavouriteJokes(data))

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.collect{
                           Timber.i("${it.isSaved}")
                            view.toggleIcon(it.isSaved)
                    }
            }
        }

    }

    private val viewModel by viewModels<JokesVM>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchJokeBinding.inflate(layoutInflater,container,false)
        subscribeObserver()
        setUpRecyclerview()
        return binding.root
    }

    private fun setUpRecyclerview() {
      binding.searchList.layoutManager= LinearLayoutManager(context,VERTICAL,false)

        binding.searchButton.setOnClickListener {
            val query = binding.searchField.text.toString()
            viewModel.onTriggerEvent(JokeEvent.SearchJokes(query))
        }
    }

    private fun subscribeObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.collect{
                    Timber.i("${it.isSaved}")
                    adapter.submitList(it.jokeData)
                    binding.searchList.adapter=adapter

                    it.errorMessage?.let { e->
                        context?.showError(e){viewModel.errorShown()}
                    }

                }
            }
        }
    }

}