package com.example.chucknorries.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.example.chucknorries.ui.viewState.JokeEvent
import com.example.chucknorries.ui.viewState.hapticFeedback
import com.example.chucknorries.ui.viewState.showError
import com.example.chucknorries.ui.viewState.toggleIcon
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class SearchJokeFragment : Fragment() {

    private var _binding:FragmentSearchJokeBinding?=null
    private val binding  get() = _binding!!

    private val adapter  = SearchAdapter{data,v->onClick(data,v)}


    private val viewModel by viewModels<JokesVM>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchJokeBinding.inflate(layoutInflater,container,false)
        subscribeObserver()
        setUpUi()
        return binding.root
    }

    private fun setUpUi() {
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
                    adapter.submitList(it.jokeData)
                    binding.searchList.adapter=adapter
                    if (it.isSaved){
                        Toast.makeText(context,"Joke has been added to favourite",Toast.LENGTH_SHORT).show()
                        Snackbar.make(binding.root,"Joke Saved",Snackbar.LENGTH_SHORT).show()
                    }
                    it.errorMessage?.let { e->
                        context?.showError(e){viewModel.errorShown()}
                    }

                }
            }
        }
    }

    private fun onClick(data: JokesEntity,view: View) {
        view.hapticFeedback()
        viewModel.onTriggerEvent(JokeEvent.SaveFavouriteJokes(data))
    }

}