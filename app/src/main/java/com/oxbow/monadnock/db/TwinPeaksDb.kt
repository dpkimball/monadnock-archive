package com.oxbow.monadnock.db


import androidx.room.Database
import androidx.room.RoomDatabase
import com.oxbow.monadnock.vo.Contributor
import com.oxbow.monadnock.vo.Repo
import com.oxbow.monadnock.vo.RepoSearchResult
import com.oxbow.monadnock.vo.User

/**
 * Main database description.
 */
@Database(
    entities = [
        User::class,
        Repo::class,
        Contributor::class,
        RepoSearchResult::class],
    version = 3,
    exportSchema = false
)
abstract class TwinPeaksDb : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun repoDao(): RepoDao
}
