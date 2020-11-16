package com.nyt.nytnews.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.nyt.nytnews.R
import com.nyt.nytnews.models.Result
import kotlinx.android.synthetic.main.top_stories_cell.view.*
import java.lang.Exception

class TopStoriesAdapter(private val topStoriesAdapterListener: TopStoriesAdapterListener) :
    RecyclerView.Adapter<TopStoriesAdapter.TopStoriesViewHolder>() {
    interface TopStoriesAdapterListener {
        fun onBookMarked(result: Result)
        fun onClicked(result: Result)
    }

    private val diffCallBack = object : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.shortUrl == newItem.shortUrl
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.bookmarked == newItem.bookmarked
        }
    }

    val differ = AsyncListDiffer(this, diffCallBack)

    inner class TopStoriesViewHolder(cell: View) : RecyclerView.ViewHolder(cell) {
        fun init(result: Result) {

            itemView.setOnClickListener {
                topStoriesAdapterListener.onClicked(result)
            }


            try {
                itemView.iv_topStories.load(result.multimedia[3].url)
            }catch (e:Exception)
            { }

            itemView.tv_titleTopStories.text = result.title
            itemView.tv_dateTopStories.text = result.displayDate

            setImage(result,itemView.ib_topStoriesCell)

            itemView.ib_topStoriesCell.setOnClickListener {
                result.bookmarked = !result.bookmarked
                setImage(result, (it as ImageButton), true)
            }
        }

        private fun setImage(result: Result, imageButton: ImageButton, change: Boolean = false) {
            if (result.bookmarked)
                imageButton.setImageResource(R.drawable.ic_baseline_bookmark_24)
            else
                imageButton.setImageResource(R.drawable.ic_baseline_bookmark_border_24)

            if (change)
                topStoriesAdapterListener.onBookMarked(result)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopStoriesViewHolder {
        return TopStoriesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.top_stories_cell, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TopStoriesViewHolder, position: Int) {
        holder.init(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}