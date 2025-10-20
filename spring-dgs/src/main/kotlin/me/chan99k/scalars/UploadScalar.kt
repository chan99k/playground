package me.chan99k.scalars

import com.netflix.graphql.dgs.DgsScalar
import graphql.GraphQLContext
import graphql.execution.CoercedVariables
import graphql.language.Value
import graphql.schema.Coercing
import graphql.schema.CoercingParseLiteralException
import graphql.schema.CoercingParseValueException
import graphql.schema.CoercingSerializeException
import org.springframework.web.multipart.MultipartFile
import java.util.*

@DgsScalar(name = "Upload")
class UploadScalar : Coercing<MultipartFile, String> {

    override fun serialize(dataFetcherResult: Any, graphQLContext: GraphQLContext, locale: Locale): String {
        return when (dataFetcherResult) {
            is MultipartFile -> dataFetcherResult.originalFilename ?: "unknown"
            is String -> dataFetcherResult
            else -> throw CoercingSerializeException("Cannot serialize Upload: ${dataFetcherResult::class.java}")
        }
    }

    override fun parseValue(input: Any, graphQLContext: GraphQLContext, locale: Locale): MultipartFile {
        return when (input) {
            is MultipartFile -> input
            else -> throw CoercingParseValueException("Cannot parse Upload from value: $input")
        }
    }

    override fun parseLiteral(
        input: Value<*>,
        variables: CoercedVariables,
        graphQLContext: GraphQLContext,
        locale: Locale
    ): MultipartFile {
        throw CoercingParseLiteralException("Upload scalar cannot be parsed from literal: $input")
    }
}