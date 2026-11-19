package uk.co.satoama.satsumaimo

data class Edge(val start: Int, val end: Int, val surface: String, val lexeme: Lexeme?, val form: String? = null)

private fun is_hiragana(char: Char): Boolean = char in 'ぁ'..'ゖ'

class Lattice(lexemes: List<Lexeme>, val text: String) {
    val edges: List<Edge>

    init {
        val entries = lexemes.flatMap { lexeme ->
            val surfaceForms: List<Pair<String, String?>> =
                if (lexeme.inflections.isEmpty()) lexeme.orthographies.map { it to null }
                else lexeme.inflections.map { it.surface to it.form }
            surfaceForms.filter { it.first.isNotEmpty() }.map { (surface, form) -> Triple(surface, lexeme, form) }
        }.distinct()
        val entriesBySurface = entries.groupBy { it.first }
        val maxLength = entriesBySurface.keys.maxOfOrNull { it.length } ?: 0
        val knownByStart = text.indices.flatMap { start ->
            (1..minOf(maxLength, text.length - start)).flatMap { length ->
                val surface = text.substring(start, start + length)
                entriesBySurface[surface].orEmpty().map { (_, lexeme, form) ->
                    Edge(start, start + length, surface, lexeme, form)
                }
            }
        }.groupBy { it.start }
        val reachable = mutableSetOf(0)
        val reachedEdges = mutableListOf<Edge>()
        for (position in text.indices) {
            if (position !in reachable) continue
            val known = knownByStart[position].orEmpty()
            val here = when {
                known.isNotEmpty() -> known
                is_hiragana(text[position]) ->
                    listOf(Edge(position, position + 1, text.substring(position, position + 1), null))
                else -> emptyList()
            }
            reachedEdges += here
            reachable += here.map { it.end }
        }
        edges = reachedEdges
    }

    fun paths(): List<List<Edge>> {
        val edgesByStart = edges.groupBy { it.start }
        fun paths_from(position: Int): List<List<Edge>> =
            if (position == text.length) listOf(emptyList())
            else edgesByStart[position].orEmpty().flatMap { edge ->
                paths_from(edge.end).map { listOf(edge) + it }
            }
        return paths_from(0)
    }
}
