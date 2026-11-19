package uk.co.satoama.satsumaimo

import kotlin.test.Test
import kotlin.test.assertEquals

class NormaliseTest {
    private val normaliser = Normaliser()
    @Test
    fun default_has_no_dictionary_so_kana_falls_back_to_katakana() {
        assertEquals("サツマイモ", normaliser.canonical_form("さつまいも"))
    }

    @Test
    fun hiragana_becomes_katakana() {
        assertEquals("ヌペラポ", normaliser.canonical_form("ぬぺらぽ"))
    }

    @Test
    fun fullwidth_becomes_halfwidth() {
        assertEquals("ABC123", normaliser.canonical_form("ＡＢＣ１２３"))
    }

    @Test
    fun surrounding_whitespace_is_trimmed_and_internal_runs_collapse() {
        assertEquals("ヌペ ラポ", normaliser.canonical_form("　ぬぺ  　らぽ\n"))
    }
}
