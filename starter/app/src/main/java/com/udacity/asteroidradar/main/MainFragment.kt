package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var adapter: MainAdapter
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        adapter = MainAdapter(MainAdapter.AsteroidClick {
            findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
        }

        )
        binding.asteroidRecycler.adapter = adapter
        viewModel.asteroids.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it)
            }
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val asteroidsToDisplay = when (item.itemId) {
            R.id.show_today_asteroids -> viewModel.getTodayAsteroid()
            R.id.show_week_asteroids -> viewModel.getWeekAsteroid()
            R.id.show_saved_asteroids -> viewModel.asteroids.value
            else -> null
        }
        adapter.submitList(asteroidsToDisplay)
        return true
    }
}
