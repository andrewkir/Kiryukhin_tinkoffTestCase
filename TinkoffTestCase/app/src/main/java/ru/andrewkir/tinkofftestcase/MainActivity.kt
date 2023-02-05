package ru.andrewkir.tinkofftestcase

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import ru.andrewkir.tinkofftestcase.common.FragmentPlaceholder
import ru.andrewkir.tinkofftestcase.flows.main.PopularFragmentDirections


class MainActivity : AppCompatActivity() {
    var flag = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val popularButton: Button = findViewById(R.id.popularButton)
        val favouritesButton: Button = findViewById(R.id.favouritesButton)

        popularButton.setOnClickListener {
            findNavController(R.id.fragmentContainerView2).navigate(R.id.action_favouritesFragment_to_mainFragment)
            favouritesButton.isClickable = true
            popularButton.isClickable = false
        }

        favouritesButton.setOnClickListener {
            findNavController(R.id.fragmentContainerView2).navigate(R.id.action_mainFragment_to_favouritesFragment)
            favouritesButton.isClickable = false
            popularButton.isClickable = true
        }

        popularButton.isClickable = false
    }

    fun hideButtons() {
        val button1 = findViewById<Button>(R.id.popularButton)
        button1.visibility = View.GONE
        val button2 = findViewById<Button>(R.id.favouritesButton)
        button2.visibility = View.GONE
    }

    fun showButtons() {
        val button1 = findViewById<Button>(R.id.popularButton)
        button1.visibility = View.VISIBLE
        val button2 = findViewById<Button>(R.id.favouritesButton)
        button2.visibility = View.VISIBLE
    }

    fun navInLandscape(filmId: Int) {
        val navHostFragment = Navigation.findNavController(this, R.id.fragmentContainerView3)
        val bundle = Bundle()
        bundle.putInt("filmID", filmId)
        if (flag) {
            flag = false
            navHostFragment.navigate(
                R.id.action_fragmentPlaceholder_to_detailsFragment,
                bundle
            )
        } else {
            navHostFragment.navigate(
                R.id.action_detailsFragment_self,
                bundle
            )
        }
    }

    fun navBackInLandscape() {
        val navHostFragment = Navigation.findNavController(this, R.id.fragmentContainerView3)
        navHostFragment.navigate(R.id.action_detailsFragment_to_fragmentPlaceholder)
        showButtons()
    }
}