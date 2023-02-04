package ru.andrewkir.tinkofftestcase.flows.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.andrewkir.tinkofftestcase.App
import ru.andrewkir.tinkofftestcase.common.BaseFragment
import ru.andrewkir.tinkofftestcase.common.ViewModelFactory
import ru.andrewkir.tinkofftestcase.databinding.FragmentPopularBinding
import ru.andrewkir.tinkofftestcase.flows.main.adapters.MoviesAdapter
import javax.inject.Inject

class PopularFragment : BaseFragment<PopularViewModel, FragmentPopularBinding>() {

    lateinit var adapter: MoviesAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun provideViewModel(): PopularViewModel {
        (requireContext().applicationContext as App).appComponent.inject(this)
        return ViewModelProvider(this, viewModelFactory)[PopularViewModel::class.java]
    }

    override fun provideBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPopularBinding = FragmentPopularBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MoviesAdapter() {
            viewModel.updateItem(it)
        }

        bind.recyclerView.adapter = adapter
        bind.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        subscribeToList()
    }

    private fun subscribeToList() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getData()?.collectLatest { movies ->
                adapter.submitData(movies)
            }
        }
    }
}