package com.github.lipen.quinemccluskey

/**
 * Petrick's method.
 */
internal fun getAdditionalCoveringPrimeImplicants(
    primeImplicants: List<Implicant>,
    minterms: List<Minterm>
): List<Implicant> = when {
    minterms.isEmpty() -> emptyList()
    else -> primeImplicants
        .powerset()
        .filter { subset -> subset.flatMap { it.minterms }.containsAll(minterms) }
        .minWith(
            compareBy(
                { subset -> subset.size },
                { subset -> subset.sumBy { it.literals.size } }
            )
        )!!
}
