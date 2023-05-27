package com.udacity.asteroidradar.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.TextItemViewHolder

class MainAdapter : RecyclerView.Adapter<TextItemViewHolder>() {
    private var data = listOf(
        Asteroid(1, "codename1", "", 0.0, 0.0, 0.0, 0.0, false),
        Asteroid(2, "codename2", "", 0.0, 0.0, 0.0, 0.0, false),
        Asteroid(3, "codename3", "", 0.0, 0.0, 0.0, 0.0, false),
        Asteroid(4, "codename4", "", 0.0, 0.0, 0.0, 0.0, false),
        Asteroid(5, "codename5", "", 0.0, 0.0, 0.0, 0.0, false),
        Asteroid(6, "codename6", "", 0.0, 0.0, 0.0, 0.0, false),
    )
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.asteroid_item_view, parent, false) as TextView
        return TextItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        val item = data[position]
        holder.textView.text = item.codename
    }

    override fun getItemCount() = data.size
}