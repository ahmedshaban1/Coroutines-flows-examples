package com.coroutines.examples.flowactivities.full_network_example



import com.coroutines.examples.flowactivities.full_network_example.NetworkCodes.GENERAL_ERROR
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import retrofit2.HttpException
import java.io.IOException

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class GenericError(val messageType: MessageType) : ResultWrapper<Nothing>()
}


suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T?
): ResultWrapper<T?> {
    return withContext(dispatcher) {
        try {
            val call = apiCall.invoke()
            ResultWrapper.Success(call)
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            when (throwable) {
                is TimeoutCancellationException -> {
                    ResultWrapper.GenericError(messageType = MessageType.SnackBar(NetworkCodes.TIMEOUT_ERROR))
                }
                is IOException -> {
                    ResultWrapper.GenericError(
                        MessageType.SnackBar(NetworkCodes.CONNECTION_ERROR)
                    )
                }
                is HttpException -> {
                    val code = throwable.code()
                    val errorResponse =  convertErrorBody(throwable)
                    errorResponse?.errors?.let {
                        ResultWrapper.GenericError(
                            MessageType.Dialog(code, message = errorResponse)
                        )
                    }?: kotlin.run {
                        ResultWrapper.GenericError(
                            MessageType.SnackBar(code, message = errorResponse)
                        )
                    }

                }
                else -> {
                    ResultWrapper.GenericError(
                        MessageType.Dialog(GENERAL_ERROR)
                    )
                }
            }

        }
    }
}


//for custom error body
private fun convertErrorBody(throwable: HttpException): ErrorResponse? {
    try {
        val json = JSONTokener(throwable.response()?.errorBody()?.string()).nextValue();
        if (json is JSONObject || json is JSONArray) {
            val errorResponse = Gson().fromJson(json.toString(), ErrorResponse::class.java)
            errorResponse?.let { return it }
        }
        return null

    } catch (exception: Exception) {
        return null
    }
}

class ErrorResponse(val message: String? = "",val errors:JsonObject)

sealed class MessageType(var code:Int, var message: ErrorResponse? = null){
    class SnackBar(code:Int, message: ErrorResponse? = null) : MessageType(code,message)
    class Dialog(code:Int,message:ErrorResponse? = null) : MessageType(code,message)
    class Toast(code:Int,message:ErrorResponse? = null): MessageType(code,message)
    class None(code:Int,message:ErrorResponse? = null): MessageType(code,message)
}


object NetworkCodes {
    const val BENEFICIARY_NOT_COMPLETED = 1003
    const val CONNECTION_ERROR  = 100
    const val TIMEOUT_ERROR = 408
    const val GENERAL_ERROR = 1000
    const val CONNECTION_NOT_FOUND = 1001
    const val BENEFICIARY_LIMIT_ERROR = 1002

}

