package dev.chan99k.algorithm

object BOJ_2675 {

    // 풀이 1: flatMap + joinToString (선언적)
    fun solveFlatMap() {
        repeat(readln().toInt()) {
            val (r, s) = readln().split(" ")
            println(s.flatMap { c -> List(r.toInt()) { c } }.joinToString(""))
        }
    }

    // 풀이 2: buildString (선언적 + 효율적)
    fun solveBuildString() {
        repeat(readln().toInt()) {
            val (rStr, s) = readln().split(" ")
            val r = rStr.toInt()
            println(buildString { for (c in s) repeat(r) { append(c) } })
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        solveBuildString()
    }
}
