package com.example.demo.di.Module

import androidx.work.ListenableWorker
import com.example.demo.worker.ChildWorkerFactory
import com.example.demo.worker.WorkerFactory
import com.example.demo.worker.WorkerManagerment
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Module
abstract class WorkerModule {

    @Binds
    abstract fun bindWorkerFactory(workerFactory: WorkerFactory?): androidx.work.WorkerFactory?

    @Binds
    @IntoMap
    @WorkerKey(WorkerManagerment::class)
    abstract fun bindWorkerManagerment(worker: WorkerManagerment.Factory?): ChildWorkerFactory?
}

@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class WorkerKey(val value: KClass<out ListenableWorker>)