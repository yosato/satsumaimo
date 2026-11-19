package uk.co.satoama.satsumaimo

private val dotHeader = listOf(
    "digraph lattice {",
    "    rankdir=LR;",
    "    node [shape=circle fontname=\"Hiragino Sans\"];",
    "    edge [fontname=\"Hiragino Sans\" fontsize=11];"
)

private fun edge_label(edge: Edge): String {
    val lexeme = edge.lexeme
    val syn = when (lexeme) {
        null -> "unk"
        is InflLexeme -> lexeme.inflection + edge.form.orEmpty()
        is NonInflLexeme -> lexeme.pos
    }
    val sem = lexeme?.id?.substringAfter(':') ?: "unk"
    return "${edge.surface}:$syn:$sem"
}

private fun finishing_edges(lattice: Lattice): List<Edge> {
    val canFinish = mutableSetOf(lattice.text.length)
    for (position in lattice.text.indices.reversed()) {
        if (lattice.edges.any { it.start == position && it.end in canFinish }) canFinish += position
    }
    return lattice.edges.filter { it.end in canFinish }
}

private fun dot_edges(lattice: Lattice, prefix: String): List<String> =
    finishing_edges(lattice).map { "    $prefix${it.start} -> $prefix${it.end} [label=\"${edge_label(it)}\"];" }

fun lattice_to_dot(lattice: Lattice): String =
    (dotHeader + dot_edges(lattice, "") + "}").joinToString("\n")

fun lattices_to_dot(lattices: List<Pair<String, Lattice>>): String {
    val clusters = lattices.flatMapIndexed { index, (text, lattice) ->
        val prefix = "c${index}_"
        val nodes = finishing_edges(lattice).flatMap { listOf(it.start, it.end) }.distinct()
            .map { "        $prefix$it [label=\"$it\"];" }
        listOf("    subgraph cluster_$index {", "        label=\"$text\";") +
            nodes + dot_edges(lattice, prefix).map { "    $it" } + "    }"
    }
    return (dotHeader + clusters + "}").joinToString("\n")
}
