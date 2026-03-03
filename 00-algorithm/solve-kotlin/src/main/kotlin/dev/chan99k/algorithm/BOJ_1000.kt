package dev.chan99k.algorithm

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer

object BOJ1000 {
    private val br = BufferedReader(InputStreamReader(System.`in`))
    private var st: StringTokenizer? = null
    private val sb = StringBuilder()

    private fun next(): String {
        while (st == null || !st!!.hasMoreTokens()) {
            st = StringTokenizer(br.readLine())
        }
        return st!!.nextToken()
    }

    private fun nextInt() = next().toInt()

    @JvmStatic
    fun main(args: Array<String>) {
        val a = nextInt()
        val b = nextInt()

        sb.append(a + b).append('\n')
        print(sb)
    }
}
