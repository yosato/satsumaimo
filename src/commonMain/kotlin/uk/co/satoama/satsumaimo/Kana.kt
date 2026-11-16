package uk.co.satoama.satsumaimo

fun katakana_to_hiragana(text: String): String =
    text.map { if (it in 'ァ'..'ヶ') it - 0x60 else it }.joinToString("")
