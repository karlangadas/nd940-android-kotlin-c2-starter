package com.udacity.asteroidradar.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import kotlinx.android.synthetic.main.asteroid_item_view.view.close_approach_date
import kotlinx.android.synthetic.main.asteroid_item_view.view.codename
import kotlinx.android.synthetic.main.asteroid_item_view.view.potentially_hazardous

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
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.asteroid_item_view, parent, false) as ConstraintLayout
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.apply {
            codename.text = item.codename
            closeApproachDate.text = item.closeApproachDate
            pottentiallyHazardous.setImageResource(if (item.isPotentiallyHazardous) R.drawable.ic_status_potentially_hazardous else R.drawable.ic_status_normal)
        }
    }

    override fun getItemCount() = data.size

    class ViewHolder(itemView: ConstraintLayout): RecyclerView.ViewHolder(itemView){
        val codename: TextView = itemView.findViewById(R.id.codename)
        val closeApproachDate: TextView = itemView.findViewById(R.id.close_approach_date)
        val pottentiallyHazardous: ImageView = itemView.findViewById(R.id.potentially_hazardous)
    }
}