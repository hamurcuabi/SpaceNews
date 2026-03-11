package app.migrosone.contract

import java.io.IOException

sealed class AppException(
    message: String,
    val code: Int,
    cause: Throwable? = null
) : Exception(message, cause) {

    data class Network(override val cause: Throwable? = null) : AppException(
        message = cause?.message ?: "Network error",
        code = -2,
        cause = cause
    )

    data class Server(
        val httpCode: Int,
        override val cause: Throwable? = null
    ) : AppException(
        message = cause?.message ?: "Server error",
        code = httpCode,
        cause = cause
    )

    data class Unknown(override val cause: Throwable? = null) : AppException(
        message = cause?.message ?: "Unknown error",
        code = -1,
        cause = cause
    )

    companion object {
        fun from(exception: Throwable): AppException {
            return when (exception) {
                is IOException -> Network(exception)
                else -> Unknown(exception)
            }
        }
    }
}
