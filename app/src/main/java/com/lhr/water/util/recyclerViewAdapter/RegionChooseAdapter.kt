package com.lhr.water.util.recyclerViewAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lhr.water.databinding.ItemChooseRegionBinding

class RegionChooseAdapter(val listener: Listener): ListAdapter<String, RegionChooseAdapter.ViewHolder>(LOCK_DIFF_UTIL) {

    companion object{
        val LOCK_DIFF_UTIL = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: String,
                newItem: String
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    }


    interface Listener{
        fun onItemClick(item: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemChooseRegionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemChooseRegionBinding): RecyclerView.ViewHolder(binding.root){

        init {
            // bindingAdapterPosition無法使用，所以用adapterPosition替代
            binding.root.setOnClickListener {
                listener.onItemClick(getItem(adapterPosition))
            }
        }

        fun bind(regionName: String){
            binding.textRegionName.text = regionName
        }
    }
}