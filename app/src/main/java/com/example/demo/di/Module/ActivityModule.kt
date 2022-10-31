package com.example.demo.data.di.Module

import com.example.demo.ui.main.MainActivity
import com.example.demo.di.Module.FragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}