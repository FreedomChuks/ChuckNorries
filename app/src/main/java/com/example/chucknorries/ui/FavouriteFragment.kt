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
import com.example.chucknorries.databinding.FragmentFavouriteBinding
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
class FavouriteFragment : Fragment() {

    private var _binding: FragmentFavouriteBinding?=null
    private val binding  get() = _binding!!

    private val viewModel by viewModels<JokesVM>()

    private val adapter  = SearchAdapter{data,v->onClick(data,v)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouriteBinding.inflate(inflater,container,false)
        setUpUI()
        subscribeObserver()
        return binding.root
    }

    private fun subscribeObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.collect{
                    Timber.i("data ${it.jokeData}")
                    adapter.submitList(it.jokeData)
                    binding.favList.adapter=adapter

                    it.errorMessage?.let { e->
                        context?.showError(e){viewModel.errorShown()}
                    }

                }
            }
        }
        viewModel.onTriggerEvent(JokeEvent.FetchFavouriteJokes)
    }
    private fun setUpUI() {
        binding.favList.layoutManager= LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
    }

    private fun onClick(data: JokesEntity, view: View) {
        view.hapticFeedback()
        viewModel.onTriggerEvent(JokeEvent.SaveFavouriteJokes(data))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}