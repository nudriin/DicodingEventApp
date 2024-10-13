package com.nudriin.dicodingeventapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nudriin.dicodingeventapp.data.response.ListEventsItem
import com.nudriin.dicodingeventapp.databinding.EventCardBinding

class EventListAdapter: ListAdapter<ListEventsItem, EventListAdapter.ViewHolder>(DIFF_CALLBACK) {

    interface OnItemClickCallback {
        fun onItemClicked(eventId: String)
    }

    private lateinit var onItemClickCallback: OnItemClickCallback
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListEventsItem>() {
            override fun areItemsTheSame(
                oldItem: ListEventsItem,
                newItem: ListEventsItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ListEventsItem,
                newItem: ListEventsItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
    class ViewHolder(val binding: EventCardBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(event: ListEventsItem) {
            Glide.with(binding.root.context)
                .load(event.mediaCover)
                .into(binding.eventImg)

            binding.tvTitle.text = event.name
            binding.tvAuthor.text = event.ownerName
            binding.tvSummary.text = event.summary
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventListAdapter.ViewHolder {
        val binding = EventCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventListAdapter.ViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(event.id.toString())
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}