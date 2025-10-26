package com.example.myapplication.core.data.network

@Suppress("MagicNumber")
class ResponseTypeMapper(val code: Int) {

    fun mapToResponseType(): ResponseType {
        return when (code) {
            NetworkParams.NO_CONNECTION_CODE -> ResponseType.NO_CONNECTION

            in 200..299 -> ResponseType.SUCCESS

            NetworkParams.BAD_REQUEST_CODE,
            NetworkParams.VALIDATION_ERROR_CODE,
            NetworkParams.FAILED_DEPENDENCY -> ResponseType.BAD_REQUEST

            NetworkParams.FORBIDDEN,
            NetworkParams.NOT_FOUND_CODE -> ResponseType.NOT_FOUND

            NetworkParams.EXISTING_CODE -> ResponseType.ALREADY_EXISTS

            NetworkParams.WRONG_PASSWORD -> ResponseType.WRONG_PASSWORD

            in 500..599 -> ResponseType.SERVER_ERROR

            else -> ResponseType.UNKNOWN
        }
    }
}