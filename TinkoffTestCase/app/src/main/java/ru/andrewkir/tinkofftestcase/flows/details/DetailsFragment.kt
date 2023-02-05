package ru.andrewkir.tinkofftestcase.flows.details

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import ru.andrewkir.tinkofftestcase.App
import ru.andrewkir.tinkofftestcase.MainActivity
import ru.andrewkir.tinkofftestcase.R
import ru.andrewkir.tinkofftestcase.common.BaseFragment
import ru.andrewkir.tinkofftestcase.common.ViewModelFactory
import ru.andrewkir.tinkofftestcase.databinding.FragmentDetailsBinding
import javax.inject.Inject

class DetailsFragment : BaseFragment<DetailsViewModel, FragmentDetailsBinding>() {

    val args: DetailsFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun provideViewModel(): DetailsViewModel {
        (requireContext().applicationContext as App).appComponent.inject(this)
        return ViewModelProvider(this, viewModelFactory)[DetailsViewModel::class.java]
    }

    override fun provideBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailsBinding = FragmentDetailsBinding.inflate(inflater, container, false)

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).hideButtons()

        viewModel.getMovie(args.filmID).let {
            Glide
                .with(requireContext())
                .load(it.posterUrl)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(bind.imageView2)

            bind.name.text = it.name
            bind.description.text = it.description
            bind.genres.text =
                "Жанры: ${it.genres?.joinToString(separator = ", ") { genre -> genre }}"
            bind.Countries.text =
                "Жанры: ${it.countries?.joinToString(separator = ", ") { country -> country }}"
        }

        bind.backButton.setOnClickListener {
            if (activity?.resources?.configuration?.orientation == Configuration.ORIENTATION_PORTRAIT) {
                activity?.onBackPressed()
            } else {
                (activity as MainActivity).navBackInLandscape()
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        (activity as MainActivity).showButtons()
    }
}