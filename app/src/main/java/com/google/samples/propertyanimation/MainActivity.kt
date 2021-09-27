/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.propertyanimation

import android.animation.*
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import com.google.samples.propertyanimation.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        insertListeners()
    }

    private fun insertListeners() {

        binding.rotateButton.setOnClickListener {
            rotate()
        }

        binding.translateButton.setOnClickListener {
            translate()
        }


        binding.scaleButton.setOnClickListener {
            scale()
        }

        binding.fadeButton.setOnClickListener {
            fade()
        }

        binding.colorizeButton.setOnClickListener {
            colorize()
        }

        binding.showerButton.setOnClickListener {
            shower()
        }
    }

    /**
     * Extension function to disable and enable view while animation is running to prevent jank
     */
    private fun ObjectAnimator.disableViewDuringAnimation(view: View) {
        this.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                view.isEnabled = false
            }

            override fun onAnimationEnd(animation: Animator?) {
                view.isEnabled = true
            }
        })
    }

    private fun rotate() {
        // Animation should end with the view being at its default state, hence starting at -360
        val animator = ObjectAnimator.ofFloat(binding.star, View.ROTATION, -360f, 0f)
        animator.duration = 2000 // default is 300ms
        animator.disableViewDuringAnimation(binding.rotateButton)
        animator.start()
    }

    /**
     * Moves the star horizontally
     */
    private fun translate() {
        val animator = ObjectAnimator.ofFloat(binding.star, View.TRANSLATION_X, 200f)
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.disableViewDuringAnimation(binding.translateButton)
        animator.start()
    }

    /**
     * Must animate X and Y properties at the same time avoid skewing with PropertyValuesHolder
     */
    private fun scale() {
        // Default value is 1, so we are 4x'ing the star size
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 4f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 4f)

        val animator = ObjectAnimator.ofPropertyValuesHolder(binding.star, scaleX, scaleY)
        // Reverse the animation so it ends up back where it began
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE

        animator.disableViewDuringAnimation(binding.scaleButton)
        animator.start()
    }

    /**
     * Utilize alpha to define fading effect
     * 0 = transparent / 1 = opaque
     */
    private fun fade() {
        val animator = ObjectAnimator.ofFloat(binding.star, View.ALPHA, 0f)
        animator.duration = 1000
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.disableViewDuringAnimation(binding.fadeButton)
        animator.start()
    }

    @SuppressLint("ObjectAnimatorBinding")
    private fun colorize() {
        // Target the FrameLayout that contains the star image view backgroundColor property name
        val animator =
            ObjectAnimator.ofArgb(binding.star.parent, "backgroundColor", Color.BLACK, Color.RED)
        animator.duration = 1000
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.disableViewDuringAnimation(binding.colorizeButton)
        animator.start()
    }

    /**
     * Create a new star view in a random X position above the container.
     * Make it rotateButton about its center as it falls to the bottom.
     */
    private fun shower() {
        // Local variables we'll need in the code below
        val container = binding.star.parent as ViewGroup // is where we'll add the stars
        val containerW = container.width
        val containerH = container.height
        var starW: Float = binding.star.width.toFloat()
        var starH: Float = binding.star.height.toFloat()

        // Create the new star (an ImageView holding our drawable) and add it to the container
        val newStar = AppCompatImageView(this)
        newStar.setImageResource(R.drawable.ic_star)
        newStar.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        container.addView(newStar)

        // Scale the view randomly between 10-160% of its default size
        newStar.scaleX = Math.random().toFloat() * 1.5f + .1f
        newStar.scaleY = newStar.scaleX
        starW *= newStar.scaleX
        starH *= newStar.scaleY

        // Position the view at a random place between the left and right edges of the container
        newStar.translationX = Math.random().toFloat() * containerW - starW / 2

        // Create an animator that moves the view from a starting position right about the container
        // to an ending position right below the container. Set an accelerate interpolator to give
        // it a gravity/falling feel. This is the falling animation
        val mover = ObjectAnimator.ofFloat(newStar, View.TRANSLATION_Y, -starH, containerH + starH)
        mover.interpolator = AccelerateInterpolator(1f)

        // Create an animator to rotateButton the view around its center up to three times
        val rotator = ObjectAnimator.ofFloat(
            newStar, View.ROTATION,
            (Math.random() * 1080).toFloat()
        )
        rotator.interpolator = LinearInterpolator()

        // Use an AnimatorSet to play the falling and rotating animators in parallel for a duration
        // of a half-second to two seconds
        val set = AnimatorSet()
        set.playTogether(mover, rotator)
        set.duration = (Math.random() * 1500 + 500).toLong()

        // When the animation is done, remove the created view from the container
        set.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                container.removeView(newStar)
            }
        })

        // Start the animation
        set.start()
    }
}
