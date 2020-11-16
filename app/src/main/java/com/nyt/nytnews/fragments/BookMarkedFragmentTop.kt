package com.nyt.nytnews.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.nyt.nytnews.R
import com.nyt.nytnews.adapters.TopStoriesAdapter
import com.nyt.nytnews.models.Result
import com.nyt.nytnews.viewModels.MainViewModel
import kotlinx.android.synthetic.main.fragment_book_marked.*

class BookMarkedFragmentTop : Fragment(),TopStoriesAdapter.TopStoriesAdapterListener {

    lateinit var topStoriesAdapter :TopStoriesAdapter
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_marked, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        setUpLiveDataObservers()
    }

    private fun setUpLiveDataObservers() {
        viewModel.bookmarks.observe(viewLifecycleOwner,{
            topStoriesAdapter.differ.submitList(it)
        })
    }

    private fun setUpRecyclerView() {
        val lm = GridLayoutManager(requireContext(),2)
        lm.orientation = LinearLayoutManager.VERTICAL

        topStoriesAdapter = TopStoriesAdapter(this)

        rv_bookmarks.apply {
            adapter = topStoriesAdapter
            layoutManager = lm
        }
    }

    override fun onBookMarked(result: Result) {
        viewModel.removeBookMark(result)
    }

    override fun onClicked(result: Result) {

    }

    override fun onResume() {
        super.onResume()
        viewModel.getBookmarks()
    }
}