package dev.chan99k.algorithm

object BOJ_10988 {
    @JvmStatic
    fun main(args: Array<String>) {
        val str = readln()
        var left = 0
        var right = str.length - 1
        while (left < right) {
            if (str[left] != str[right]) {
                println(0)
                return
            }
            left++
            right--
        }
        println(1)
    }
}