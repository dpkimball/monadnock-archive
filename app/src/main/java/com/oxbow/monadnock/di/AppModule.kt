package com.oxbow.monadnock.di

import android.app.Application
import androidx.room.Room
import com.oxbow.monadnock.api.TwinPeaksService
import com.oxbow.monadnock.db.TwinPeaksDb
import com.oxbow.monadnock.db.RepoDao
import com.oxbow.monadnock.db.UserDao
import com.oxbow.monadnock.util.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {
    @Singleton
    @Provides
    fun provideGithubService(): TwinPeaksService {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
            .create(TwinPeaksService::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): TwinPeaksDb {
        return Room
            .databaseBuilder(app, TwinPeaksDb::class.java, "github.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideUserDao(db: TwinPeaksDb): UserDao {
        return db.userDao()
    }

    @Singleton
    @Provides
    fun provideRepoDao(db: TwinPeaksDb): RepoDao {
        return db.repoDao()
    }
}
