package com.example.demo.ui.man1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demo.R
import com.example.demo.databinding.FragmentMan1Binding
import com.example.demo.ui.adapter.RcvPagingAdapter
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class FragmentMan1 : DaggerFragment(R.layout.fragment_man1) {

    @Inject
    lateinit var viewmodelFactory: ViewModelProvider.Factory
    private lateinit var binding: FragmentMan1Binding
    private lateinit var man1ViewModel: Man1ViewModel
    private var rcvPagingAdapter = RcvPagingAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMan1Binding.inflate(inflater, container, false)
        val root: View = binding.root

        initViewModel()
        initRecyclerView()

        return root
    }

    private fun initViewModel() {
        man1ViewModel = ViewModelProvider(this, viewmodelFactory).get(Man1ViewModel::class.java)
        lifecycleScope.launchWhenCreated {
            man1ViewModel.getListFromApi().collectLatest {
                rcvPagingAdapter.submitData(it)
            }
        }
    }

    private fun initRecyclerView() {
        binding.rcv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            val decoration =
                DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)
            adapter = rcvPagingAdapter
        }
    }
}