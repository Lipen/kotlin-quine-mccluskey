package com.github.lipen.quinemccluskey

import kotlin.math.abs

internal fun evaluateDNF(dnf: List<List<Literal>>, valuation: List<Boolean>): Boolean =
    dnf.any { term ->
        term.all { literal ->
            (literal < 0) xor valuation[abs(literal) - 1]
        }
    }

internal fun evaluateDNF(dnf: List<List<Literal>>, n: Int): List<Int> =
    (0.."1".repeat(n).toInt(2)).filter { i ->
        val valuation = i.toString(2).padStart(n, '0').map {
            when (it) {
                '0' -> false
                '1' -> true
                else -> error("Bad binary character '$it'")
            }
        }
        evaluateDNF(dnf, valuation)
    }

internal fun evaluateCNF(cnf: List<List<Literal>>, valuation: List<Boolean>): Boolean =
    cnf.all { term ->
        term.any { literal ->
            (literal < 0) xor valuation[abs(literal) - 1]
        }
    }

internal fun evaluateCNF(cnf: List<List<Literal>>, n: Int): List<Int> =
    (0.."1".repeat(n).toInt(2)).filter { i ->
        val valuation = i.toString(2).padStart(n, '0').map {
            when (it) {
                '0' -> false
                '1' -> true
                else -> error("Bad binary character '$it'")
            }
        }
        evaluateCNF(cnf, valuation)
    }
