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

    return coveringPrimeImplicants.map { it.toLiterals() }
}
