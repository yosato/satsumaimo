package uk.co.satoama.satsumaimo

object Inflector {
    private class GodanRow(val a: String, val o: String, val i: String, val onbin: String?, val e: String)

    private val godanRows: Map<Char, GodanRow> = mapOf(
        'く' to GodanRow("か", "こ", "き", "い", "け"),
        'ぐ' to GodanRow("が", "ご", "ぎ", "い", "げ"),
        'す' to GodanRow("さ", "そ", "し", null, "せ"),
        'つ' to GodanRow("た", "と", "ち", "っ", "て"),
        'ぬ' to GodanRow("な", "の", "に", "ん", "ね"),
        'ぶ' to GodanRow("ば", "ぼ", "び", "ん", "べ"),
        'む' to GodanRow("ま", "も", "み", "ん", "め"),
        'る' to GodanRow("ら", "ろ", "り", "っ", "れ"),
        'う' to GodanRow("わ", "お", "い", "っ", "え")
    )

    private fun ichidan_forms(orthography: String): List<InflectedForm> {
        val stem = orthography.dropLast(1)
        return listOf(
            InflectedForm(Inflection.mizen, stem),
            InflectedForm(Inflection.renyo, stem),
            InflectedForm(Inflection.shushi, orthography),
            InflectedForm(Inflection.rentai, orthography),
            InflectedForm(Inflection.katei, stem + "れ"),
            InflectedForm(Inflection.meirei, stem + "ろ"),
            InflectedForm(Inflection.meirei, stem + "よ")
        )
    }

    private fun godan_forms(orthography: String): List<InflectedForm> {
        val row = godanRows[orthography.last()] ?: return emptyList()
        val stem = orthography.dropLast(1)
        return listOfNotNull(
            InflectedForm(Inflection.mizen, stem + row.a),
            InflectedForm(Inflection.mizen, stem + row.o),
            InflectedForm(Inflection.renyo, stem + row.i),
            row.onbin?.let { InflectedForm(Inflection.renyoOnbin, stem + it) },
            InflectedForm(Inflection.shushi, orthography),
            InflectedForm(Inflection.rentai, orthography),
            InflectedForm(Inflection.katei, stem + row.e),
            InflectedForm(Inflection.meirei, stem + row.e)
        )
    }

    private fun adj_forms(orthography: String): List<InflectedForm> {
        val stem = orthography.dropLast(1)
        return listOf(
            InflectedForm(Inflection.mizen, stem + "かろ"),
            InflectedForm(Inflection.renyoOnbin, stem + "かっ"),
            InflectedForm(Inflection.renyo, stem + "く"),
            InflectedForm(Inflection.shushi, orthography),
            InflectedForm(Inflection.rentai, orthography),
            InflectedForm(Inflection.katei, stem + "けれ")
        )
    }

    private val auxiliaryForms: Map<String, List<InflectedForm>> = mapOf(
        "たい" to adj_forms("たい"),
        "た" to listOf(
            InflectedForm(Inflection.mizen, "たろ"),
            InflectedForm(Inflection.shushi, "た"),
            InflectedForm(Inflection.rentai, "た"),
            InflectedForm(Inflection.katei, "たら")
        ),
        "ぬ" to listOf(
            InflectedForm(Inflection.mizen, "ず"),
            InflectedForm(Inflection.renyo, "ず"),
            InflectedForm(Inflection.shushi, "ぬ"),
            InflectedForm(Inflection.rentai, "ぬ"),
            InflectedForm(Inflection.katei, "ね")
        ),
        "ん" to listOf(
            InflectedForm(Inflection.shushi, "ん"),
            InflectedForm(Inflection.rentai, "ん")
        ),
        "みたい" to listOf(
            InflectedForm(Inflection.shushi, "みたい"),
            InflectedForm(Inflection.rentai, "みたい")
        )
    )

    fun forms(orthography: String, conjugation: String): List<InflectedForm> = when (conjugation) {
        Inflection.ichidan -> ichidan_forms(orthography)
        Inflection.godan -> godan_forms(orthography)
        Inflection.adj -> adj_forms(orthography)
        Inflection.aux -> auxiliaryForms[orthography].orEmpty()
        else -> emptyList()
    }
}
