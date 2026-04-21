package dev.chan99k.algorithm

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.StringTokenizer

object BOJ_15552 {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.`out`))
    @JvmStatic
    fun main(args: Array<String>) {
        val t = br.readLine().toInt()

        repeat(t) {
            val st = StringTokenizer(br.readLine())
            val a = st.nextToken().toInt()
            val b = st.nextToken().toInt()
            bw.write("${a + b}\n")
        }
        bw.flush()
    }
}