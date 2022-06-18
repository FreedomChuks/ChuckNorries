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
import com.example.chucknorries.databinding.FragmentRandomBinding
import com.example.chucknorries.ui.uIState.JokeEvent
import com.example.chucknorries.ui.uIState.animateImage
import com.example.chucknorries.ui.uIState.showError
import com.example.chucknorries.ui.uIState.showJokes
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RandomFragment : Fragment() {
    private var _binding:FragmentRandomBinding?=null
    private val binding get() = _binding!!

    private val viewModel by viewModels<JokesVM>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRandomBinding.inflate(layoutInflater,container,false)
        onSubscribeObserver()
        setUpUI()
        return binding.root
    }

    private fun setUpUI() {
        binding.button.setOnClickListener {
            viewModel.onTriggerEvent(JokeEvent.FetchRandomJokes)
        }
    }

    private fun onSubscribeObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.collect{
                    with(binding){
                        animationView.animateImage(it.isLoading)
                        cardDialog.showJokes(it.jokeData,jokeText,okBtn) { viewModel.jokeShown() }
                        it.errorMessage?.let {
                            context?.showError(it){viewModel.errorShown()}
                        }

                    }

                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}