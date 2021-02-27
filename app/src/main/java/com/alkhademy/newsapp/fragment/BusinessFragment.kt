package com.alkhademy.newsapp.fragment

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alkhademy.newsapp.R
import com.alkhademy.newsapp.activity.DetailNewsActivity
import com.alkhademy.newsapp.adapter.ArticleAdapter
import com.alkhademy.newsapp.databinding.FragmentBusinessBinding
import com.alkhademy.newsapp.model.Article
import com.alkhademy.newsapp.viewmodel.BusinessViewModel


class BusinessFragment : Fragment() {

    private var _binding: FragmentBusinessBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: BusinessViewModel
    private lateinit var adapter: ArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBusinessBinding.inflate(inflater, container, false)

        adapter = ArticleAdapter()
        adapter.notifyDataSetChanged()
        viewModel =
            ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory()).get(
                BusinessViewModel::class.java
            )

        binding.apply {
            rvBusiness.layoutManager = LinearLayoutManager(context)
            rvBusiness.setHasFixedSize(true)
            rvBusiness.adapter = adapter
            adapter.setOnItemClickCallback(object : ArticleAdapter.OnItemClickCallback {
                override fun onItemClicked(data: Article) {
                    showSelectedNews(data)
                }
            })

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    adapter.filter.filter(newText)
                    return false
                }

            })
        }

        if (isConnect()) {
            viewModel.setBusiness("id", "business", getString(R.string.api_key))

            viewModel.getBusiness().observe(requireActivity(), {
                if (it != null) {
                    adapter.setList(it)
                    showLoading(false)
                } else {
                    Toast.makeText(context, getString(R.string.request_limit), Toast.LENGTH_LONG)
                        .show()
                }
            })
        } else {
            showLoading(false)
            Toast.makeText(context, getString(R.string.not_connect), Toast.LENGTH_LONG).show()
        }

        return binding.root
    }

    private fun isConnect(): Boolean {
        val connect: ConnectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return connect.activeNetworkInfo != null && connect.activeNetworkInfo!!.isConnected
    }

    private fun showSelectedNews(data: Article) {
        val intent = Intent(context, DetailNewsActivity::class.java)
        intent.putExtra("url", data.url)
        startActivity(intent)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}