package com.example.chucknorries.ui.uIState

import android.view.View.*
import android.widget.Button
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.example.chucknorries.domain.entities.JokesEntity
import com.example.chucknorries.ui.JokesVM
import com.google.android.material.card.MaterialCardView

fun LottieAnimationView.animateImage(isLoading:Boolean){
    when(isLoading){
        true->{
            this.repeatMode = LottieDrawable.INFINITE
            this.animate()
            this.playAnimation()
        }
        false->{
            this.pauseAnimation()
        }
    }
}


fun MaterialCardView.showJokes(data:List<JokesEntity>, text:TextView, button:Button, viewModel:JokesVM){
    if (data.isNotEmpty()) {
        text.text = data[0].value
        this.visibility = VISIBLE
    }
    else {
        this.visibility= INVISIBLE
    }
    button.setOnClickListener {
        this.visibility= INVISIBLE
        viewModel.jokeShown()
    }

}