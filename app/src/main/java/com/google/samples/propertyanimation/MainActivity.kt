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

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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

        /**
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
         */
    }

    private fun rotate() {
        // Animation should end with the view being at its default state, hence starting at -360
        val animator = ObjectAnimator.ofFloat(binding.star, View.ROTATION, -360f, 0f)
        animator.duration = 2000 // default is 300ms

        // Listener to disable and enable button while animation is running to prevent jank
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                binding.rotateButton.isEnabled = false
            }
            override fun onAnimationEnd(animation: Animator?) {
                binding.rotateButton.isEnabled = true
            }
        })

        animator.start()
    }

    private fun translate() {
    }

    private fun scale() {
    }

    private fun fade() {
    }

    private fun colorize() {
    }

    private fun shower() {
    }
}
