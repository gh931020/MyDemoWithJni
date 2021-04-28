package com.example.mydemowithjni

fun main() {
    println(LeedCode.reverse(-2147483648))
}

object LeedCode {
    //- 2 147 483 648
    fun reverse(x: Int): Int {
        val isNegative: Boolean
        val result: Int
        var resultTemp: Long
        if (x == 0 || x == Int.MIN_VALUE){
            return 0
        }else{
            isNegative = (x <0)
            resultTemp = Math.abs(x).toString().reversed().toLong()
            if (isNegative){
                resultTemp = 0 - resultTemp
            }

            result = if (resultTemp < Int.MIN_VALUE || resultTemp > Int.MAX_VALUE){
                0
            }else{
                resultTemp.toInt()
            }

        }
        return result
        /* 通过除或者取余操作对数字进行反转.
        var y = x
        var r = 0
        while (y != 0) {
            val p = y % 10
            if ((r > 0 && r > (Int.MAX_VALUE - y) / 10) || (r < 0 && r < (Int.MIN_VALUE - y) / 10)) {
                return 0
            }
            r = r * 10 + p
            y /= 10
        }
        return r
         */
    }

}