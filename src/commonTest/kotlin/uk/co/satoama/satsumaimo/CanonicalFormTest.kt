package uk.co.satoama.satsumaimo

import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.readString
import kotlin.test.Test
import kotlin.test.assertEquals

private fun load_test_dictionary(): Dictionary {
    val text = SystemFileSystem.source(Path("testdata/test_dictionary.json")).buffered().use { it.readString() }
    return Dictionary.from_json(text)
}

class CanonicalFormTest {
    private val dict = load_test_dictionary()

    @Test
    fun katakana_variant_maps_to_canonical() {
        assertEquals("薩摩イモ", dict.canonical_form("サツマイモ"))
    }

    @Test
    fun hiragana_variant_maps_to_canonical() {
        assertEquals("薩摩イモ", dict.canonical_form("さつまいも"))
    }

    @Test
    fun kanji_and_hiragana_mix_maps_to_canonical() {
        assertEquals("薩摩イモ", dict.canonical_form("さつま芋"))
    }

    @Test
    fun kanji_and_hiragana_mix_with_kanji_first_maps_to_canonical() {
        assertEquals("薩摩イモ", dict.canonical_form("薩摩いも"))
    }

    @Test
    fun canonical_form_maps_to_itself() {
        assertEquals("薩摩イモ", dict.canonical_form("薩摩イモ"))
    }

    @Test
    fun unknown_hiragana_word_falls_back_to_katakana() {
        assertEquals("ヌペラポ", dict.canonical_form("ぬぺらぽ"))
    }

    @Test
    fun fullwidth_letters_and_digits_become_halfwidth() {
        assertEquals("ABC123", dict.canonical_form("ＡＢＣ１２３"))
    }

    @Test
    fun fullwidth_symbols_become_halfwidth() {
        assertEquals("!@#()", dict.canonical_form("！＠＃（）"))
    }

    @Test
    fun fullwidth_space_becomes_plain_space() {
        assertEquals("a b", dict.canonical_form("ａ　ｂ"))
    }

    @Test
    fun fullwidth_tilde_becomes_halfwidth() {
        assertEquals("~", dict.canonical_form("～"))
    }

    @Test
    fun width_and_katakana_fallback_combine() {
        assertEquals("ヌペラポA", dict.canonical_form("ぬぺらぽＡ"))
    }
}
