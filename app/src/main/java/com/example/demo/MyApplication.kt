package com.example.demo

import android.util.Log
import androidx.work.Configuration
import com.example.demo.data.component.DaggerAppComponent
import com.example.demo.worker.WorkerFactory
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Inject

class MyApplication : DaggerApplication() , Configuration.Provider{
    private val applicationInjector = DaggerAppComponent.builder().application(this).build()
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = applicationInjector

    @Inject
    lateinit var workerFactory: WorkerFactory

    override fun getWorkManagerConfiguration() = Configuration.Builder()
        .setMinimumLoggingLevel(Log.INFO)
        .setWorkerFactory(workerFactory)
        .build()
}