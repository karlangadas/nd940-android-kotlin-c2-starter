package com.udacity.asteroidradar.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.AsteroidItemViewBinding

class MainAdapter : RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    private var data = listOf(
        Asteroid(1, "codename1", "2020-02-08", 0.0, 0.0, 0.0, 0.0, false),
        Asteroid(2, "codename2", "2020-02-01", 0.0, 0.0, 0.0, 0.0, true),
        Asteroid(3, "codename3", "2020-02-07", 0.0, 0.0, 0.0, 0.0, false),
        Asteroid(4, "codename4", "2020-02-03", 0.0, 0.0, 0.0, 0.0, true),
        Asteroid(5, "codename5", "2020-02-04", 0.0, 0.0, 0.0, 0.0, true),
        Asteroid(6, "codename6", "2020-02-05", 0.0, 0.0, 0.0, 0.0, false),
    )
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun getItemCount() = data.size

    class ViewHolder private constructor(private val binding: AsteroidItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Asteroid) {
            binding.asteroid = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AsteroidItemViewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}