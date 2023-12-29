package com.lhr.water.util.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lhr.water.R
import com.lhr.water.data.WaitDealGoodsData
import com.lhr.water.databinding.ItemInputBinding
import com.lhr.water.databinding.ItemWaitInputGoodsBinding
import com.lhr.water.room.StorageContentEntity
import com.lhr.water.ui.goods.GoodsViewModel
import com.lhr.water.ui.history.HistoryViewModel
import com.lhr.water.util.manager.jsonStringToJson
import com.lhr.water.util.showToast
import timber.log.Timber

class InputAdapter(
    val context: Context,
    val reportTitle: String,
    val formNumber: String,
    val listener: Listener,
    val viewModel: HistoryViewModel
) :
    ListAdapter<WaitDealGoodsData, InputAdapter.ViewHolder>(LOCK_DIFF_UTIL) {
    companion object {
        val LOCK_DIFF_UTIL = object : DiffUtil.ItemCallback<WaitDealGoodsData>() {
            override fun areItemsTheSame(
                oldItem: WaitDealGoodsData,
                newItem: WaitDealGoodsData
            ): Boolean {
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

    interface Listener {
        fun onItemClick(item: WaitDealGoodsData, maxQuantity: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemInputBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemInputBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(waitDealGoodsData: WaitDealGoodsData) {
            binding.textMaterialName.text =
                waitDealGoodsData.itemInformation["materialName"].toString()
            binding.textMaterialNumber.text =
                waitDealGoodsData.itemInformation["materialNumber"].toString()
            // 需判斷暫存待入庫的貨物列表是否有相對應貨物，有的話需要減去數量
            var quantity = waitDealGoodsData.itemInformation.getInt("receivedQuantity") - viewModel.formRepository.getMaterialQuantityByTempWaitInputGoods(
                reportTitle,
                formNumber,
                waitDealGoodsData.itemInformation["number"].toString()
            )
            binding.textQuantity.text = quantity.toString()
            if(quantity == 0){
                binding.cover.visibility = View.VISIBLE
            }else{
                binding.cover.visibility = View.INVISIBLE
            }

            binding.root.setOnClickListener {

                if(quantity == 0){
                    showToast(context, "已經無貨物")
                }else{
                    listener.onItemClick(getItem(adapterPosition), binding.textQuantity.text.toString())
                }
            }
        }
    }
}