package com.logician.studio.androidkit.util

import java.math.BigInteger

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
}
