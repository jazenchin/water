package com.lhr.water.util.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lhr.water.data.WaitDealGoodsData
import com.lhr.water.databinding.ItemWaitInpuptGoodsBinding

class WaitOutputGoodsAdapter(val listener: Listener): ListAdapter<WaitDealGoodsData, WaitOutputGoodsAdapter.ViewHolder>(LOCK_DIFF_UTIL) {
    companion object{
        val LOCK_DIFF_UTIL = object : DiffUtil.ItemCallback<WaitDealGoodsData>() {
            override fun areItemsTheSame(oldItem: WaitDealGoodsData, newItem: WaitDealGoodsData): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: WaitDealGoodsData,
                newItem: WaitDealGoodsData
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    }


    interface Listener{
        fun onItemClick(item: WaitDealGoodsData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemWaitInpuptGoodsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))

    }

    inner class ViewHolder(private val binding: ItemWaitInpuptGoodsBinding): RecyclerView.ViewHolder(binding.root){

        init {
            // bindingAdapterPosition無法使用，所以用adapterPosition替代
            binding.root.setOnClickListener {
                listener.onItemClick(getItem(adapterPosition))
            }
        }

        fun bind(waitDealGoodsData: WaitDealGoodsData){
            binding.textFormName.text = waitDealGoodsData.reportTitle
            binding.textFormNumber.text = waitDealGoodsData.formNumber
            binding.textMaterialName.text = waitDealGoodsData.itemInformation["materialName"].toString()
            binding.textMaterialNumber.text = waitDealGoodsData.itemInformation["materialNumber"].toString()
        }
    }
}