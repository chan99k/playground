package dev.chan99k.algorithm

object BOJ_2753 {
    @JvmStatic
    fun main(args: Array<String>) {
        var y = readln().toInt()
        if ((y % 4 == 0 && y % 100 != 0 )|| y % 400 == 0) {
            println("1")
        } else {
            println("0")
        }
    }
}

//윤년은 연도가 4의 배수이면서, 100의 배수가 아닐 때 또는 400의 배수일 때이다.
//
//예를 들어, 2012년은 4의 배수이면서 100의 배수가 아니라서 윤년이다. 1900년은 100의 배수이고 400의 배수는 아니기 때문에 윤년이 아니다. 하지만, 2000년은 400의 배수이기 때문에 윤년이다.
//
