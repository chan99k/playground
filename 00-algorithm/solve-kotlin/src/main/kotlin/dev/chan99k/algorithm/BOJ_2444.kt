package dev.chan99k.algorithm

object BOJ_2444 {
    @JvmStatic
    fun main(args: Array<String>) {
        val n = readln().toInt()
        repeat(n) {
            println(" ".repeat(n - it - 1) + "*".repeat(2 * it + 1))
        }
        repeat(n - 1) {
            println(" ".repeat(it + 1) + "*".repeat(2 * (n - it - 1) - 1))
        }
    }
    // 별찍기 패턴의 핵심 공식: n번째 줄의 * 개수가 2n+1이면 다이아몬드, n이면 삼각형
    // 공백은 항상 전체 너비 - 별 개수의 절반
}