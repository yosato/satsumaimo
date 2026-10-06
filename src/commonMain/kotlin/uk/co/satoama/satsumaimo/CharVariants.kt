package uk.co.satoama.satsumaimo

object CharVariants {
    fun hiragana_to_katakana(text: String): String =
        text.map { if (it in 'ぁ'..'ゖ') it + 0x60 else it }.joinToString("")

    fun fullwidth_to_halfwidth_ascii(text: String): String =
        text.map {
            when (it) {
                '　' -> ' '
                in '！'..'～' -> it - 0xFEE0
                else -> it
            }
        }.joinToString("")
}
