package dev.chan99k.algorithm

object BOJ_1316 {
    fun solveLambda() {
        val n = readln().toInt()
        println((1..n).count {
            readln().let { w ->
                w.indices.none { i -> i > 0 && w[i] != w[i - 1] && w[i] in w.substring(0, i - 1) }
            }
        })
    }

    fun solveManual() {
        var count = 0
        repeat(readln().toInt()) {
            val word = readln()
            val seen = BooleanArray(26)
            var isGroup = true
            for (i in word.indices) {
                val c = word[i] - 'a'
                if (i > 0 && word[i] != word[i - 1] && seen[c]) {
                    // 이전 문자와 다른데, 이미 등장한 적 있음 -> 그룹 단어 아님
                    isGroup = false
                    break
                }
                seen[c] = true
            }
            if (isGroup) count++
        }
        println(count)
    }

}