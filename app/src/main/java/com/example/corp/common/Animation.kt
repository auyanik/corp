package com.example.corp.common

import android.view.View
import android.view.animation.Animation

const val ANIMATION_DURATION_MEDIUM: Long = 400
const val ANIMATION_DURATION_LONG: Long = 800

inline fun Animation.clearAfter(view: View) {
    this.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationRepeat(animation: Animation?) {}
        override fun onAnimationEnd(animation: Animation?) {
            view.clearAnimation()
        }

        override fun onAnimationStart(animation: Animation?) {}
    })
}

fun Animation.onAnimationEnd(predicate: (animation: Animation?) -> Unit) {
    this.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationRepeat(animation: Animation?) {}
        override fun onAnimationEnd(animation: Animation?) {
            predicate.invoke(animation)
        }

        override fun onAnimationStart(animation: Animation?) {}
    })
}
