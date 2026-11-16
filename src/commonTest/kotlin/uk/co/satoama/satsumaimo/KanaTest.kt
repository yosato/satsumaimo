package uk.co.satoama.satsumaimo

import kotlin.test.Test
import kotlin.test.assertEquals

class KanaTest {
    @Test
    fun katakana_becomes_hiragana() {
        assertEquals("さつまいも", katakana_to_hiragana("サツマイモ"))
    }

    @Test
    fun hiragana_is_unchanged() {
        assertEquals("さつまいも", katakana_to_hiragana("さつまいも"))
    }

    @Test
    fun kanji_is_unchanged_in_mixed_text() {
        assertEquals("薩摩いも", katakana_to_hiragana("薩摩イモ"))
    }

    @Test
    fun long_vowel_mark_is_unchanged() {
        assertEquals("らーめん", katakana_to_hiragana("ラーメン"))
    }

    @Test
    fun empty_string_stays_empty() {
        assertEquals("", katakana_to_hiragana(""))
    }
}
