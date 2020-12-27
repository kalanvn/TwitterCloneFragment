package com.kalan.android.twitterclonefragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kalan.android.twitterclonefragment.model.TweetData

class TweetsAdapter (val  dataSet: List<TweetData>) : RecyclerView.Adapter<TweetsAdapter.TweetsViewHolder>() {
    class TweetsViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView){
        val textViewListItem : TextView = listItemView.findViewById(R.id.textViewListItem)
        val imageViewListItem : ImageView = listItemView.findViewById(R.id.imageViewListItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetsViewHolder {
        val tweetsListItemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_tweet, parent, false)
        return TweetsViewHolder(tweetsListItemView)
    }

    override fun onBindViewHolder(holder: TweetsViewHolder, position: Int) {
//        holder.textViewListItem.text = dataSet[position].tweetText
        holder.textViewListItem.setText(dataSet[position].tweetTextId)
        holder.imageViewListItem.setImageResource(dataSet[position].tweetImageId)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}