package com.islam.githubapp.ui.adapters

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.islam.githubapp.R
import com.islam.githubapp.databinding.OneItemListBinding
import com.islam.githubapp.generalUtils.Const
import com.islam.githubapp.generalUtils.Utils
import com.kharismarizqii.githubuserapp.core.data.source.remote.response.UserResponse
import dagger.hilt.android.internal.managers.ViewComponentManager

class MainAdapter : PagingDataAdapter<UserResponse, MainAdapter.ViewHolder>(DIFF_CALLBACK) {

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
        private var username: TextView = itemView.userName
        private var userImage: ImageView = itemView.userImage

        fun bind(listItems: UserResponse) {

            username.text = listItems.username

            loadImage(itemView.context, listItems.avatarUrl, userImage)

            itemView.setOnClickListener { view ->

                Utils.hideKeyboard(activityContext(itemView) as Activity)

                navigateToUserDetails(view, listItems)

            }

        }
    }

    private fun navigateToUserDetails(view: View, listItems: UserResponse) {
        val bundle = Bundle()
        bundle.putString(Const.UserDetailsKey, listItems.username)
        view.findNavController().navigate(
            R.id.action_mainFragment_to_userDetailsFragment,
            bundle
        )
    }

    fun loadImage(context: Context, url: String?, logo: ImageView) {
        Glide.with(context).load(Uri.parse(url))
            .placeholder(R.drawable.ic_language)
            .thumbnail(0.1f)
            .circleCrop()
            .into(logo)
    }

    private fun activityContext(itemView: View): Context? {
        val context = itemView.context
        return if (context is ViewComponentManager.FragmentContextWrapper) {
            context.baseContext
        } else context
    }

}