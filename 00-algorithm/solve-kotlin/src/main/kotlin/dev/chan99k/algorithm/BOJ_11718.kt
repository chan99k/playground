package dev.chan99k.algorithm

object BOJ_11718 {
    @JvmStatic
    fun main(args: Array<String>) {
        val br = System.`in`.bufferedReader()
        val bw = System.`out`.bufferedWriter()
        var line: String?
        while (br.readLine().also { line = it } != null) {
            val str = line!!
            bw.write("${str}\n")
        }
        bw.flush()
    }
}