package com.hariharan.aboutcanada.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hariharan.aboutcanada.R
import com.hariharan.aboutcanada.data.model.Facts
import com.hariharan.aboutcanada.databinding.MainFragmentBinding

/**
 * Fragment to display the facts list.
 */
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    private lateinit var binding: MainFragmentBinding

    private val observer = Observer<Facts> { facts ->
        binding.main.isRefreshing = false
        val title = facts?.title
        if (!title.isNullOrEmpty()) {
            activity?.title = title
        }
        val factsList = facts?.facts
        if (!factsList.isNullOrEmpty()) {
            val adapter = FactsAdapter(factsList)
            binding.recyclerview.adapter = adapter
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.getResponseLD().observe(viewLifecycleOwner, observer)
        binding.recyclerview.layoutManager = LinearLayoutManager(activity)
        binding.main.setOnRefreshListener {
            viewModel.fetchData()
        }
        if (!viewModel.isNetworkAvailable()) {
            Toast.makeText(context, R.string.no_internet, Toast.LENGTH_SHORT).show()
        }
    }

}