package uk.co.satoama.satsumaimo

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class Dictionary(entries: Map<String, List<String>>) {
    private val canonicalByVariant: Map<String, String> =
        entries.flatMap { (canonical, variants) -> variants.map { it to canonical } }.toMap()

    fun canonical_form(text: String): String {
        val halfwidth = CharVariants.fullwidth_to_halfwidth_ascii(text)
        return canonicalByVariant[halfwidth] ?: CharVariants.hiragana_to_katakana(halfwidth)
    }

    companion object {
        fun from_json(text: String): Dictionary =
            Dictionary(Json.decodeFromString<Map<String, List<String>>>(text))
    }
}
