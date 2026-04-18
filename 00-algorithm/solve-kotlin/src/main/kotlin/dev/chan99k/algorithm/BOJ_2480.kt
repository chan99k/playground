package dev.chan99k.algorithm

object BOJ_2480 {
    @JvmStatic
    fun main(args: Array<String>) {
        val (a, b, c) = readln().split(" ").map { it.toInt() }
        when {
            a == b && b == c -> println(10_000 + a * 1_000)
            a == b -> println(1_000 + a * 100)
            b == c -> println(1_000 + b * 100)
            a == c -> println(1_000 + a * 100)
            else -> println(a.coerceAtLeast(b).coerceAtLeast(c) * 100)
        }
    }
}