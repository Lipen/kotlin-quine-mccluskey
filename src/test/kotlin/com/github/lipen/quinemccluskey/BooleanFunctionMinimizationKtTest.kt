package com.github.lipen.quinemccluskey

import org.amshove.kluent.shouldEqual
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class BooleanFunctionMinimizationKtTest {
    private val n: Int = 3

    @ParameterizedTest(name = "{index}: minterms={0}")
    @MethodSource("mintermsProvider")
    fun `minimize without dontcares`(minterms: List<Int>) {
        val minimalDNF = minimizeToDNF(minterms, n = n)
        val result = evaluate(minimalDNF, n)
        result shouldEqual minterms
    }

    @Suppress("unused")
    fun mintermsProvider(): Iterable<List<Int>> = sequence {
        val k = "1".repeat(n).toInt(2) + 1
        for (x in 0.."1".repeat(k).toInt(2)) {
            val minterms = x.toBinary(k).mapIndexedNotNull { i, c -> i.takeIf { c == '1' } }
            yield(minterms)
        }
    }.asIterable()
}
