package app.migrosone.contract

sealed interface ResultState<out T> {
    data class Success<out T>(val data: T) : ResultState<T>

    data class Error(val appException: AppException) : ResultState<Nothing>

    data object Loading : ResultState<Nothing>
}
