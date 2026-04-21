package dev.chan99k.algorithm

object BOJ_2739 {
    @JvmStatic
    fun main(args: Array<String>) {
        val i = readln().toInt()
            for (j in 1..9) {
                println("$i * $j = ${i * j} ")
        }
    }
}