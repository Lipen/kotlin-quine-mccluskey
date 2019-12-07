package com.github.lipen.quinemccluskey

/**
 * Quine-McCluskey method.
 */
internal fun getPrimeImplicants(
    minterms: List<Minterm>,
    dontcares: List<Minterm> = emptyList(),
    n: Int
): List<Implicant> {
    require(n >= 1)

    val initialGroups: List<MutableSet<Implicant>> = List(n + 1) { mutableSetOf<Implicant>() }
    for (m in minterms + dontcares) {
        val numberOf1s = m.toString(2).count { it == '1' }
        initialGroups[numberOf1s].add(Implicant.minterm(m, n = n))
    }
    var groups: List<Set<Implicant>> = initialGroups
    val primeImplicants: MutableSet<Implicant> = mutableSetOf()

    while (groups.isNotEmpty()) {
        val newGroups = mutableListOf<Set<Implicant>>()

        for ((group1, group2) in groups.zipWithNext()) {
            val newGroup = mutableSetOf<Implicant>()
            for (term1 in group1)
                for (term2 in group2) {
                    term1.combine(term2)?.let { combined ->
                        term1.mark()
                        term2.mark()
                        newGroup.add(combined)
                    }
                }
            newGroups.add(newGroup)
        }

        groups.flatten().filter { !it.marked }.toCollection(primeImplicants)
        groups = newGroups //.map { group -> group.distinctBy { term -> term.binary } }
    }

    return primeImplicants.toList()
}

internal fun getEssentialPrimeImplicants(
    primeImplicants: List<Implicant>,
    minterms: List<Minterm>
): List<Implicant> {
    val essentialPrimeImplicants: MutableList<Implicant> = mutableListOf()
    val pis: MutableList<Implicant> = primeImplicants.toMutableList() // prime implicants
    val ms: MutableList<Minterm> = minterms.toMutableList() // minterms

    while (pis.isNotEmpty() && ms.isNotEmpty()) {
        // Extract EPIs from the prime implicant chart
        val epis = getEssentialPrimeImplicantsStep(pis, ms)
        if (epis.isEmpty()) break
        essentialPrimeImplicants += epis

        // Reduce the prime implicant chart
        pis -= epis
        ms -= epis.flatMap { it.minterms }
        // TODO: eliminate redundant prime implicants
    }

    return essentialPrimeImplicants
}

private fun getEssentialPrimeImplicantsStep(
    primeImplicants: List<Implicant>,
    minterms: List<Minterm>
): List<Implicant> =
    minterms
        .map { m -> primeImplicants.filter { m in it.minterms } }
        .filter { it.size == 1 }
        .flatten()
        .distinct()
