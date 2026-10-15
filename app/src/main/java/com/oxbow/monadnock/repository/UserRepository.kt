package com.oxbow.monadnock.repository

import androidx.lifecycle.LiveData
import com.oxbow.monadnock.AppExecutors
import com.oxbow.monadnock.api.TwinPeaksService
import com.oxbow.monadnock.db.UserDao
import com.oxbow.monadnock.testing.OpenForTesting
import com.oxbow.monadnock.vo.Resource
import com.oxbow.monadnock.vo.User
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository that handles User objects.
 */
@OpenForTesting
@Singleton
class UserRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val userDao: UserDao,
    private val twinPeaksService: TwinPeaksService
) {

    fun loadUser(login: String): LiveData<Resource<User>> {
        return object : NetworkBoundResource<User, User>(appExecutors) {
            override fun saveCallResult(item: User) {
                userDao.insert(item)
            }

            override fun shouldFetch(data: User?) = data == null

            override fun loadFromDb() = userDao.findByLogin(login)

            override fun createCall() = twinPeaksService.getUser(login)
        }.asLiveData()
    }
}
