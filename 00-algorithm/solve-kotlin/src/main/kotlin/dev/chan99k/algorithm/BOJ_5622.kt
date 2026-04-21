package dev.chan99k.algorithm

object BOJ_5622 {
    @JvmStatic
    fun main(args: Array<String>) {
        val str = readln()
        println(str.sumOf { c ->
            when (c) {
                in 'A'..'C' -> 2
                in 'D'..'F' -> 3
                in 'G'..'I' -> 4
                in 'J'..'L' -> 5
                in 'M'..'O' -> 6
                in 'P'..'S' -> 7
                in 'T'..'V' -> 8
                in 'W'..'Z' -> 9
                else -> 1
            }
        })
    }
}