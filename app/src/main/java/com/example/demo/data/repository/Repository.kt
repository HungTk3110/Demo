package com.example.demo.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.example.demo.data.data.model.CharacterData
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getApi(): Flow<PagingData<CharacterData>>
    fun getApiV2(): Flow<PagingData<CharacterData>>
    suspend fun getApiWithPage(page: Int ) : MutableList<CharacterData>


    fun getApiWithLiveData() : MutableLiveData<MutableList<CharacterData>>
    fun getApiWWithWorker() : MutableList<CharacterData>
}