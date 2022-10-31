package com.example.demo.ui.man3

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.demo.data.data.model.CharacterData
import com.example.demo.data.repository.Repository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

class Man3ViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val page = MutableLiveData<Int>()

    @OptIn(FlowPreview::class)
    val responseFlow = page.asFlow().flatMapMerge { page ->
        flowOf(repository.getApiWithPage(page ?:1))
    }
    val responseLive = MutableLiveData<MutableList<CharacterData>>()

    init {
        viewModelScope.launch {
            @OptIn(FlowPreview::class)
            page.asFlow().flatMapMerge {
                flowOf(repository.getApiWithPage(it ?:1))
            }.collectLatest {
                responseLive.postValue(it)
            }
        }
    }
}