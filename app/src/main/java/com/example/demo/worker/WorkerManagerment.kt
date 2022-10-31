package com.example.demo.worker

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.work.*
import com.example.demo.data.data.model.CharacterData
import com.example.demo.data.repository.Repository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.Duration
import java.util.*
import javax.inject.Inject

class WorkerManagerment(
    context: Context,
    parameter: WorkerParameters,
    private val repository: Repository,
) : CoroutineWorker(context, parameter) {

    override suspend fun doWork(): Result {

        kotlin.runCatching {
            val page: Int = inputData.getInt(PAGE_INDEX, 1)
            repository.getApiWithPage(page).let {
                return Result.success(Data.Builder().apply {
                    val json = kotlin.runCatching { Gson().toJson(it) }.getOrNull()
                    this.putString(DATA, json)
                }.build())
            }
        }.getOrElse{
            Result.retry()
        }
        return Result.failure(Data.Builder().apply {
            // error, lỗi code bao nhiêu, lỗi gì
            this.putInt(ERROR, 401)
        }.build())
    }

    class Factory @Inject constructor(
        private val repository: Repository,
    ) : ChildWorkerFactory {
        override fun create(
            appContext: Context,
            params: WorkerParameters,
        ): CoroutineWorker {
            return WorkerManagerment(
                appContext,
                params,
                repository
            )
        }
    }

    companion object {
        private const val PAGE_INDEX = "PAGE_INDEX_PARAMS"
        private const val DATA = "DATA_JSON"
        private const val ERROR = "ERROR_CODE"

        fun createUUID(
            context: Context,
            page: Int
        ): UUID {
            // khởi tạo 1 builder cho WorkerCharacter
            val workBuilder = OneTimeWorkRequest.Builder(WorkerManagerment::class.java)
            workBuilder.setInputData(Data.Builder().apply {
                this.putInt(PAGE_INDEX ,page)
            }.build())
            // khởi chạy worker
            val oneTimeWorkRequest = workBuilder.apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    this.setInitialDelay(Duration.ofSeconds(2))
                }
            }.build()
            WorkManager.getInstance(context).enqueue(oneTimeWorkRequest)
            // Hứng nội dung worker
            return oneTimeWorkRequest.id
        }

        fun observerUUID(
            context: Context,
            uuid: UUID
        ): LiveData<Pair<WorkInfo, MutableList<CharacterData>?>> {
            return Transformations.map(
                WorkManager.getInstance(context).getWorkInfoByIdLiveData(uuid)
            ) {
                //converts dữ liệu sang dữ liệu mong muốn
                val json = it.outputData.getString(DATA)
                val CharacterData = kotlin.runCatching {
                    val listType = object : TypeToken<MutableList<CharacterData>>() {}.type
                    val list:MutableList<CharacterData> = Gson().fromJson(json,listType)
                    list
                }.getOrNull()
                Pair<WorkInfo,MutableList<CharacterData>?>(
                    it,CharacterData
                )
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun create(
            context: Context,
            page : Int
        ): LiveData<Pair<WorkInfo, MutableList<CharacterData>?>> {
            val uuid= createUUID(context ,page)
            return observerUUID(context,uuid)
        }
    }
}