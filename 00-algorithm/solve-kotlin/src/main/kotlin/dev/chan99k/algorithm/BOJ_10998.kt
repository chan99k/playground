package dev.chan99k.algorithm

import java.io.BufferedInputStream

object BOJ10998 {
    private val input = BufferedInputStream(System.`in`)
    private val buffer = ByteArray(1 shl 16)
    private var len = 0
    private var ptr = 0

    private fun readByte(): Int {
        if (ptr >= len) {
            len = input.read(buffer)
            ptr = 0
            if (len <= 0) return -1
        }
        return buffer[ptr++].toInt()
    }

    fun nextInt(): Int {
        var c = readByte()
        while (c <= 32) c = readByte()

        var sign = 1
        if (c == '-'.code) {
            sign = -1
            c = readByte()
        }

        var num = 0
        while (c > 32) {
            num = num * 10 + (c - 48)
            c = readByte()
        }

        return num * sign
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val a = nextInt()
        val b = nextInt()
        println(a * b)
    }
}
