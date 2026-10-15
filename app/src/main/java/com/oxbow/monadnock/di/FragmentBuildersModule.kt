package com.oxbow.monadnock.di

import com.oxbow.monadnock.ui.repo.RepoFragment
import com.oxbow.monadnock.ui.search.SearchFragment
import com.oxbow.monadnock.ui.user.UserFragment

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeRepoFragment(): RepoFragment

    @ContributesAndroidInjector
    abstract fun contributeUserFragment(): UserFragment

    @ContributesAndroidInjector
    abstract fun contributeSearchFragment(): SearchFragment
}
