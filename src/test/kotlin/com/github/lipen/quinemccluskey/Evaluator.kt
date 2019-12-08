package com.github.lipen.quinemccluskey

import kotlin.math.abs

internal fun evaluate(dnf: List<List<Literal>>, valuation: List<Boolean>): Boolean {
    return dnf.any { term ->
        term.all { literal ->
            (literal < 0) xor valuation[abs(literal) - 1]
        }
    }
}

internal fun evaluate(dnf: List<List<Literal>>, n: Int): List<Int> {
    return (0.."1".repeat(n).toInt(2)).filter { i ->
        val valuation = i.toString(2).padStart(n, '0').map {
            when (it) {
                '0' -> false
                '1' -> true
                else -> error("Bad binary character '$it'")
            }
        }
        evaluate(dnf, valuation)
    }
}
