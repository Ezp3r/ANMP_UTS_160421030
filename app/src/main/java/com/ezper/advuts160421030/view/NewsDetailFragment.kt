package com.ezper.advuts160421030.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.ezper.advuts160421030.databinding.FragmentNewsDetailBinding
import com.ezper.advuts160421030.viewmodel.DetailViewModel

class NewsDetailFragment : Fragment() {
    private lateinit var binding:FragmentNewsDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.txtTitle.text = NewsDetailFragmentArgs.fromBundle(requireArguments()).title
        binding.txtAuthor.text = "@"+NewsDetailFragmentArgs.fromBundle(requireArguments()).author
        binding.btnPrev.isEnabled = false
        binding.btnNext.isEnabled = false
        val picasso = Picasso.Builder(this.requireContext())
        picasso.listener { picasso, uri, exception ->
            exception.printStackTrace()
        }
        picasso.build().load(NewsDetailFragmentArgs.fromBundle(requireArguments()).url).into(binding.imageView, object:
            Callback {
            override fun onSuccess() {
                binding.progressLoadImg2.visibility = View.INVISIBLE
                binding.imageView.visibility = View.VISIBLE
            }

            override fun onError(e: Exception?) {
                Log.e("picasso_error", e.toString())
            }

        })

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        if(arguments != null) {
            val ID =
                NewsDetailFragmentArgs.fromBundle(requireArguments()).id
            viewModel.fetch(ID.toString())
            observeViewModel()
        }
    }

    fun observeViewModel() {
        viewModel.detailLD.observe(viewLifecycleOwner, Observer{
            var detail = it
            var page = 0
            binding.txtSubtitle.text = detail[page].subtitle
            binding.txtIsi.text = detail[page].isi
            binding.txtPage.text = detail[page].page.toString() + " / " + detail.size.toString()

            if (detail.size>1){
                binding.btnNext.isEnabled=true
            }
            binding.btnPrev.setOnClickListener {
                binding.btnNext.isEnabled=true
                page--
                if (page==0){
                    binding.btnPrev.isEnabled=false
                }
                binding.txtSubtitle.text = detail[page].subtitle
                binding.txtIsi.text = detail[page].isi
                binding.txtPage.text = detail[page].page.toString() + " / " + detail.size.toString()
            }
            binding.btnNext.setOnClickListener {
                binding.btnPrev.isEnabled=true
                page++
                if (page==detail.size-1){
                    binding.btnNext.isEnabled=false
                }
                binding.txtSubtitle.text = detail[page].subtitle
                binding.txtIsi.text = detail[page].isi
                binding.txtPage.text = detail[page].page.toString() + " / " + detail.size.toString()
            }
        })

    }

}