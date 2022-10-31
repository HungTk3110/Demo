package com.example.demo.data.di.Module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.demo.ui.ViewModelFactory
import com.example.demo.ui.man1.Man1ViewModel
import com.example.demo.ui.man2.Man2ViewModel
import com.example.demo.ui.man3.Man3ViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Module
internal abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(Man1ViewModel::class)
    internal abstract fun bindsMan1ViewModel(man1ViewModel: Man1ViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(Man2ViewModel::class)
    internal abstract fun bindsMan2ViewModel(man2ViewModel: Man2ViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(Man3ViewModel::class)
    internal abstract fun bindsMan3ViewModel(man3ViewModel: Man3ViewModel): ViewModel

}

@MustBeDocumented
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)
