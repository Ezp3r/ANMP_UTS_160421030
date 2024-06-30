package com.ezper.advuts160421030.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ezper.advuts160421030.R
import com.ezper.advuts160421030.databinding.FragmentHomeBinding
import com.ezper.advuts160421030.databinding.FragmentLoginBinding
import com.ezper.advuts160421030.viewmodel.ListViewModel

class NewsListFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: ListViewModel
    private val newsListAdapter  = NewsListAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.refresh()
        binding.recView.layoutManager = LinearLayoutManager(context)
        binding.recView.adapter = newsListAdapter

        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.newsLD.observe(viewLifecycleOwner, Observer {
            newsListAdapter.updateNewsList(it)
            if(it.isEmpty()) {
                binding.recView?.visibility = View.GONE
                binding.txtError.setText("No News Available")
            } else {
                binding.recView?.visibility = View.VISIBLE
            }
        })

        viewModel.loadingLD.observe(viewLifecycleOwner, Observer {
            if(it == false) {
                binding.progressLoad?.visibility = View.GONE
            } else {
                binding.progressLoad?.visibility = View.VISIBLE
            }
        })

        viewModel.newsLoadErrorLD.observe(viewLifecycleOwner, Observer {
            if(it == false) {
                binding.txtError?.visibility = View.GONE
            } else {
                binding.txtError?.visibility = View.VISIBLE
            }
        })


    }
}