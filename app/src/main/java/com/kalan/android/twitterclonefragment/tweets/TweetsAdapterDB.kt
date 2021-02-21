package com.kalan.android.twitterclonefragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kalan.android.twitterclonefragment.database.Tweet
import com.kalan.android.twitterclonefragment.model.TweetData

class TweetsAdapterDB (val tweetListItemClickListener: TweetDBListItemClickListener)
    : RecyclerView.Adapter<TweetsAdapterDB.TweetsViewHolder>() {

    var dataSet = listOf<Tweet>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class TweetsViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView){
        val textViewListItem : TextView = listItemView.findViewById(R.id.textViewListItem)
        val imageViewListItem : ImageView = listItemView.findViewById(R.id.imageViewListItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetsViewHolder {
        val tweetsListItemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_tweet, parent, false)
        return TweetsViewHolder(tweetsListItemView)
    }

    override fun onBindViewHolder(holder: TweetsViewHolder, position: Int) {
        holder.textViewListItem.text = dataSet[position].text
//        holder.textViewListItem.setText(dataSet[position].tweetTextId)
//        holder.textViewListItem.setOnClickListener {
//            tweetListItemClickListener.textViewListItemClickListener(dataSet[position].text)
//        }
//        holder.imageViewListItem.setImageResource(dataSet[position].tweetImageId)
//        holder.imageViewListItem.setOnClickListener {
//            tweetListItemClickListener.imageViewListItemClickListener()
//        }

        val uri = dataSet[position].url?.toUri()?.buildUpon()?.scheme("https")?.build()
        Glide.with(holder.itemView.context)
            .load(uri)
            .into(holder.imageViewListItem)
        holder.itemView.setOnClickListener {
            tweetListItemClickListener.listItemClickListener(dataSet[position])
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}

interface TweetDBListItemClickListener {
    fun listItemClickListener(tweet: Tweet)
    fun textViewListItemClickListener(textString : String)
    fun imageViewListItemClickListener()
}