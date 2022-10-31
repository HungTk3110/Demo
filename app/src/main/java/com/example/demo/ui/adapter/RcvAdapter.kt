package com.example.demo.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.demo.R
import com.example.demo.data.data.model.CharacterData

class RcvAdapter() : RecyclerView.Adapter<RcvAdapter.Adapterholder>() {

    private var list : MutableList<CharacterData> = mutableListOf()

    fun setList(list: MutableList<CharacterData>) {
        this.list = list
        notifyDataSetChanged()

    }

    inner class Adapterholder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvDesc: TextView = view.findViewById(R.id.tvDesc)

        fun bind(data: CharacterData) {
            tvName.text = data.name
            tvDesc.text = data.species

            Glide.with(imageView)
                .load(data.image)
                .circleCrop()
                .into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapterholder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_row, parent, false)
        return Adapterholder(inflater)
    }

    override fun onBindViewHolder(holder: Adapterholder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}