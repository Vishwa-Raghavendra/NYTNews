package com.nyt.nytnews.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nyt.nytnews.R
import com.nyt.nytnews.adapters.TopStoriesAdapter
import com.nyt.nytnews.database.NYTDatabase
import com.nyt.nytnews.utility.Resource
import com.nyt.nytnews.utility.RetrofitInstance
import com.nyt.nytnews.viewModels.MainViewModel
import kotlinx.android.synthetic.main.fragment_top_stories.*
import kotlinx.coroutines.*

class TopStoriesFragment : Fragment() {


    lateinit var topStoriesAdapter :TopStoriesAdapter
    lateinit var viewModel:MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top_stories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        setUpLiveDataObservers()
    }

    private fun setUpLiveDataObservers() {
        viewModel.topStories.observe(viewLifecycleOwner)
        {
            when(it)
            {
                is Resource.Success -> {
                    pb_topStories.visibility = View.GONE
                    topStoriesAdapter.differ.submitList(it.data!!)
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), ""+it.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

    private fun setUpRecyclerView() {
        val lm = LinearLayoutManager(requireContext())
        lm.orientation = LinearLayoutManager.VERTICAL

        topStoriesAdapter = TopStoriesAdapter()

        topStoriesRecyclerView.apply {
            adapter = topStoriesAdapter
            layoutManager = lm
        }
    }

}