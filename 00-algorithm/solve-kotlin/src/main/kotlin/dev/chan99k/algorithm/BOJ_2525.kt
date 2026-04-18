package dev.chan99k.algorithm

object BOJ_2525 {
    @JvmStatic
    fun main(args: Array<String>) {
        var (h, m) = readln().split(" ").map { it.toInt() }
        val t = readln().toInt()
        m += t
        if (m >= 60) {
            val r = m / 60
            m -= m % 60
            h += r
        }
        if (h >= 24) h -= 24
        println("$h $m")
    }
}