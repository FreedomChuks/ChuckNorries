package com.example.chucknorries.ui.viewState

import android.content.Context
import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.example.chucknorries.R
import com.example.chucknorries.databinding.JokeLayoutDialogBinding
import com.example.chucknorries.domain.entities.JokesEntity
import com.example.chucknorries.domain.utils.ProgressBarState
import com.example.chucknorries.domain.utils.UIComponent
import com.google.android.material.card.MaterialCardView
import timber.log.Timber

fun LottieAnimationView.animateImage(isLoading:ProgressBarState){
    when(isLoading){
        is ProgressBarState.Loading->{
            Timber.i("progress Loading")
            this.repeatMode = LottieDrawable.INFINITE
            this.animate()
            this.playAnimation()
        }
        is ProgressBarState.Idle->{
            Timber.i("progress Idle")
            this.pauseAnimation()
        }
    }
}


fun MaterialCardView.showJokes(data:List<JokesEntity>, text:TextView, button:Button,onClick:()->Unit){
    if (data.isNotEmpty()) {
        text.text = data[0].value
        this.visibility = VISIBLE
    }
    else {
        this.visibility= INVISIBLE
    }
    button.setOnClickListener {
        this.visibility= INVISIBLE
        onClick()
    }

}

fun View.hapticFeedback() {
    this.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
}


/** @param message:Boolean
 * @param onClick:Boolean
 * extention function of material Dialog to showError Log
 *
 *
 */
fun Context.showError(uiComponent: UIComponent,onClick:()->Unit){
    if (uiComponent is UIComponent.Dialog){
        MaterialDialog(this).show {
            title(text = uiComponent.title)
            message(text = uiComponent.description)
            negativeButton(text = "close") {
                dismiss()
                onClick()
            }
        }
    }
}

/** @param isSaved:Boolean
 * @throws TypeCastException
 * @author Freedom Chuks
 *
 * takes in a boolean and updated View based on truthy
 *
 */
fun View.toggleIcon(isSaved:Boolean){
    if (this is ImageView){
        when(isSaved) {
            true-> this.setBackgroundResource(R.drawable.ic_favorite_filled)
            false-> this.setBackgroundResource(R.drawable.ic_favorite_border)
        }
    }else{
        Timber.i("error")
        Throwable(TypeCastException("View Must be of type ImageView"))
    }
}


fun Context.showJokeDialog(jokes:String?,onClick: () -> Unit){
    jokes?.let {
        MaterialDialog(this).show {
            val binding = JokeLayoutDialogBinding.inflate(LayoutInflater.from(context),view,true)
            customView(view = binding.root)
            binding.jokeText.text=jokes
            binding.okBtn.setOnClickListener {
                dismiss()
                onClick()
            }
            cornerRadius(8F)
        }
    }
}


