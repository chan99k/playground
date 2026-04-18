package dev.chan99k.algorithm

object BOJ_1330 {
    @JvmStatic
    fun main(args: Array<String>) {
        val (i, j) = readln().split(" ").map { it.toInt() }
        if (i > j) {
            println('>')
        } else if (i < j) {
            println('<')
        } else {
            println('=')
        }
    }
}