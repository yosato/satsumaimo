package uk.co.satoama.satsumaimo

import kotlin.test.Test
import kotlin.test.assertEquals

private fun load_test_dictionary(): Normaliser =
    Normaliser.from_json("""{"薩摩イモ": ["薩摩芋", "さつまいも", "サツマイモ", "さつま芋", "薩摩いも", "薩摩イモ"]}""")

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

    @Test
    fun surrounding_spaces_are_trimmed() {
        assertEquals("薩摩イモ", dict.canonical_form(" サツマイモ "))
    }

    @Test
    fun surrounding_fullwidth_spaces_are_trimmed() {
        assertEquals("薩摩イモ", dict.canonical_form("　サツマイモ　"))
    }

    @Test
    fun surrounding_tab_and_newline_are_trimmed() {
        assertEquals("薩摩イモ", dict.canonical_form("\tサツマイモ\n"))
    }

    @Test
    fun internal_space_runs_collapse_to_one_space() {
        assertEquals("ヌペ ラポ", dict.canonical_form("ぬぺ  　らぽ"))
    }
}
