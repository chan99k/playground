package dev.chan99k.algorithm

object BOJ_2884 {
    @JvmStatic
    fun main(args: Array<String>) {
        var (h, m) = readln().split(" ").map { it.toInt() }
        m -= 45
        if (m < 0) {
            h -= 1
            if (h < 0) h += 24
            m += 60
        }
        println("$h $m")
    }
}