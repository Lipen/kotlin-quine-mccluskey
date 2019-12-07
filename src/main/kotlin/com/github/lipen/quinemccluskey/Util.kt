package com.github.lipen.quinemccluskey

internal fun Int.toBinary(n: Int): String =
    toString(2).padStart(n, '0')

internal fun <T> Iterable<T>.powerset(): Sequence<List<T>> = this.toList().powerset()

internal fun <T> Collection<T>.powerset(): Sequence<List<T>> = powerset(this, sequenceOf(emptyList()))

private tailrec fun <T> powerset(left: Collection<T>, acc: Sequence<List<T>>): Sequence<List<T>> = when {
    left.isEmpty() -> acc
    else -> powerset(left.drop(1), acc + acc.map { it + left.first() })
}
