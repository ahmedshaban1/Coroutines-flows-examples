package com.coroutines.examples.flowactivities.full_network_example

data class Resource<out T>(
    val status: Status, val data: T?, val messageType: MessageType?,
    val dataSource: DataSource? = null
) {
    companion object {
        fun <T> success(data: T?,dataSource:DataSource = DataSource.REMOTE): Resource<T> {
            return Resource(
                Status.SUCCESS,
                data,
                null,
                dataSource = dataSource
            )
        }

        fun <T> error(error: MessageType, data: T?): Resource<T> {
            return Resource(
                Status.ERROR,
                data,
                error
            )
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(
                Status.LOADING,
                data,
                null
            )
        }
    }

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    enum class DataSource {
        REMOTE,
        LOCAL,
    }
}