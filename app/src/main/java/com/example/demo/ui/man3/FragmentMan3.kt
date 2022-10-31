package com.example.demo.ui.man3

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.WorkInfo
import com.example.demo.R
import com.example.demo.databinding.FragmentMan3Binding
import com.example.demo.ui.adapter.RcvAdapter
import com.example.demo.worker.WorkerManagerment
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class FragmentMan3 : DaggerFragment(R.layout.fragment_man3) {

    @Inject
    lateinit var viewmodelFactory: ViewModelProvider.Factory
    private lateinit var binding: FragmentMan3Binding
    private lateinit var man3ViewModel: Man3ViewModel
    private var rcvAdapter = RcvAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMan3Binding.inflate(inflater, container, false)
        val root: View = binding.root
        man3ViewModel = ViewModelProvider(this, viewmodelFactory).get(Man3ViewModel::class.java)

        binding.btnWithLiveData.setOnClickListener {
            initViewModelWithLiveData()
            man3ViewModel.page.value = 1
            initRecyclerView()
            Log.d("page", man3ViewModel.page.value.toString())
//            Log.d("list",man3ViewModel.test.toString())
        }
        binding.btnWithFlow.setOnClickListener {
            initViewModelWithFlow()
            man3ViewModel.page.value = 2
            Log.d("page", man3ViewModel.page.value.toString())
            initRecyclerView()
        }
        binding.btnWithWorker.setOnClickListener {
            initWorker()
            initRecyclerView()
        }
        return root
    }

    private fun initWorker() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WorkerManagerment.create(requireContext(), 4)
                .observe(viewLifecycleOwner, Observer {
                    when (it.first.state) {
                        WorkInfo.State.ENQUEUED,
                        WorkInfo.State.RUNNING,
                        -> {
                            //running
                            it.first.progress
                        }
                        WorkInfo.State.SUCCEEDED -> {
                            //success
                            it.second?.run {
                                rcvAdapter.setList(this)
                            }
                        }
                        else -> {
                            //fail
                        }
                    }
                })
        }
    }

    private fun initRecyclerView() {
        binding.rcv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            val decoration =
                DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)
            adapter = rcvAdapter
        }
    }

    private fun initViewModelWithFlow() {
        lifecycleScope.launch {
            man3ViewModel.responseFlow.collectLatest {
                rcvAdapter.setList(it)
            }
        }
    }

    private fun initViewModelWithLiveData() {
        lifecycleScope.launchWhenCreated {
            man3ViewModel.responseLive.observe(viewLifecycleOwner, Observer {
                rcvAdapter.setList(it)
            })
        }
    }
}
