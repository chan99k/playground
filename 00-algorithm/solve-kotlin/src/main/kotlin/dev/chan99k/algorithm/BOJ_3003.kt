package dev.chan99k.algorithm

object BOJ_3003 {
    @JvmStatic
    fun main(args: Array<String>) {
        val cnt = readln().split(" ").map { it.toInt() }
        val correct = intArrayOf(1, 1, 2, 2, 2, 8)
        println(cnt.zip(correct.toList()) { a, b -> a - b }.joinToString(" "))
    }
}