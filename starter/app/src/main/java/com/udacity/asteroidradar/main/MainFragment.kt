package com.udacity.asteroidradar.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        val adapter = MainAdapter()
        binding.asteroidRecycler.adapter = adapter
        val data = listOf(
            Asteroid(1, "codename1", "2020-02-08", 0.0, 0.0, 0.0, 0.0, false),
            Asteroid(2, "codename2", "2020-02-01", 0.0, 0.0, 0.0, 0.0, true),
            Asteroid(3, "codename3", "2020-02-07", 0.0, 0.0, 0.0, 0.0, false),
            Asteroid(4, "codename4", "2020-02-03", 0.0, 0.0, 0.0, 0.0, true),
            Asteroid(5, "codename5", "2020-02-04", 0.0, 0.0, 0.0, 0.0, true),
            Asteroid(6, "codename6", "2020-02-05", 0.0, 0.0, 0.0, 0.0, false),
        )
        adapter.submitList(data)

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}
