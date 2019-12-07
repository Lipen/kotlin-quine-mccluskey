package com.github.lipen.quinemccluskey

typealias Minterm = Int
typealias Literal = Int

internal data class Implicant(
    val minterms: Set<Minterm>,
    val binary: String
) {
    val literals: List<Literal> = binary.mapIndexedNotNull { i, c ->
        when (c) {
            '1' -> i + 1
            '0' -> -(i + 1)
            '-' -> null
            else -> error("Bad character in binary string: '$c'")
        }
    }

    var marked: Boolean = false
        private set

    fun mark() {
        marked = true
    }

    fun combine(other: Implicant): Implicant? {
        if (binary == other.binary || minterms == other.minterms) return null

        var result = ""
        var difference = 0

        for ((c1, c2) in binary.zip(other.binary)) {
            if (c1 != c2) {
                result += '-'
                difference++
            } else {
                result += c1
            }

            if (difference > 1) return null
        }

        return Implicant(minterms + other.minterms, result)
    }

    fun toLiterals(): List<Literal> {
        return binary.mapIndexedNotNull { i, c ->
            when (c) {
                '1' -> i + 1
                '0' -> -(i + 1)
                '-' -> null
                else -> error("Bad character in binary string: '$c'")
            }
        }
    }

    override fun toString(): String {
        return "Implicant(${minterms.sorted()}, '$binary')"
    }

    companion object {
        fun minterm(minterm: Minterm, n: Int): Implicant =
            Implicant(setOf(minterm), minterm.toBinary(n))
    }
}
