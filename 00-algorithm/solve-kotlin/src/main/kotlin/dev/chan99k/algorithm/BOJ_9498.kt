package dev.chan99k.algorithm

object BOJ_9498 {
    @JvmStatic
    fun main(args: Array<String>) {
        val score = readln().toInt()
        when (score) {
            in 90..100 -> println("A")
            in 80..89 -> println("B")
            in 70..79 -> println("C")
            in 60..69 -> println("D")
            else -> println("F")
        }
    }
}