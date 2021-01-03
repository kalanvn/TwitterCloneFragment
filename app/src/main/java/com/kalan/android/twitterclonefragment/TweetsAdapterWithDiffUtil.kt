package com.kalan.android.twitterclonefragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kalan.android.twitterclonefragment.model.TweetData

class TweetsAdapterWithDiffUtil (val tweetListItemClickListener: TweetListItemClickListenerWithDiffUtil)
    : ListAdapter<TweetData, TweetsAdapterWithDiffUtil.TweetsViewHolder>(TweetListItemDiffCallback()) {
    class TweetsViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView){
        val textViewListItem : TextView = listItemView.findViewById(R.id.textViewListItem)
        val imageViewListItem : ImageView = listItemView.findViewById(R.id.imageViewListItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetsViewHolder {
        val tweetsListItemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_tweet, parent, false)
        return TweetsViewHolder(tweetsListItemView)
    }

    override fun onBindViewHolder(holder: TweetsViewHolder, position: Int) {
        holder.textViewListItem.text = getItem(position).tweetText
//        holder.textViewListItem.setText(dataSet[position].tweetTextId)
        holder.textViewListItem.setOnClickListener {
            tweetListItemClickListener.textViewListItemClickListener(getItem(position).tweetText)
        }
        holder.imageViewListItem.setImageResource(getItem(position).tweetImageId)
        holder.imageViewListItem.setOnClickListener {
            tweetListItemClickListener.imageViewListItemClickListener()
        }
        holder.itemView.setOnClickListener {
            tweetListItemClickListener.listItemClickListener(getItem(position))
        }
    }
}

interface TweetListItemClickListenerWithDiffUtil {
    fun listItemClickListener(tweetData: TweetData)
    fun textViewListItemClickListener(textString : String)
    fun imageViewListItemClickListener()
}

class TweetListItemDiffCallback : DiffUtil.ItemCallback<TweetData> () {
    override fun areItemsTheSame(oldItem: TweetData, newItem: TweetData): Boolean {
        return oldItem.tweetText.equals(newItem.tweetText)
    }

    override fun areContentsTheSame(oldItem: TweetData, newItem: TweetData): Boolean {
        return oldItem == newItem
    }
}