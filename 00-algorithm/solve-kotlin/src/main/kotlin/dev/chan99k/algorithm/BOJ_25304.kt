package dev.chan99k.algorithm

object BOJ_25304 {
    @JvmStatic
    fun main(args: Array<String>) {
        var x = readln().toInt()
        val n = readln().toInt()
        for (i in 1..n) {
            val (a, b) = readln().split(" ").map { it.toInt() }
            x -= a * b
        }
        print(if (x == 0) "Yes" else "No")
    }
}