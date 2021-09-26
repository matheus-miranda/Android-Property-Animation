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

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.samples.propertyanimation.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        insertListeners()
    }

    private fun insertListeners() {
        /**
        binding.apply {

            rotateButton.setOnClickListener {
                rotater()
            }

            translateButton.setOnClickListener {
                translater()
            }

            scaleButton.setOnClickListener {
                scaler()
            }

            fadeButton.setOnClickListener {
                fader()
            }

            colorizeButton.setOnClickListener {
                colorizer()
            }

            showerButton.setOnClickListener {
                shower()
            }
        }
        */
    }

    private fun rotater() {
    }

    private fun translater() {
    }

    private fun scaler() {
    }

    private fun fader() {
    }

    private fun colorizer() {
    }

    private fun shower() {
    }
}