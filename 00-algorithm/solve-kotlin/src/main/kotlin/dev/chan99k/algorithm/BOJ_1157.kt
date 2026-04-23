package dev.chan99k.algorithm

object BOJ_1157 {
    // 방법 1: 수동 루프 — 플래그 변수로 직접 추적
    fun solveManual() {
        val freq = IntArray(26)
        for (c in readln()) freq[c.lowercaseChar() - 'a']++

        var max = -1
        var maxIdx = 0
        var duplicate = false
        for (i in freq.indices) {
            if (freq[i] > max) {
                max = freq[i]
                maxIdx = i
                duplicate = false
            } else if (freq[i] == max) {
                duplicate = true
            }
        }
        println(if (duplicate) "?" else ('A' + maxIdx))
    }

    // 방법 2: stdlib 활용 — 선언적 스타일
    fun solveStdlib() {
        val freq = IntArray(26)
        for (c in readln()) freq[c.lowercaseChar() - 'a']++

        val max = freq.max()
        println(if (freq.count { it == max } > 1) "?" else ('A' + freq.indexOf(max)))
    }

}