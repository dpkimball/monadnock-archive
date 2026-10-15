package com.oxbow.monadnock.di

import com.oxbow.monadnock.MainActivity

import dagger.Module
import dagger.android.ContributesAndroidInjector
import kotlin.text.Typography.dagger

@Suppress("unused")
@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeMainActivity(): MainActivity
}
