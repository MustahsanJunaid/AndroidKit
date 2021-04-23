package com.android.kit.util

import com.android.kit.logV
import java.math.BigInteger
import kotlin.math.abs
import kotlin.math.max

/**
 * Created by Mustahsan on 30-Aug-17.
 */

object MathUtils {

    fun getGCD(number1: Number, number2: Number): Int {
        val b1 = BigInteger.valueOf(number1.toLong())
        val b2 = BigInteger.valueOf(number2.toLong())
        val gcd = b1.gcd(b2)
        return gcd.toInt()
    }

    fun ratio(number1: Number, number2: Number): Pair<Float, Float> {
        val gcd = getGCD(number1, number2)
        val n1 = number1.toFloat()
        val n2 = number2.toFloat()
        return Pair(n1 / gcd, n2 / gcd)
    }
//
//    fun ratio(number1: Int, number2: Int): Pair<Float, Float> {
//        val gcd = getGCD(number1, number2)
//        val n1 = number1.toFloat()
//        val n2 = number2.toFloat()
//        return Pair(n1 / gcd, n2 / gcd)
//    }

    fun ratio(number1: Float, number2: Float): Pair<Float, Float> {

        val text1 = abs(number1).toString()
        val ip1: Int = text1.indexOf('.')
        val dp1: Int = text1.length - ip1 - 1
        val text2 = abs(number2).toString()

        val ip2: Int = text2.indexOf('.')
        val dp2: Int = text2.length - ip2 - 1

        val multiplier = "1${String.format("%0${max(dp1, dp2)-1}d", 0)}".toInt()
        val n1 = number1 * multiplier
        val n2 = number2 + multiplier
        logV("n1=$n1\n2=$n2")
        val gcd = getGCD(number1, number2)
        return Pair(n1 / gcd, n2 / gcd)
    }
}
