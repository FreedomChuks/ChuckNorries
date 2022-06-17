package com.example.chucknorries.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.chucknorries.databinding.SearchLayoutBinding
import com.example.chucknorries.domain.entities.JokesEntity

class SearchAdapter(private val onclick:(data: JokesEntity)->Unit):ListAdapter<JokesEntity, SearchAdapter.SearchVH>(searchDiff) {

    inner class SearchVH(private val binding:SearchLayoutBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(data: JokesEntity){
            binding.jokeContent.text  = data.value
            binding.jokeContent.text = data.categories[0]
            binding.root.setOnClickListener {
              onclick(data)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchVH {
        return SearchVH(SearchLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: SearchVH, position: Int) {
        val data  = getItem(position)
        holder.bind(data)
    }

    companion object{
        val searchDiff = object : DiffUtil.ItemCallback<JokesEntity>(){
            override fun areItemsTheSame(oldItem: JokesEntity, newItem: JokesEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: JokesEntity, newItem: JokesEntity): Boolean {
                return oldItem.id ==newItem.id
            }

        }
    }
}