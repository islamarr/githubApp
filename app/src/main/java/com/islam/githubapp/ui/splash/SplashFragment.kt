package com.islam.githubapp.ui.splash

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.islam.githubapp.R
import com.islam.githubapp.databinding.FragmentSplashBinding
import com.islam.githubapp.ui.BaseFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSplashBinding
        get() = FragmentSplashBinding::inflate

    override fun setupOnViewCreated(view: View) {
        startApp()
    }

    private fun startApp() {

        lifecycleScope.launch {

            delay(1500)

            findNavController().navigate(R.id.action_splash_to_mainFragment)

        }

    }

}