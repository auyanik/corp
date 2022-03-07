package com.example.corp.ui.loader

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import androidx.core.os.postDelayed
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.example.corp.R
import com.example.corp.common.ANIMATION_DURATION_LONG
import com.example.corp.common.ANIMATION_DURATION_MEDIUM
import com.example.corp.common.onAnimationEnd
import com.example.corp.databinding.DialogLoadingBinding
import javax.inject.Inject

class LoadingDialog @Inject constructor(context: Context) : Dialog(context) {

    private val binding = DialogLoadingBinding.inflate(layoutInflater)

    private val spinAnimation: AnimationSet by lazy {
        AnimationUtils.loadAnimation(
            getContext(), R.anim.rotate
        ) as AnimationSet
    }
    private val fadeInAnimation: Animation by lazy {
        AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in)
            .apply { duration = ANIMATION_DURATION_LONG }
    }
    private val fadeOutAnimation: Animation by lazy {
        AnimationUtils.loadAnimation(
            getContext(), android.R.anim.fade_out
        )
            .apply { duration = ANIMATION_DURATION_MEDIUM }
    }
    private val iconList = listOf(R.mipmap.ic_launcher)

    private var isActive: Boolean = false

    init {
        window?.let {
            it.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            it.setWindowAnimations(R.style.Widget_LoadingDialogAnimation)
        }
        setCanceledOnTouchOutside(false)
        setCancelable(false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_loading)
    }

    override fun onBackPressed() {
        dismiss()
        ownerActivity?.onBackPressed()
        super.onBackPressed()
    }

    override fun dismiss() {
        isActive = false
        super.dismiss()
    }

    override fun show() {
        super.show()
        isActive = true
        startAnimation()
    }

    private fun startAnimation() = with(binding) {
        var index = 0
        ivSpin.startAnimation(spinAnimation)
        ivIcon.setImageResource(iconList[index])
        ivIcon.startAnimation(fadeInAnimation)

        fadeInAnimation.onAnimationEnd {
            if (!isShowing || !isActive) return@onAnimationEnd
            ivIcon.isVisible = true
            Handler().postDelayed(
                context.resources.getInteger(R.integer.loading_dialog_delay)
                    .toLong()
            ) {
                if (isActive) ivIcon.startAnimation(fadeOutAnimation)
            }
        }
        fadeOutAnimation.onAnimationEnd {
            if (!isShowing || !isActive) return@onAnimationEnd
            index++
            index %= iconList.size
            ivIcon.isInvisible = true
            ivIcon.setImageResource(iconList[index])
            Handler().postDelayed(0) {
                if (isActive) ivIcon.startAnimation(fadeInAnimation)
            }
        }

    }
}
