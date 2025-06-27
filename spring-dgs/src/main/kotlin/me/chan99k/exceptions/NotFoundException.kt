package me.chan99k.exceptions

import com.netflix.graphql.types.errors.ErrorType

class NotFoundException(
    override val message: String = "Not Found Exception"


) : CustomException(
    message = message,
    errorType = ErrorType.NOT_FOUND,
    errorCode = 1002
) {
}