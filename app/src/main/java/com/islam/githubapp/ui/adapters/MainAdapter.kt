package com.islam.githubapp.ui.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.islam.githubapp.R
import com.islam.githubapp.databinding.OneItemListBinding
import com.kharismarizqii.githubuserapp.core.data.source.remote.response.UserResponse

class MainAdapter() : PagingDataAdapter<UserResponse, MainAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserResponse>() {
            override fun areItemsTheSame(oldItem: UserResponse, newItem: UserResponse): Boolean {

                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: UserResponse,
                newItem: UserResponse
            ): Boolean {

                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            OneItemListBinding.bind(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.one_item_list, parent, false)
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val listItems = getItem(position)
        holder.bind(listItems!!)

    }

    inner class ViewHolder(itemView: OneItemListBinding) : RecyclerView.ViewHolder(itemView.root) {
        private var label: TextView = itemView.label
        private var logo: ImageView = itemView.logo

        fun bind(listItems: UserResponse) {

            label.text = listItems.username

            loadImage(itemView.context, listItems.avatarUrl, logo)

        }
    }

    fun loadImage(context: Context, url: String?, logo: ImageView) {
        Glide.with(context).load(Uri.parse(url))
            .placeholder(R.drawable.ic_language)
            .thumbnail(0.1f)
            .into(logo)
    }

}