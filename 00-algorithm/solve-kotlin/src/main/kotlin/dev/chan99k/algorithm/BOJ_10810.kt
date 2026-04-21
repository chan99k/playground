package dev.chan99k.algorithm

object BOJ_10810 {
    @JvmStatic
    fun main(args: Array<String>) {
        val (n, m) = readln().split(" ").map { it.toInt() }
        val arr = IntArray(n)
        repeat(m) {
            val (i, j, x) = readln().split(" ").map { it.toInt() }
            for (k in i..j) {
                arr[k - 1] = x
            }
        }
        println(arr.joinToString(" "))
    }
}