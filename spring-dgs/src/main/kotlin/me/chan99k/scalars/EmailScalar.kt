package me.chan99k.scalars

import com.netflix.graphql.dgs.DgsScalar
import graphql.GraphQLContext
import graphql.execution.CoercedVariables
import graphql.language.StringValue
import graphql.language.Value
import graphql.schema.Coercing
import graphql.schema.CoercingParseValueException
import graphql.schema.CoercingSerializeException
import java.util.*

@DgsScalar(name = "Email")
class EmailScalar : Coercing<String, String> {
    private final val emailRegex: Regex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9+_.-]+$")
    override fun serialize(dataFetcherResult: Any, graphQLContext: GraphQLContext, locale: Locale): String? {
        val email = dataFetcherResult.toString()
        if (!emailRegex.matches(email)) {
            throw CoercingSerializeException("유효한 Email이 아닙니다.")
        }
        return email
    }

    override fun parseValue(input: Any, graphQLContext: GraphQLContext, locale: Locale): String? {
        val email = input.toString()
        if (emailRegex.matches(email)) {
            return email
        }
        throw CoercingParseValueException("유효한 Email 입력이 아닙니다.")
    }

    override fun parseLiteral(
        input: Value<*>,
        variables: CoercedVariables,
        graphQLContext: GraphQLContext,
        locale: Locale
    ): String? {
        val email = (input as StringValue).value
        if (emailRegex.matches(email)) {
            return email
        }
        throw CoercingParseValueException("유효한 Email 입력이 아닙니다.")
    }
}