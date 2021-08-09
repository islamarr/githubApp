package com.islam.githubapp.ui.userDetails

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.islam.githubapp.databinding.FragmentUserDetailsBinding
import com.islam.githubapp.generalUtils.Const
import com.islam.githubapp.ui.BaseFragment


class UserDetailsFragment : BaseFragment<FragmentUserDetailsBinding>() {

    private var username: String? = null
    private lateinit var url: String

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentUserDetailsBinding
        get() = FragmentUserDetailsBinding::inflate

    private val callback: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.webView.canGoBack()) {
                    binding.webView.goBack()
                } else {
                    findNavController().popBackStack()
                }
            }
        }

    @SuppressLint("SetJavaScriptEnabled")
    override fun setupOnViewCreated(view: View) {

        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)

        arguments?.let {
            username = it.getString(Const.UserDetailsKey)
            url = "${Const.profileBaseUrl}$username"
        }

        binding.webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                binding.loading.visibility = View.GONE
            }
        }

        val webSettings: WebSettings = binding.webView.settings
        webSettings.javaScriptEnabled = true
        binding.webView.loadUrl(url)

    }

}