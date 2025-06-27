package me.chan99k.scalars

import com.netflix.graphql.dgs.DgsScalar
import graphql.GraphQLContext
import graphql.execution.CoercedVariables
import graphql.language.StringValue
import graphql.language.Value
import graphql.schema.Coercing
import graphql.schema.CoercingParseLiteralException
import graphql.schema.CoercingSerializeException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

 @DgsScalar(name = "Date")
class DateScalar : Coercing<LocalDate, String> {

    override fun serialize(dataFetcherResult: Any, graphQLContext: GraphQLContext, locale: Locale): String? {
        if (dataFetcherResult is LocalDate) {
            return dataFetcherResult.format(DateTimeFormatter.ISO_DATE)
        } else {
            throw CoercingSerializeException("유효한 Date가 아닙니다.")
        }
    }

    override fun parseLiteral(
        input: Value<*>,
        variables: CoercedVariables,
        graphQLContext: GraphQLContext,
        locale: Locale
    ): LocalDate? {
        if (input is StringValue) {
            return LocalDate.parse(input.value, DateTimeFormatter.ISO_DATE)
        } else {
            throw CoercingParseLiteralException("유효한 ISO 날짜가 아닙니다.")
        }
    }

    override fun parseValue(input: Any, graphQLContext: GraphQLContext, locale: Locale): LocalDate? {
        return LocalDate.parse(input.toString(), DateTimeFormatter.ISO_DATE)
    }

}