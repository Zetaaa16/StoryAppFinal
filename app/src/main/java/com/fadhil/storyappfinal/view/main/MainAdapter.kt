package com.fadhil.storyappfinal.view.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fadhil.storyappfinal.data.GetStoryResult
import com.fadhil.storyappfinal.databinding.ItemRowStoryBinding


class MainAdapter :
    PagingDataAdapter<GetStoryResult, MainAdapter.MyViewHolder>(DIFF_CALLBACK) {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
            holder.itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRowStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    class MyViewHolder(private val binding: ItemRowStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: GetStoryResult) {
            Glide.with(binding.root.context)
                .load(data.photoUrl)
                .into(binding.imageview)

            binding.textView.text = data.name
            binding.textDate.text = data.createdAt
            binding.textdescription.text = data.description
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(story:GetStoryResult)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GetStoryResult>() {
            override fun areItemsTheSame(oldItem: GetStoryResult, newItem: GetStoryResult): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: GetStoryResult, newItem: GetStoryResult): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}