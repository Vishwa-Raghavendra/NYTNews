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

class TopStoriesAdapter(private val storiesBookMarkListener: StoriesBookMarkListener) : RecyclerView.Adapter<TopStoriesAdapter.TopStoriesViewHolder>()
{
    interface StoriesBookMarkListener
    {
        fun onBookMarked(result: Result)
    }

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

            itemView.ib_topStoriesCell.setOnClickListener {
                result.bookmarked = !result.bookmarked
                setImage(result,(it as ImageButton))
            }
        }

        private fun setImage(result: Result,imageButton: ImageButton)
        {
            if(result.bookmarked)
                imageButton.setImageResource(R.drawable.ic_baseline_bookmark_24)
            else
                imageButton.setImageResource(R.drawable.ic_baseline_bookmark_border_24)

            storiesBookMarkListener.onBookMarked(result)
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