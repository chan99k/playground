package dev.chan99k.algorithm

object BOJ_2941 {
    @JvmStatic
    fun main() {
        val input = readln()
        val croatian = setOf("c=", "c-", "dz=", "d-", "lj", "nj", "s=", "z=")

        var i = 0
        var count = 0
        while (i < input.length) {
            when {
                i + 3 <= input.length && input.substring(i, i + 3) in croatian -> i += 3
                i + 2 <= input.length && input.substring(i, i + 2) in croatian -> i += 2
                else -> i += 1
            }
            count++
        }
        println(count)
    }
}