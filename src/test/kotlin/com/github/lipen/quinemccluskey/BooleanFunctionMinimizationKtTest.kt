package com.github.lipen.quinemccluskey

import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class BooleanFunctionMinimizationKtTest {
    @ParameterizedTest(name = "{index}: minterms={0}")
    @MethodSource("mintermsProvider")
    fun `minimize to DNF without dontcares`(minterms: List<Int>) {
        val minimalDNF = minimizeToDNF(minterms, n = n)
        val result = evaluateDNF(minimalDNF, n)
        result shouldEqual minterms
    }

    @ParameterizedTest(name = "{index}: minterms={0}")
    @MethodSource("mintermsProvider")
    fun `minimize to CNF without dontcares`(minterms: List<Int>) {
        val minimalCNF = minimizeToCNF(minterms, n = n)
        val result = evaluateCNF(minimalCNF, n)
        result shouldEqual minterms
    }

    companion object {
        private const val n: Int = 3

        @JvmStatic
        fun mintermsProvider(): Iterable<List<Int>> = sequence {
            val k = "1".repeat(n).toInt(2) + 1
            for (x in 0.."1".repeat(k).toInt(2)) {
                val minterms = x.toBinary(k).mapIndexedNotNull { i, c -> i.takeIf { c == '1' } }
                yield(minterms)
            }
        }.asIterable()
    }
}
