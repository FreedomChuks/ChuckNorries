package com.example.chucknorries.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.chucknorries.databinding.CategoryLayoutBinding
import com.example.chucknorries.databinding.SearchLayoutBinding
import com.example.chucknorries.domain.entities.JokesEntity

class CategoryAdapter(private val onclick:(data: String)->Unit):ListAdapter<String, CategoryAdapter.SearchVH>(searchDiff) {

    inner class SearchVH(private val binding:CategoryLayoutBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(data: String){
            binding.jokeCategoryName.text  = data
            binding.root.setOnClickListener {
                onclick(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchVH {
        return SearchVH(CategoryLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: SearchVH, position: Int) {
        val data  = getItem(position)
        holder.bind(data)
    }

    companion object{
        val searchDiff = object : DiffUtil.ItemCallback<String>(){
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem ==newItem
            }

        }
    }
}