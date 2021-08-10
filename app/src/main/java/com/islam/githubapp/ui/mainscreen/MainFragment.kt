package com.islam.githubapp.ui.mainscreen

import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.islam.githubapp.R
import com.islam.githubapp.databinding.MainFragmentBinding
import com.islam.githubapp.ui.BaseFragment
import com.islam.githubapp.ui.adapters.MainAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "MainFragment"
@AndroidEntryPoint
class MainFragment : BaseFragment<MainFragmentBinding>() {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var mainAdapter: MainAdapter

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> MainFragmentBinding
        get() = MainFragmentBinding::inflate

    override fun setupOnViewCreated(view: View) {

        setHasOptionsMenu(true)
        initRecyclerView()
        startObserver()

    }

    private fun startObserver() {

        mainAdapter.addLoadStateListener { loadState ->

            val isEmptyList = loadState.refresh is LoadState.NotLoading && mainAdapter.itemCount == 0

            if (isEmptyList) {
                binding.starterImage.visibility = View.VISIBLE
                binding.listLayout.list.visibility = View.GONE
                return@addLoadStateListener
            }

            if (loadState.refresh is LoadState.Loading) {
                binding.starterImage.visibility = View.GONE
                binding.listLayout.list.visibility = View.VISIBLE
                binding.listLayout.emptyList.visibility = View.GONE
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
                    binding.listLayout.list.visibility = View.GONE
                    binding.listLayout.emptyList.visibility = View.VISIBLE
                    binding.listLayout.emptyList.text = getString(R.string.no_internet_connection)
                }
            }
        }

    }

    private fun initRecyclerView() {

        binding.listLayout.list.apply {
            mainAdapter = MainAdapter()
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = mainAdapter
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.search_bar, menu)
        val searchView = menu.findItem(R.id.search)?.actionView as SearchView
        searchView.queryHint = resources.getString(R.string.search_hint)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(searchText: String?): Boolean {

                lifecycleScope.launch {
                    viewModel.searchResults(searchText!!).collectLatest {
                        mainAdapter.submitData(it)
                    }
                }

                return false
            }

        })

    }
}