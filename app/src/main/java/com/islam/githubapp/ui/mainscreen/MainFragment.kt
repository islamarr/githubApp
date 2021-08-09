package com.islam.githubapp.ui.mainscreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.islam.githubapp.R
import com.islam.githubapp.data.Resource
import com.islam.githubapp.databinding.MainFragmentBinding
import com.islam.githubapp.ui.BaseFragment
import com.islam.githubapp.ui.adapters.MainAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainFragment : BaseFragment<MainFragmentBinding>() {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var mainAdapter: MainAdapter

   // private lateinit var applicableList: MutableList<Applicable>

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> MainFragmentBinding
        get() = MainFragmentBinding::inflate

    override fun setupOnViewCreated(view: View) {

        initRecyclerView()
        startObserver()

        binding.toolbar.backBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }

    }

    private fun startObserver() {

        lifecycleScope.launch {
            viewModel.searchResults.collectLatest {
                mainAdapter.submitData(it)
            }
        }

        mainAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading) {
                binding.listLayout.loadingProgressBar.visibility = View.VISIBLE
            } else {
                binding.listLayout.emptyList.visibility = View.GONE
                binding.listLayout.loadingProgressBar.visibility = View.GONE

                val errorState = when {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                errorState?.let {
                    binding.listLayout.emptyList.visibility = View.VISIBLE
                    binding.listLayout.emptyList.text = getString(R.string.no_internet_connection)
                }
            }
        }

       /* viewModel.methods.observe(viewLifecycleOwner, Observer {
            it?.let { result ->
                when (result) {
                    is Resource.Success -> {

                        binding.listLayout.emptyList.visibility = View.GONE
                        binding.listLayout.loadingProgressBar.visibility = View.GONE

                      //  applicableList = result.data.networks.applicable

                      *//*  if (applicableList.isEmpty()) {
                            binding.listLayout.emptyList.visibility = View.VISIBLE
                            return@Observer
                        }*//*

                        initRecyclerView()

                    }
                    is Resource.Error -> {

                        binding.listLayout.loadingProgressBar.visibility = View.GONE
                        binding.listLayout.emptyList.visibility = View.VISIBLE
                        binding.listLayout.emptyList.text = result.exception

                    }
                    is Resource.Loading -> {

                        binding.listLayout.loadingProgressBar.visibility = View.VISIBLE

                    }
                }
            }
        })*/

    }

    private fun initRecyclerView() {

        binding.listLayout.list.apply {
            mainAdapter = MainAdapter()
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = mainAdapter
        }

    }

}