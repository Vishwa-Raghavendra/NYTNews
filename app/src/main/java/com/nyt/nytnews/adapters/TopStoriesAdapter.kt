package com.nyt.nytnews.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.nyt.nytnews.R
import com.nyt.nytnews.models.Result
import kotlinx.android.synthetic.main.top_stories_cell.view.*

class TopStoriesAdapter : RecyclerView.Adapter<TopStoriesAdapter.TopStoriesViewHolder>()
{
    private val diffCallBack = object :DiffUtil.ItemCallback<Result>()
    {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.shortUrl == newItem.shortUrl
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,diffCallBack)

    inner class TopStoriesViewHolder(cell: View) : RecyclerView.ViewHolder(cell)
    {
        fun init(result: Result)
        {
            itemView.iv_topStories.load(result.multimedia[3].url)
            itemView.tv_titleTopStories.text = result.title
            itemView.tv_dateTopStories.text = result.publishedDate
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopStoriesViewHolder {
        return TopStoriesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.top_stories_cell,parent,false))
    }

    override fun onBindViewHolder(holder: TopStoriesViewHolder, position: Int) {
        holder.init(differ.currentList[position])
    }

    override fun getItemCount(): Int {
       return differ.currentList.size
    }
}