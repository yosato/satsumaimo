package uk.co.satoama.satsumaimo

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class Normaliser(entries: Map<String, List<String>> = emptyMap()) {
    private val canonicalByVariant: Map<String, String> =
        entries.flatMap { (canonical, variants) -> variants.map { it to canonical } }.toMap()

    fun canonical_form(text: String): String {
        val halfwidth = CharVariants.fullwidth_to_halfwidth_ascii(text)
        val spaced = CharVariants.collapse_whitespace(halfwidth)
        return canonicalByVariant[spaced] ?: CharVariants.hiragana_to_katakana(spaced)
    }
    fun score(text: String, candidate: String): Double = 0.5
    fun rank(text: String, candidates: List<String>): List<String> = candidates.sortedByDescending { score(text, it) }

    companion object {
        fun from_json(text: String): Normaliser =
            Normaliser(Json.decodeFromString<Map<String, List<String>>>(text))
    }
}
