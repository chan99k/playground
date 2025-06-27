package me.chan99k.monitoring

import graphql.ExecutionResult
import graphql.execution.instrumentation.InstrumentationState
import graphql.execution.instrumentation.parameters.InstrumentationExecutionParameters
import graphql.execution.instrumentation.tracing.TracingInstrumentation
import graphql.execution.instrumentation.tracing.TracingSupport
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture
import kotlin.collections.iterator

@Component
class MovieTracingInstrumentation : TracingInstrumentation(Options.newOptions().includeTrivialDataFetchers(false)) {
    private val logger = LoggerFactory.getLogger(this::class.java)
    override fun instrumentExecutionResult(
        executionResult: ExecutionResult,
        parameters: InstrumentationExecutionParameters,
        state: InstrumentationState
    ): CompletableFuture<ExecutionResult> {
        require(state is TracingSupport)

        logger.info("\n[Tracing]\n" + formatMap(state.snapshotTracingData()))

        return CompletableFuture.completedFuture(executionResult)
    }

    private fun formatMap(map: Map<String, Any?>, indent: Int = 0): String {
        val indentSpace = "  ".repeat(indent) // 들여쓰기 공백
        val builder = StringBuilder()

        for ((key, value) in map) {
            builder.append("$indentSpace$key: ")

            extracted(value, builder, indent)
        }
        return builder.toString()
    }


    private fun formatList(list: List<*>, indent: Int): String {
        val indentSpace = "  ".repeat(indent)
        val builder = StringBuilder()

        for (item in list) {
            builder.append("$indentSpace- ")
            extracted(item, builder, indent)
        }
        return builder.toString()
    }

    private fun extracted(item: Any?, builder: StringBuilder, indent: Int) {
        when (item) {
            is Map<*, *> -> {
                builder.append("\n")
                @Suppress("UNCHECKED_CAST")
                builder.append(formatMap(item as Map<String, Any?>, indent + 1))
            }

            is List<*> -> {
                builder.append("\n")
                builder.append(formatList(item, indent + 1))
            }

            else -> builder.append("$item\n")
        }
    }
}