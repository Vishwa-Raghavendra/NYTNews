package com.nyt.nytnews.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.nyt.nytnews.R
import com.nyt.nytnews.models.Result
import com.nyt.nytnews.viewModels.MainViewModel
import kotlinx.android.synthetic.main.fragment_detailed.*

class DetailedFragment : Fragment() {
    lateinit var viewModel: MainViewModel
    lateinit var clickedResult :Result
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        clickedResult = viewModel.clickedResult
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detailed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpData()
    }

    private fun setUpData() {
        iv_detailFragment.load(clickedResult.multimedia[0].url){
            placeholder(R.drawable.placeholder)
        }
        tv_newsTitleDetailedFragment.text = clickedResult.title
        tv_dateDetailedFragment.text = clickedResult.displayDate
        tv_abstractDetailedFragment.text = clickedResult.abstractText
        tv_websiteDetailedFragment.setOnClickListener {
            goToURL(clickedResult.shortUrl)
        }
    }


    private fun goToURL(url: String) {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.addCategory(Intent.CATEGORY_BROWSABLE)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }
}