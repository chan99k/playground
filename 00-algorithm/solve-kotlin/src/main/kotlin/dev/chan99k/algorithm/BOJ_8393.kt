package dev.chan99k.algorithm

object BOJ_8393 {
    @JvmStatic
    fun main(args: Array<String>) {
        val num = readln().toInt()
        print(sum(num, 0))
    }

    private tailrec fun sum(n: Int, acc: Int = 0): Int {
        if (n == 0) return acc
        return sum(n - 1, acc + n)
    }
}