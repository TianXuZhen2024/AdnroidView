package com.xuzhen.androidview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xuzhen.androidview.databinding.ItemTestBinding
import com.xuzhen.library_base.base.BaseAdapter

/**
 * @version 1.0
 * @date 2024/9/30 15:36
 */
class TestAdapter(items: MutableList<String>) : BaseAdapter<String>(items) {

    override fun onCreateVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemTestBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TestViewHolder(binding)
    }

    override fun onBindVHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TestViewHolder) {
            holder.bind(getItemData(position))
        }
    }

    inner class TestViewHolder(private val binding: ItemTestBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.time.text = item
        }
    }
}