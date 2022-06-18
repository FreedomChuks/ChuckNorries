package com.example.chucknorries.ui.uIState

import android.content.Context
import android.view.HapticFeedbackConstants
import android.view.View
import android.view.View.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.example.chucknorries.R
import com.example.chucknorries.domain.entities.JokesEntity
import com.example.chucknorries.ui.JokesVM
import com.google.android.material.card.MaterialCardView
import timber.log.Timber
import java.lang.Exception

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
fun Context.showError(message:String,onClick:()->Unit){
    MaterialDialog(this).show {
        title(text = "Error Occurred")
        message(text = message)
        negativeButton(text = "close") {
            dismiss()
            onClick()
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