package com.example.demo.ui.man1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.demo.data.repository.Repository
import javax.inject.Inject

class Man1ViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    fun getListFromApi() =
        repository.getApi().cachedIn(viewModelScope)
}
