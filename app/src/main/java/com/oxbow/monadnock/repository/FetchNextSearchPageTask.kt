package com.oxbow.monadnock.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.oxbow.monadnock.api.ApiEmptyResponse
import com.oxbow.monadnock.api.ApiErrorResponse
import com.oxbow.monadnock.api.ApiResponse
import com.oxbow.monadnock.api.ApiSuccessResponse
import com.oxbow.monadnock.api.TwinPeaksService
import com.oxbow.monadnock.db.TwinPeaksDb
import com.oxbow.monadnock.vo.RepoSearchResult
import com.oxbow.monadnock.vo.Resource
import java.io.IOException

/**
 * A task that reads the search result in the database and fetches the next page, if it has one.
 */
class FetchNextSearchPageTask constructor(
    private val query: String,
    private val twinPeaksService: TwinPeaksService,
    private val db: TwinPeaksDb
) : Runnable {
    private val _liveData = MutableLiveData<Resource<Boolean>>()
    val liveData: LiveData<Resource<Boolean>> = _liveData

    override fun run() {
        val current = db.repoDao().findSearchResult(query)
        if (current == null) {
            _liveData.postValue(null)
            return
        }
        val nextPage = current.next
        if (nextPage == null) {
            _liveData.postValue(Resource.success(false))
            return
        }
        val newValue = try {
            val response = twinPeaksService.searchRepos(query, nextPage).execute()
            val apiResponse = ApiResponse.create(response)
            when (apiResponse) {
                is ApiSuccessResponse -> {
                    // we merge all repo ids into 1 list so that it is easier to fetch the
                    // result list.
                    val ids = arrayListOf<Int>()
                    ids.addAll(current.repoIds)

                    ids.addAll(apiResponse.body.items.map { it.id })
                    val merged = RepoSearchResult(
                        query, ids,
                        apiResponse.body.total, apiResponse.nextPage
                    )
                    try {
                        db.beginTransaction()
                        db.repoDao().insert(merged)
                        db.repoDao().insertRepos(apiResponse.body.items)
                        db.setTransactionSuccessful()
                    } finally {
                        db.endTransaction()
                    }
                    Resource.success(apiResponse.nextPage != null)
                }
                is ApiEmptyResponse -> {
                    Resource.success(false)
                }
                is ApiErrorResponse -> {
                    Resource.error(apiResponse.errorMessage, true)
                }
            }

        } catch (e: IOException) {
            Resource.error(e.message!!, true)
        }
        _liveData.postValue(newValue)
    }
}
