package dev.chan99k.algorithm

object BOJ_10811 {
    @JvmStatic
    fun main(args: Array<String>) {
        val (n, m) = readln().split(" ").map { it.toInt() }
        val arr = IntArray(n) { it + 1 }
        repeat(m) {
            val (a, b) = readln().split(" ").map { it.toInt() }
            var left = a - 1
            var right = b - 1
            while (left < right) {
                arr[left] = arr[right].also { arr[right] = arr[left] }
                left++
                right--
            }
        }
        println(arr.joinToString(" "))
    }
}