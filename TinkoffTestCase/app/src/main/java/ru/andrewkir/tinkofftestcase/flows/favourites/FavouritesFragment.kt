package ru.andrewkir.tinkofftestcase.flows.favourites

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import ru.andrewkir.tinkofftestcase.App
import ru.andrewkir.tinkofftestcase.MainActivity
import ru.andrewkir.tinkofftestcase.common.BaseFragment
import ru.andrewkir.tinkofftestcase.common.ViewModelFactory
import ru.andrewkir.tinkofftestcase.databinding.FragmentPopularBinding
import ru.andrewkir.tinkofftestcase.flows.favourites.adapters.FavouritesMoviesAdapter
import ru.andrewkir.tinkofftestcase.flows.main.PopularFragmentDirections
import javax.inject.Inject

class FavouritesFragment : BaseFragment<FavouritesViewModel, FragmentPopularBinding>() {

    lateinit var adapter: FavouritesMoviesAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun provideViewModel(): FavouritesViewModel {
        (requireContext().applicationContext as App).appComponent.inject(this)
        return ViewModelProvider(this, viewModelFactory)[FavouritesViewModel::class.java]
    }

    override fun provideBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPopularBinding = FragmentPopularBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val favouritesList = viewModel.getData()

        (activity as MainActivity).flag = true

        adapter = FavouritesMoviesAdapter({
            val action =
                FavouritesFragmentDirections.actionFavouritesFragmentToDetailsFragment(it?.id ?: 0)
            if (activity?.resources?.configuration?.orientation == Configuration.ORIENTATION_PORTRAIT) {
                findNavController().navigate(action)
            } else {
                (activity as MainActivity).navInLandscape(it?.id ?: 0)
            }
        }, {
            if (favouritesList.find { movieModel -> movieModel.id == it?.id } != null) {
                viewModel.removeItem(it)
                getList()
            }
        })
        adapter.favouritesList = favouritesList

        bind.recyclerView.adapter = adapter
        bind.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        bind.textView.text = "Избранное"

        getList()
    }

    private fun getList() {
        viewLifecycleOwner.lifecycleScope.launch {
            adapter.dataSet = viewModel.getData()
            bind.progressBar.visibility = View.GONE
        }
    }
}