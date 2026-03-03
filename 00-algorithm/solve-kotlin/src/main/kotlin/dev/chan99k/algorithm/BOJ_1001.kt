package dev.chan99k.algorithm

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer

object BOJ1001 {
    private val br = BufferedReader(InputStreamReader(System.`in`))
    private var st: StringTokenizer? = null

    private fun next(): String {
        while (st?.hasMoreTokens() != true) {
            st = StringTokenizer(br.readLine())
        }
        return st!!.nextToken()
    }

    private fun nextInt() = next().toInt()

    @JvmStatic
    fun main(args: Array<String>) {
        val a = nextInt()
        val b = nextInt()
        print(a - b)
    }
}
