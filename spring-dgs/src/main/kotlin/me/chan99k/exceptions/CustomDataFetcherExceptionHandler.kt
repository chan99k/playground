package me.chan99k.exceptions

import com.netflix.graphql.types.errors.ErrorDetail
import com.netflix.graphql.types.errors.ErrorType
import com.netflix.graphql.types.errors.TypedGraphQLError
import graphql.execution.DataFetcherExceptionHandler
import graphql.execution.DataFetcherExceptionHandlerParameters
import graphql.execution.DataFetcherExceptionHandlerResult
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture

@Component
class CustomDataFetcherExceptionHandler : DataFetcherExceptionHandler {
    override fun handleException(handlerParameters: DataFetcherExceptionHandlerParameters): CompletableFuture<DataFetcherExceptionHandlerResult> {
        val graphqlError: TypedGraphQLError
        if (handlerParameters.exception is CustomException) {
            graphqlError = (handlerParameters.exception as CustomException)
                .toGraphQlError(
                    handlerParameters.path, handlerParameters.sourceLocation
                )
        } else {
            graphqlError = TypedGraphQLError.newBuilder()
                .errorType(ErrorType.BAD_REQUEST)
                .message(handlerParameters.exception.message ?: "unknown error")
                .location(handlerParameters.sourceLocation)
                .path(handlerParameters.path)
                .errorDetail(ErrorDetail.Common.FIELD_NOT_FOUND)
                .debugInfo(mapOf("StackTrace" to handlerParameters.exception.stackTrace[0]))
                .origin("에러가 발생한 서버 정보 입력")
                .debugUri("http://comminFnQ")
                .extensions(mapOf("errorCode" to "1001")) // 사내 에러 코드 입력 가능
                .build()


        }

        val result = DataFetcherExceptionHandlerResult.newResult()
            .error(graphqlError)
            .build()

        return CompletableFuture.completedFuture(result)
    }
}