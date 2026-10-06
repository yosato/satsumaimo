package uk.co.satoama.satsumaimo

object Kana {
    fun hiragana_to_katakana(text: String): String =
        text.map { if (it in 'ぁ'..'ゖ') it + 0x60 else it }.joinToString("")
}
