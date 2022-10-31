package com.example.demo.data.di.Module

import ccom.example.demo.data.di.Module.RoomDbModule
import com.example.demo.di.Module.FragmentModule
import com.example.demo.di.Module.WorkerModule

import dagger.Module

@Module(
    includes = [
        ActivityModule::class ,
        ApiModule :: class ,
        RoomDbModule :: class ,
        ViewModelModule :: class ,
        RepositoryModule::class ,
        FragmentModule::class ,
        WorkerModule::class
    ]
)
class AppModule