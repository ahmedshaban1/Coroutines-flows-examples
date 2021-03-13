package com.coroutines.examples.flowactivities.full_network_example

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

abstract class NetworkBoundResource<T> {

    @ExperimentalCoroutinesApi
    fun asFlow(): Flow<Resource<T>> = flow {

        if (shouldFetch()) {
            if (shouldFetchWithLocalData()) {
                emit(Resource.loading(data = localFetch()))
            }
            if (isHasInterNet()) {
                val results = safeApiCall(dispatcher = Dispatchers.IO) {
                    remoteFetch()
                }
                when (results) {
                    is ResultWrapper.Success -> {
                        results.value?.let {
                            saveFetchResult(results.value)
                        }
                        emit(Resource.success(data = results.value))
                        updateEntry(results.value)
                    }

                    is ResultWrapper.GenericError -> {
                        emit(Resource.error(data = null, error = results.messageType))
                    }
                }
            } else {
                saveFetchResult(null)
                emit(Resource.success(dataSource = Resource.DataSource.LOCAL, data = localFetch()))
            }

        } else {
            //get from cash
            val data = localFetch()
            emit(Resource.loading(data = data))
        }


    }.onStart {
        //get From cache
        if(shouldFetch())
            emit(Resource.loading(data = null))
    }


    abstract suspend fun remoteFetch(): T
    abstract suspend fun saveFetchResult(data: T?)
    abstract suspend fun localFetch(): T
    open fun onFetchFailed(throwable: Throwable) = Unit
    open fun shouldFetch() = true
    open fun shouldFetchWithLocalData() = false
    open fun isHasInterNet() = true
    open suspend fun updateEntry(data: T?) {}
}