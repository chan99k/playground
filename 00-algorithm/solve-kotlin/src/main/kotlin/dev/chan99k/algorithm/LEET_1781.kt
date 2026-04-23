package dev.chan99k.algorithm

object LEET_1781 {
    @JvmStatic
    fun main(args: Array<String>) {
        beautySum("leetcode").let(::println)
    }

    fun beautySum(s: String): Int {
        var result = 0
        for (i in 0 until s.length-1) {
            val freq = IntArray(26)
            for (j in i until s.length) {
                freq[s[j] - 'a']++
                val max = freq.max()
                val min = freq.filter { it > 0 }.min()
                result += max - min
            }
        }
        return result
    }
}