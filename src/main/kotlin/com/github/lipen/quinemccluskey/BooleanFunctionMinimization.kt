package com.github.lipen.quinemccluskey

fun minimizeToDNF(
    minterms: List<Minterm>,
    dontcares: List<Minterm> = emptyList(),
    n: Int
): List<List<Literal>> {
    val primeImplicants = getPrimeImplicants(minterms, dontcares, n)
    val essentialPrimeImplicants = getEssentialPrimeImplicants(primeImplicants, minterms)
    val uncoveredMinterms = minterms - essentialPrimeImplicants.flatMap { it.minterms }
    val additionalPrimeImplicants =
        getAdditionalCoveringPrimeImplicants(
            primeImplicants = primeImplicants - essentialPrimeImplicants,
            minterms = uncoveredMinterms
        )
    val coveringPrimeImplicants = essentialPrimeImplicants + additionalPrimeImplicants

    return coveringPrimeImplicants.map { it.literals }
}

fun minimizeToCNF(
    minterms: List<Minterm>,
    dontcares: List<Minterm> = emptyList(),
    n: Int
): List<List<Literal>> {
    val maxterms: List<Minterm> = (0.."1".repeat(n).toInt(2)) - minterms - dontcares
    val primeImplicants = getPrimeImplicants(maxterms, dontcares, n)
    val essentialPrimeImplicants = getEssentialPrimeImplicants(primeImplicants, maxterms)
    val uncoveredMaxterms = maxterms - essentialPrimeImplicants.flatMap { it.minterms }
    val additionalPrimeImplicants =
        getAdditionalCoveringPrimeImplicants(
            primeImplicants = primeImplicants - essentialPrimeImplicants,
            minterms = uncoveredMaxterms
        )
    val coveringPrimeImplicants = essentialPrimeImplicants + additionalPrimeImplicants

    return coveringPrimeImplicants.map { primeImplicant -> primeImplicant.literals.map { -it } }
}
