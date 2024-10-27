package com.nudriin.dicodingeventapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nudriin.dicodingeventapp.data.local.entity.EventEntity
import com.nudriin.dicodingeventapp.databinding.EventCardBinding

class FavoriteListAdapter: ListAdapter<EventEntity, FavoriteListAdapter.ViewHolder>(DIFF_CALLBACK) {

    interface OnItemClickCallback {
        fun onItemClicked(eventId: String)
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<EventEntity>() {
            override fun areItemsTheSame(
                oldItem: EventEntity,
                newItem: EventEntity
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: EventEntity,
                newItem: EventEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = EventCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(event.id.toString())
        }
    }

    class ViewHolder(private val binding: EventCardBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(event: EventEntity) {
            with(binding) {
                Glide.with(root.context)
                    .load(event.mediaCover)
                    .into(eventImg)

                tvTitle.text = event.name
                tvBeginTime.text = event.beginTime
                tvAuthor.text = root.resources.getString(R.string.author, event.ownerName)
                tvSummary.text = event.summary
                tvLocation.text = root.resources.getString(R.string.location, event.cityName)
                tvQuota.text = root.resources.getString(R.string.quota, event.quota)
            }
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


}