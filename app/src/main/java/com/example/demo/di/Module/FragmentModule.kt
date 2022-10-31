package com.example.demo.di.Module

import com.example.demo.ui.man1.FragmentMan1
import com.example.demo.ui.man2.FragmentMan2
import com.example.demo.ui.man3.FragmentMan3
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeFragmentMan1() : FragmentMan1

    @ContributesAndroidInjector
    abstract fun contributeFragmentMan2() : FragmentMan2

    @ContributesAndroidInjector
    abstract fun contributeFragmentMan3() : FragmentMan3

}