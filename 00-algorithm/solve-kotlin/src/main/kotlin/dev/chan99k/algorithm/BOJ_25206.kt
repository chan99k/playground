package dev.chan99k.algorithm

object BOJ_25206 {
    @JvmStatic
    fun main() {
        val table = mapOf(
            "A+" to 4.5, "A0" to 4.0,
            "B+" to 3.5, "B0" to 3.0,
            "C+" to 2.5, "C0" to 2.0,
            "D+" to 1.5, "D0" to 1.0,
            "F" to 0.0
        )
        var credit = 0
        var sum = 0.0
        repeat(20) {
            val input = readln().split(" ")
            val c = input[1].toInt()
            val grade = input[2]
            if (grade == "P") return@repeat  // P면 이번 반복 건너뜀
            credit += c
            sum += c * table[grade]!!
        }
        println(sum / credit)
    }

}