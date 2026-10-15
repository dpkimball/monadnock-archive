package com.oxbow.monadnock.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.oxbow.monadnock.ui.repo.RepoViewModel
import com.oxbow.monadnock.ui.search.SearchViewModel
import com.oxbow.monadnock.ui.user.UserViewModel
import com.oxbow.monadnock.viewmodel.TwinPeaksViewModelFactory

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel::class)
    abstract fun bindUserViewModel(userViewModel: UserViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(searchViewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RepoViewModel::class)
    abstract fun bindRepoViewModel(repoViewModel: RepoViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: TwinPeaksViewModelFactory): ViewModelProvider.Factory
}
